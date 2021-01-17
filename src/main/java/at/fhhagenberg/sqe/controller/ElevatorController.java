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
import java.util.Vector;

public class ElevatorController implements IElevatorController {
    private static final int TIMER_INTERVAL = 100;
    private Timer m_timer;
    private TimerTask m_timer_task;
    private final IElevatorWrapper m_elevator_service;
    private final IMainViewModel m_main_view_model;
    private int m_number_of_elevators;
    private int m_number_of_floors;

    public ElevatorController(IElevatorWrapper elevator_service, IMainViewModel model) {
        m_elevator_service = elevator_service;
        m_main_view_model = model;
        m_main_view_model.setConnectionState(true);
        m_timer = new Timer();

        initFloors();
        initElevators();
    }

    private void initFloors() {
        try {
            m_number_of_floors = m_elevator_service.getFloorNum();
        } catch (RemoteException e) {
        	tryReconnect();
        }

        m_main_view_model.getFloorsModel().setNumberOfFloors(m_number_of_floors);
    }

    private void initElevators() {
        try {
            m_number_of_elevators = m_elevator_service.getElevatorNum();
        } catch (RemoteException e) {
        	tryReconnect();
        }


        for (int i = 0; i < m_number_of_elevators; i++) {
            m_main_view_model.addElevatorModel(new ElevatorViewModel(i, m_number_of_floors, this));
        }
    }

    private void subhandlerGUI(int i, ArrayList<ElevatorViewModel> elevators) throws Exception {

        int speed = m_elevator_service.getElevatorSpeed(i);
        elevators.get(i).setSpeed(speed);

        int floor = m_elevator_service.getElevatorFloor(i);
        elevators.get(i).setFloor(floor);

        int weight = m_elevator_service.getElevatorWeight(i);
        elevators.get(i).setPayload(weight);

        int pos = m_elevator_service.getElevatorPosition(i);
        elevators.get(i).setPosition(pos);

        int state = m_elevator_service.getElevatorDoorStatus(i);
        if (state == IElevator.ELEVATOR_DOORS_OPEN) {
            elevators.get(i).setDoorState(DoorState.open);
        } else if (state == IElevator.ELEVATOR_DOORS_CLOSED) {
            elevators.get(i).setDoorState(DoorState.closed);
        }

        int dir = m_elevator_service.getCommittedDirection(i);
        if (dir == IElevator.ELEVATOR_DIRECTION_UP) {
            elevators.get(i).setDirection(Direction.up);
        } else if (dir == IElevator.ELEVATOR_DIRECTION_DOWN) {
            elevators.get(i).setDirection(Direction.down);
        } else {
        	elevators.get(i).setDirection(Direction.uncommited);
        }

        Vector<Integer> disabled_floors = new Vector<>();
        for (int j = 0; j < m_number_of_floors; j++) {
            boolean is_serviced = m_elevator_service.getServicesFloors(i, j);
            if (!is_serviced) {
                disabled_floors.add(j);
            }
        }
        elevators.get(i).setDisabledFloors(disabled_floors);
        
        Vector<Integer> pressed_floors = new Vector<>();
        for (int j = 0; j < m_number_of_floors; j++) {
            boolean is_pressed = m_elevator_service.getElevatorButton(i, j);
            if (is_pressed) {
            	pressed_floors.add(j);
            }
        }
        elevators.get(i).setPressedButtons(pressed_floors);

    }

    private void updateGUI() {
        try {
            ArrayList<ElevatorViewModel> elevators = m_main_view_model.getElevatorModels();

            for (int i = 0; i < m_number_of_elevators; i++) {
                subhandlerGUI(i, elevators);
            }

            Vector<Integer> ups = new Vector<Integer>();
            Vector<Integer> downs = new Vector<Integer>();
            for (int i = 0; i < m_number_of_floors; i++) {
                if (m_elevator_service.getFloorButtonDown(i)) {
                    downs.add(i);
                }
                if (m_elevator_service.getFloorButtonUp(i)) {
                    ups.add(i);
                }
            }
            if (m_main_view_model.getFloorsModel() != null) {
                m_main_view_model.getFloorsModel().setFloorsDOWN(downs);
                m_main_view_model.getFloorsModel().setFloorsUP(ups);
            }
            m_main_view_model.setConnectionState(true);
        } catch (RemoteException e) {
        	tryReconnect();
        } catch (Exception e) {
        	System.out.println("Fatal unknown Error in Elevator Controller");
        }
    }
    
    public void startController() {
    	updateGUI();
    	m_timer_task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateGUI());
            }
        };
        m_timer.scheduleAtFixedRate(m_timer_task, 0, TIMER_INTERVAL);
    }

    @Override
    public void handleElevatorPositionChange(int elevator_number, int floor_number) {
        System.out.println("Elevator " + elevator_number + " drives to floor " + floor_number);
        try {
            m_elevator_service.setTarget(elevator_number, floor_number);
        } catch (Exception e) {
        	tryReconnect();
        }
    }
    
    private void tryReconnect() {
        try {
        	//m_timer.cancel();
        	m_timer.purge();
        	m_main_view_model.setConnectionState(false);
			m_elevator_service.reconnect();
		} catch (Exception e1) {
			System.out.println("Fatal Connection Error in Elevator Controller");
		}
    }
}