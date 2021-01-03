package at.fhhagenberg.sqelevator.mock;

import at.fhhagenberg.sqelevator.IElevator;
import java.util.ArrayList;
import java.util.List;

public class MockElevatorState {
	
	// Private member variables
    private List<Boolean> m_serviced_floors;
    private List<Boolean> m_floor_buttons;
    private int m_direction = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
    private int m_state_doors = IElevator.ELEVATOR_DOORS_CLOSED;
    private int m_acceleration = 0;
    private int m_current_floor = 0;
    private int m_target_floor = 0;
    private int m_speed = 0;
    private int m_weight = 0;
    private int m_capacity = 0;
    private int m_number_of_floors = 0;    

	// CTOR
    public MockElevatorState(int number_of_floors) {
        m_number_of_floors = number_of_floors;
        m_serviced_floors = new ArrayList<>(number_of_floors);
        m_floor_buttons = new ArrayList<>(number_of_floors);
        
        for (int i = 0; i < number_of_floors; i++) {
        	m_serviced_floors.add(i, true);
        	m_floor_buttons.add(i, false);
        }
    }

    public int getTargetFloor() {
        return m_target_floor;
    }
    
    public int getCurrentFloor() {
        return m_current_floor;
    }
    
    public int getAcceleration() {
        return m_acceleration;
    }
    
    public int getDirection() {
        return m_direction;
    }
    
    public int getDoorStatus() {
        return m_state_doors;
    }
    
    public int getCapacity() {
        return m_capacity;
    }
    
    public int getWeight() {
        return m_weight;
    }
    
    public int getSpeed() {
        return m_speed;
    }

    public void setTargetFloor(int floor) {
        m_target_floor = floor;
    }
    
    public void setDirection(int dir) {
        m_direction = dir;
    }

    public void setAcceleration(int acc) {
        m_acceleration = acc;
    }
    
    public void setCurrentFloor(int current_floor) {
        m_current_floor = current_floor;
    }

    public void setSpeed(int speed) {
        m_speed = speed;
    }

    public void setWeight(int weight) {
        m_weight = weight;
    }

    public void setCapacity(int cap) {
        m_capacity = cap;
    }
    
    public void setDoorStatus(int state) throws MockElevatorException {
        checkDoorStatus(state);
        m_state_doors = state;
    }

    public boolean isFloorButtonActive(int floor) throws MockElevatorException {
        checkFloorNumber(floor);
        return m_floor_buttons.get(floor);
    }

    public void setFloorButtonActive(int floor, boolean active) throws MockElevatorException {
        checkFloorNumber(floor);
        m_floor_buttons.set(floor, active);
    }

    public boolean getServicesFloors(int floor) throws MockElevatorException {
        checkFloorNumber(floor);
        return m_serviced_floors.get(floor);
    }

    public void setServicesFloors(int floor, boolean service) throws MockElevatorException {
        checkFloorNumber(floor);
        m_serviced_floors.set(floor, service);
    }

    private void checkFloorNumber(int num) throws MockElevatorException {
        if (num < 0 || num >= m_number_of_floors) {
            throw new MockElevatorException("Invalid floor state!");
        }
    }
    
    private void checkDoorStatus(int status) throws MockElevatorException {
        if (status != IElevator.ELEVATOR_DOORS_OPEN &&
        	status != IElevator.ELEVATOR_DOORS_CLOSED &&
			status != IElevator.ELEVATOR_DOORS_OPENING &&
			status != IElevator.ELEVATOR_DOORS_CLOSING) {
            throw new MockElevatorException("Invalid door state!");
        }
    }
}