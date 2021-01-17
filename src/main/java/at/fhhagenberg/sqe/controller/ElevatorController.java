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

public class ElevatorController implements IElevatorController {
    private static final int TIMER_INTERVAL = 250;
    private Timer m_timer;
    private TimerTask m_timerTask;
    private final IElevatorWrapper m_elevatorService;
    private final IMainViewModel m_mainViewModel;
    private int m_numberOfElevators;
    private int m_numberOfFloors;

    public ElevatorController(IElevatorWrapper elevator_service, IMainViewModel model) {
        m_elevatorService = elevator_service;
        m_mainViewModel = model;
        m_mainViewModel.setConnectionState(true);
        m_timer = new Timer();

        initFloors();
        initElevators();
    }

    private void initFloors() {
        try {
            m_numberOfFloors = m_elevatorService.getFloorNum();
        } catch (RemoteException e) {
        	tryReconnect();
        }

        m_mainViewModel.getFloorsModel().setNumberOfFloors(m_numberOfFloors);
    }

    private void initElevators() {
        try {
            m_numberOfElevators = m_elevatorService.getElevatorNum();
        } catch (RemoteException e) {
        	tryReconnect();
        }


        for (int i = 0; i < m_numberOfElevators; i++) {
            m_mainViewModel.addElevatorModel(new ElevatorViewModel(i, m_numberOfFloors, this));
        }
    }

    private void subhandlerGUI(int i, ArrayList<ElevatorViewModel> elevators) throws Exception {

        int speed = m_elevatorService.getElevatorSpeed(i);
        elevators.get(i).setSpeed(speed);

        int floor = m_elevatorService.getElevatorFloor(i);
        elevators.get(i).setFloor(floor);

        int weight = m_elevatorService.getElevatorWeight(i);
        elevators.get(i).setPayload(weight);

        int pos = m_elevatorService.getElevatorPosition(i);
        elevators.get(i).setPosition(pos);

        int state = m_elevatorService.getElevatorDoorStatus(i);
        if (state == IElevator.ELEVATOR_DOORS_OPEN) {
            elevators.get(i).setDoorState(DoorState.open);
        } else if (state == IElevator.ELEVATOR_DOORS_CLOSED) {
            elevators.get(i).setDoorState(DoorState.closed);
        }

        int dir = m_elevatorService.getCommittedDirection(i);
        if (dir == IElevator.ELEVATOR_DIRECTION_UP) {
            elevators.get(i).setDirection(Direction.up);
        } else if (dir == IElevator.ELEVATOR_DIRECTION_DOWN) {
            elevators.get(i).setDirection(Direction.down);
        } else {
        	elevators.get(i).setDirection(Direction.uncommited);
        }

        ArrayList<Integer> disabled_floors = new ArrayList<Integer>();
        for (int j = 0; j < m_numberOfFloors; j++) {
            boolean is_serviced = m_elevatorService.getServicesFloors(i, j);
            if (!is_serviced) {
                disabled_floors.add(j);
            }
        }
        elevators.get(i).setDisabledFloors(disabled_floors);
        
        ArrayList<Integer> pressed_floors = new ArrayList<Integer>();
        for (int j = 0; j < m_numberOfFloors; j++) {
            boolean isPressed = m_elevatorService.getElevatorButton(i, j);
            if (isPressed) {
            	pressed_floors.add(j);
            }
        }
        elevators.get(i).setPressedButtons(pressed_floors);

    }

    private void updateGUI() {
        try {
            ArrayList<ElevatorViewModel> elevators = m_mainViewModel.getElevatorModels();

            for (int i = 0; i < m_numberOfElevators; i++) {
                subhandlerGUI(i, elevators);
            }

            ArrayList<Integer> ups = new ArrayList<Integer>();
            ArrayList<Integer> downs = new ArrayList<Integer>();
            for (int i = 0; i < m_numberOfFloors; i++) {
                if (m_elevatorService.getFloorButtonDown(i)) {
                    downs.add(i);
                }
                if (m_elevatorService.getFloorButtonUp(i)) {
                    ups.add(i);
                }
            }
            if (m_mainViewModel.getFloorsModel() != null) {
                m_mainViewModel.getFloorsModel().setFloorsDOWN(downs);
                m_mainViewModel.getFloorsModel().setFloorsUP(ups);
            }
            m_mainViewModel.setConnectionState(true);
        } catch (RemoteException e) {
        	tryReconnect();
        } catch (Exception e) {
        	System.out.println("Fatal unknown Error in Elevator Controller");
        }
    }
    
    public void startController() {
    	updateGUI();
    	m_timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateGUI());
            }
        };
        m_timer.scheduleAtFixedRate(m_timerTask, 0, TIMER_INTERVAL);
    }

    @Override
    public void handleElevatorPositionChange(int elevator_number, int floor_number) {
        System.out.println("Elevator " + elevator_number + " drives to floor " + floor_number);
        try {
            m_elevatorService.setTarget(elevator_number, floor_number);
        } catch (Exception e) {
        	tryReconnect();
        }
    }
    
    private void tryReconnect() {
        try {
        	//m_timer.cancel();
        	m_timer.purge();
        	m_mainViewModel.setConnectionState(false);
			m_elevatorService.reconnect();
		} catch (Exception e) {
			System.out.println("Fatal Connection Error in Elevator Controller");
		}
    }
}