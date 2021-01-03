package at.fhhagenberg.sqelevator.mock;

import at.fhhagenberg.sqelevator.IElevator;
import java.util.ArrayList;
import java.util.List;

public class MockElevatorState {
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

    public MockElevatorState(int numFloors) {
        m_number_of_floors = numFloors;
        m_serviced_floors = new ArrayList<>(numFloors);
        m_floor_buttons = new ArrayList<>(numFloors);

        for (int i = 0; i < numFloors; i++) {
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
    
    public void setDirection(int direction) {
        m_direction = direction;
    }

    public void setAcceleration(int acceleration) {
        m_acceleration = acceleration;
    }
    
    public void setCurrentFloor(int currentFloor) {
        m_current_floor = currentFloor;
    }

    public void setSpeed(int speed) {
        m_speed = speed;
    }

    public void setWeight(int weight) {
        m_weight = weight;
    }

    public void setCapacity(int capacity) {
        m_capacity = capacity;
    }
    
    public void setDoorStatus(int doorStatus) throws MockElevatorException {
        checkDoorStatus(doorStatus);
        m_state_doors = doorStatus;
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

    private void checkFloorNumber(int floorNumber) throws MockElevatorException {
        if (floorNumber < 0 || floorNumber >= m_number_of_floors) {
            throw new MockElevatorException("MockFloorState number is invalid!");
        }
    }
    
    private void checkDoorStatus(int status) throws MockElevatorException {
        if (status != IElevator.ELEVATOR_DOORS_OPEN &&
        	status != IElevator.ELEVATOR_DOORS_CLOSED &&
			status != IElevator.ELEVATOR_DOORS_OPENING &&
			status != IElevator.ELEVATOR_DOORS_CLOSING) {
            throw new MockElevatorException("Door status is invalid!");
        }
    }
}