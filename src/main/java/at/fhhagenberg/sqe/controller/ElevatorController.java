package at.fhhagenberg.sqe.controller;

import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;
import at.fhhagenberg.sqe.viewmodel.IMainViewModel;
import sqelevator.IElevator;
import sqelevator.IElevatorWrapper;
import javafx.application.Platform;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ElevatorController implements IElevatorController {
    private static final int TIMER_INTERVAL = 250;
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

    private void subhandlerGUI(int i, ArrayList<ElevatorViewModel> elevators) throws Exception {

        int speed = mElevatorService.getElevatorSpeedWrapped(i);
        elevators.get(i).setSpeed(speed);

        int floor = mElevatorService.getElevatorFloorWrapped(i);
        elevators.get(i).setFloor(floor);

        int weight = mElevatorService.getElevatorWeightWrapped(i);
        elevators.get(i).setPayload(weight);

        int pos = mElevatorService.getElevatorPositionWrapped(i);
        elevators.get(i).setPosition(pos);

        int state = mElevatorService.getElevatorDoorStatusWrapped(i);
        if (state == IElevator.ELEVATOR_DOORS_OPEN) {
            elevators.get(i).setDoorState(DoorState.OPEN);
        } else if (state == IElevator.ELEVATOR_DOORS_CLOSED) {
            elevators.get(i).setDoorState(DoorState.CLOSED);
        }

        int dir = mElevatorService.getCommittedDirectionWrapped(i);
        if (dir == IElevator.ELEVATOR_DIRECTION_UP) {
            elevators.get(i).setDirection(Direction.UP);
        } else if (dir == IElevator.ELEVATOR_DIRECTION_DOWN) {
            elevators.get(i).setDirection(Direction.DOWN);
        } else {
        	elevators.get(i).setDirection(Direction.UNCOMMITED);
        }

        ArrayList<Integer> disabledFloors = new ArrayList<>();
        for (int j = 0; j < mNumberOfFloors; j++) {
            boolean isServiced = mElevatorService.getServicesFloorsWrapped(i, j);
            if (!isServiced) {
            	disabledFloors.add(j);
            }
        }
        elevators.get(i).setDisabledFloors(disabledFloors);
        
        ArrayList<Integer> pressedFloors = new ArrayList<>();
        for (int j = 0; j < mNumberOfFloors; j++) {
            boolean isPressed = mElevatorService.getElevatorButtonWrapped(i, j);
            if (isPressed) {
            	pressedFloors.add(j);
            }
        }
        elevators.get(i).setPressedButtons(pressedFloors);
    }

    private void updateGUI() {
        try {
            ArrayList<ElevatorViewModel> elevators = mMainViewModel.getElevatorModels();
            long clockTickStart = mElevatorService.getClockTickWrapped();
            
            for (int i = 0; i < mNumberOfElevators; i++) {
                subhandlerGUI(i, elevators);
            }

            ArrayList<Integer> ups = new ArrayList<>();
            ArrayList<Integer> downs = new ArrayList<>();
            for (int i = 0; i < mNumberOfFloors; i++) {
                if (mElevatorService.getFloorButtonDownWrapped(i)) {
                    downs.add(i);
                }
                if (mElevatorService.getFloorButtonUpWrapped(i)) {
                    ups.add(i);
                }
            }
            if (mMainViewModel.getFloorsModel() != null) {
                mMainViewModel.getFloorsModel().setFloorsDOWN(downs);
                mMainViewModel.getFloorsModel().setFloorsUP(ups);
            }

            
            long clockTickEnd = mElevatorService.getClockTickWrapped();
            long diff = clockTickStart - clockTickEnd;
			if (diff != 0) {
				throw new RuntimeException("Runtime Exception: updateGUI: Clock tick mismatch.");
			}
            mMainViewModel.setConnectionState(true);
        } catch (RemoteException e) {
        	tryReconnect();
        } catch (Exception e) {
        	m_main_view_model.addLogText(e.getMessage());
        }
    }
    
    public void startController() {
    	initFloors();
        initElevators();
        
        m_timer = new Timer();
        
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
        //System.out.println("Elevator " + elevatorNumber + " drives to floor " + floorNumber);
        try {
            mElevatorService.setTargetWrapped(elevatorNumber, floorNumber);
        } catch (Exception e) {
        	tryReconnect();
        }
    }
    
    @Override
    public void doConnect() {
    	m_main_view_model.addLogText("trying to connect...");
    	try {
			m_elevator_service.reconnect();
			m_main_view_model.setConnectionState(true);
			m_main_view_model.addLogText("successfully connected");
			this.startController();
		} catch (Exception e) {
			m_main_view_model.addLogText("Fatal Connection Error in Elevator Controller");
			m_main_view_model.setConnectionState(false);
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