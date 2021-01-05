package at.fhhagenberg.sqe.controller;

import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.IElevator;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class ElevatorController implements IElevatorController{
	private static final int TIMER_INTERVAL = 100;	
	private Timer m_timer;
	private IElevator m_elevator_service = null;
	private MainViewModel m_main_view_model = null;	
	private int m_number_of_elevators;
	private int m_number_of_floors;
	
	public ElevatorController(IElevator elevator_service, MainViewModel model) {
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
		}
		catch (RemoteException e) {
			// TODO: Error handling...
		}
		
		m_main_view_model.getFloorsModel().setNumberOfFloors(m_number_of_floors);
	}
	
	private void initElevators(){
		try {
			m_number_of_elevators = m_elevator_service.getElevatorNum();
		}
		catch (RemoteException e) {
			// TODO: Error handling...
		}
		

		for (int i = 0; i < m_number_of_elevators; i++) {
			m_main_view_model.addElevatorModel(new ElevatorViewModel(i, m_number_of_floors, this));
		}		
	}
	
	private void updateGUI() {
		try {
			//DISABLED_FLOORS
			ArrayList<ElevatorViewModel> elevators = m_main_view_model.getElevatorModels();
			for (int i = 0; i < m_number_of_elevators; i++) {
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
				}else if (state == IElevator.ELEVATOR_DOORS_CLOSED) {
					elevators.get(i).setDoorState(DoorState.closed);
				}
				
				int dir = m_elevator_service.getCommittedDirection(i);				
				if (dir == IElevator.ELEVATOR_DIRECTION_UP) {
					elevators.get(i).setDirection(Direction.up);
				}else if (dir == IElevator.ELEVATOR_DIRECTION_DOWN) {
					elevators.get(i).setDirection(Direction.down);
				}	
				
				Vector<Integer> disabled_floors = new Vector<Integer>();
				for (int j = 0; j < m_number_of_floors; j++) {
					boolean is_serviced = m_elevator_service.getServicesFloors(j, floor);
					if (!is_serviced) {
						disabled_floors.add(j);
					}
				}
				elevators.get(i).setDisabledFloors(disabled_floors);				
			}		
		}
		catch (RemoteException e) {
			// TODO: Error handling...
		}
	}
	
	public void startTimer() {
		m_timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					updateGUI();
				});
			}
		}, 0, TIMER_INTERVAL);
	}	
	
	public void handleElevatorPositionChange(int elevator_number, int floor_number) {
		System.out.println("Elevator " + Integer.toString(elevator_number) + " drives to floor " + Integer.toString(floor_number));
	}
}