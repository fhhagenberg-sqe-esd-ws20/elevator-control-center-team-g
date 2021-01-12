package at.fhhagenberg.sqelevator.mock;

import sqelevator.IElevator;
import sqelevator.IElevatorWrapper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MockElevator implements IElevatorWrapper {
	
	// Private member variables
    private final List<MockElevatorState> m_elevators;
    private final List<MockFloorState> m_floors;
    private final int m_number_of_elevators;
    private final int m_number_of_floors;
    private final int m_floor_height;

    // CTOR
    public MockElevator(int number_elevators, int number_floors, int floor_height, int capacity) {
        m_elevators = new ArrayList<MockElevatorState>();
        m_floors = new ArrayList<MockFloorState>();
    	m_number_of_elevators = number_elevators;
    	m_number_of_floors = number_floors;
    	m_floor_height = floor_height;
    	
        for (int i = 0; i < number_floors; i++) {
        	m_floors.add(new MockFloorState());
        }

        for (int i = 0; i < number_elevators; i++) {
            var elevator = new MockElevatorState(number_floors);
            elevator.setAcceleration(MockElevatorConstants.ACCELERATION);
            elevator.setSpeed(MockElevatorConstants.SPEED);
            elevator.setWeight(MockElevatorConstants.WEIGHT);
            elevator.setCapacity(capacity);
            m_elevators.add(elevator);
        }
    }
    
    // ----------------- Public functions -----------------
    public void reconnect() {
    	
    }
    
    public List<MockElevatorState> getElevators() {
        return m_elevators;  
    }

    public List<MockFloorState> getFloors() {
        return m_floors;
    }
    
    // ----------------- Helper functions -----------------
    
    private void checkElevatorNumber(int num) throws MockElevatorException {
        if (num < 0 || num >= m_number_of_elevators) {
            throw new MockElevatorException("Invalid MockElevatorState!");
        }
    }

    private void checkFloorNumber(int num) throws MockElevatorException {
        if (num < 0 || num >= m_number_of_floors) {
            throw new MockElevatorException("Invalid MockFloorState!");
        }
    }

    private void checkDirection(int dir) throws MockElevatorException {
        if (dir != IElevator.ELEVATOR_DIRECTION_DOWN &&
    		dir != IElevator.ELEVATOR_DIRECTION_UNCOMMITTED &&
    		dir != IElevator.ELEVATOR_DIRECTION_UP) {
        	throw new MockElevatorException("Invalid direction for the elevator!");
        }
    }
    
    // ----------------- Overwritten functions -----------------
    
    @Override
    public int getElevatorWeight(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getWeight();
    }

    @Override
    public int getElevatorCapacity(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getCapacity();
    }

    @Override
    public boolean getFloorButtonDown(int floor) throws RemoteException {
        checkFloorNumber(floor);
        return m_floors.get(floor).getDownButtonState();
    }

    @Override
    public boolean getFloorButtonUp(int floor) throws RemoteException {
        checkFloorNumber(floor);
        return m_floors.get(floor).getUpButtonState();
    }

    @Override
    public int getCommittedDirection(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getDirection();
    }

    @Override
    public int getElevatorAccel(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getAcceleration();
    }

    @Override
    public boolean getElevatorButton(int num, int floor) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(floor);
        return m_elevators.get(num).isFloorButtonActive(floor);
    }

    @Override
    public int getElevatorDoorStatus(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getDoorStatus();
    }

    @Override
    public int getElevatorFloor(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getCurrentFloor();
    }

    @Override
    public int getElevatorNum() throws RemoteException {
        return m_number_of_elevators;
    }

    @Override
    public int getElevatorPosition(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getCurrentFloor() * m_floor_height;
    }

    @Override
    public int getElevatorSpeed(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getSpeed();
    }

    @Override
    public int getFloorHeight() throws RemoteException {
        return m_floor_height;
    }

    @Override
    public int getFloorNum() throws RemoteException {
        return m_number_of_floors;
    }

    @Override
    public boolean getServicesFloors(int num, int floor) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(floor);
        return m_elevators.get(num).getServicesFloors(floor);
    }

    @Override
    public int getTarget(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getTargetFloor();
    }

    @Override
    public void setCommittedDirection(int num, int dir) throws RemoteException {
        checkElevatorNumber(num);
        checkDirection(dir);
        m_elevators.get(num).setDirection(dir);
    }

    @Override
    public void setServicesFloors(int num, int floor, boolean service) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(floor);
        m_elevators.get(num).setServicesFloors(floor, service);
    }

    @Override
    public void setTarget(int num, int target) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(target);
        m_elevators.get(num).setTargetFloor(target);
        m_elevators.get(num).setCurrentFloor(target);
    }

    @Override
    public long getClockTick() throws RemoteException {
        return MockElevatorConstants.CLOCK_TICK;
    }  
}