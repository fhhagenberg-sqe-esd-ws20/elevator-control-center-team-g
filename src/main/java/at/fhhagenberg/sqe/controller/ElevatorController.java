package at.fhhagenberg.sqe.controller;

import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;
import at.fhhagenberg.sqe.viewmodel.IMainViewModel;
import sqelevator.IElevator;
import sqelevator.IElevatorWrapper;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class ElevatorController implements IElevatorController {
    private static final int TIMER_INTERVAL = 100;
    private static final int MAX_TICK_DIFF = -1;
    private Timer mTimer;
    private final IElevatorWrapper mElevatorService;
    private final IMainViewModel mMainViewModel;
    private int mNumberOfElevators;
    private int mNumberOfFloors;

    public ElevatorController(IElevatorWrapper elevatorService, IMainViewModel model) {
        mElevatorService = elevatorService;
        mMainViewModel = model;
      
        try {
          mMainViewModel.setConnectionState(true);
          startController();
        } catch(Exception e) {}      
    }

    private void initFloors() {
        try {
            mNumberOfFloors = mElevatorService.getFloorNumWrapped();
        } catch (RemoteException e) {
        	tryReconnect();
        }

        mMainViewModel.getFloorsModel().setNumberOfFloors(mNumberOfFloors);
    }

    private void initElevators() {
        try {
            mNumberOfElevators = mElevatorService.getElevatorNumWrapped();
        } catch (RemoteException e) {
        	tryReconnect();
        }

        mMainViewModel.getElevatorModels().clear();
        for (int i = 0; i < mNumberOfElevators; i++) {
            mMainViewModel.addElevatorModel(new ElevatorViewModel(i, mNumberOfFloors, this));
        }
    }
    
    @FunctionalInterface
    private interface ElevatorCommand<T> {
        public T execute() throws RemoteException;
    }
    
    private <T> T runElevatorCommand(ElevatorCommand<T> command) throws Exception{    	
    	long clockTickStart = 0;
    	long clockTickEnd = 0;
    	long diff = 0;
    	try {
    		clockTickStart = mElevatorService.getClockTickWrapped();
    		var ret = command.execute();
    		clockTickEnd = mElevatorService.getClockTickWrapped();
    		diff = clockTickStart - clockTickEnd;
    		
    		if (diff < MAX_TICK_DIFF) {
				throw new RuntimeException("Runtime Exception: updateGUI: Clock tick mismatch.");
    		}
    		return ret;
    	}catch (Exception e) {
    		throw e;
    	}
	}

    private void subhandlerGUI(int i, ArrayList<ElevatorViewModel> elevators) throws Exception {

        int speed = runElevatorCommand(()->mElevatorService.getElevatorSpeedWrapped(i));
        elevators.get(i).setSpeed(speed);

        int floor = runElevatorCommand(()->mElevatorService.getElevatorFloorWrapped(i));
        elevators.get(i).setFloor(floor);

        int weight = runElevatorCommand(()->mElevatorService.getElevatorWeightWrapped(i));
        elevators.get(i).setPayload(weight);

        int pos = runElevatorCommand(()->mElevatorService.getElevatorPositionWrapped(i));
        elevators.get(i).setPosition(pos);

        int state = runElevatorCommand(()->mElevatorService.getElevatorDoorStatusWrapped(i));
        if (state == IElevator.ELEVATOR_DOORS_OPEN) {
            elevators.get(i).setDoorState(DoorState.OPEN);
        } else if (state == IElevator.ELEVATOR_DOORS_CLOSED) {
            elevators.get(i).setDoorState(DoorState.CLOSED);
        }

        int dir = runElevatorCommand(()->mElevatorService.getCommittedDirectionWrapped(i));
        if (dir == IElevator.ELEVATOR_DIRECTION_UP) {
            elevators.get(i).setDirection(Direction.UP);
        } else if (dir == IElevator.ELEVATOR_DIRECTION_DOWN) {
            elevators.get(i).setDirection(Direction.DOWN);
        } else {
        	elevators.get(i).setDirection(Direction.UNCOMMITED);
        }

        ArrayList<Integer> disabledFloors = new ArrayList<>();
        for (int j = 0; j < mNumberOfFloors; j++) {
        	final int jIndex = j;
            boolean isServiced = runElevatorCommand(()->mElevatorService.getServicesFloorsWrapped(i, jIndex));
            if (!isServiced) {
            	disabledFloors.add(j);
            }
        }
        elevators.get(i).setDisabledFloors(disabledFloors);
        
        ArrayList<Integer> pressedFloors = new ArrayList<>();
        for (int j = 0; j < mNumberOfFloors; j++) {
        	final int jIndex = j;
            boolean isPressed = runElevatorCommand(()->mElevatorService.getElevatorButtonWrapped(i, jIndex));
            if (isPressed) {
            	pressedFloors.add(j);
            }
        }
        elevators.get(i).setPressedButtons(pressedFloors);
    }

    private void updateGUI() {
        try {
        	ArrayList<ElevatorViewModel> elevators = mMainViewModel.getElevatorModels();
            
            for (int i = 0; i < mNumberOfElevators; i++) {
                subhandlerGUI(i, elevators);
            }

            ArrayList<Integer> ups = new ArrayList<>();
            ArrayList<Integer> downs = new ArrayList<>();
            for (int i = 0; i < mNumberOfFloors; i++) {
            	final int index = i;
                if (runElevatorCommand(()->mElevatorService.getFloorButtonDownWrapped(index))) {
                    downs.add(i);
                }
                if (runElevatorCommand(()->mElevatorService.getFloorButtonUpWrapped(index))) {
                    ups.add(i);
                }
            }
            if (mMainViewModel.getFloorsModel() != null) {
                mMainViewModel.getFloorsModel().setFloorsDOWN(downs);
                mMainViewModel.getFloorsModel().setFloorsUP(ups);
            }          
        } catch (RemoteException e) {
        	tryReconnect();
        } catch (Exception e) {
        	mMainViewModel.addLogText(e.getMessage());
        }
    }
    
    public void startController() {
    	initFloors();
        initElevators();
        
        mTimer = new Timer();
        
    	updateGUI();
    	TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateGUI());
            }
        };
        mTimer.scheduleAtFixedRate(timerTask, 0, TIMER_INTERVAL);
    }

    @Override
    public void handleElevatorPositionChange(int elevatorNumber, int floorNumber) {
    	mMainViewModel.addLogText("Elevator " + elevatorNumber + " drives to floor " + floorNumber);
        try {
            mElevatorService.setTargetWrapped(elevatorNumber, floorNumber);
        } catch (Exception e) {
        	tryReconnect();
        }
    }
    
    @Override
    public void doConnect() {
    	mMainViewModel.addLogText("Trying to connect...");
    	try {
			mElevatorService.reconnect();
			mMainViewModel.setConnectionState(true);
			mMainViewModel.addLogText("Successfully connected");
			this.startController();
		} catch (Exception e) {
			mMainViewModel.addLogText("Fatal Connection Error in Elevator Controller");
			mMainViewModel.setConnectionState(false);
		}
    }
    
    private void tryReconnect() {
        try {
        	mTimer.cancel();
        	mTimer.purge();
        	mMainViewModel.setConnectionState(false);
			mElevatorService.reconnect();
			mMainViewModel.setConnectionState(true);
			this.startController();
		} catch (Exception e1) {
			mMainViewModel.addLogText("Fatal Connection Error in Elevator Controller");
			mMainViewModel.setConnectionState(false);
		}
    }
}