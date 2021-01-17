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
    public int getElevatorWeightWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getWeight();
    }

    @Override
    public int getElevatorCapacityWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getCapacity();
    }

    @Override
    public boolean getFloorButtonDownWrapped(int floor) throws RemoteException {
        checkFloorNumber(floor);
        return m_floors.get(floor).getDownButtonState();
    }

    @Override
    public boolean getFloorButtonUpWrapped(int floor) throws RemoteException {
        checkFloorNumber(floor);
        return m_floors.get(floor).getUpButtonState();
    }

    @Override
    public int getCommittedDirectionWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getDirection();
    }

    @Override
    public int getElevatorAccelWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getAcceleration();
    }

    @Override
    public boolean getElevatorButtonWrapped(int num, int floor) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(floor);
        return m_elevators.get(num).isFloorButtonActive(floor);
    }

    @Override
    public int getElevatorDoorStatusWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getDoorStatus();
    }

    @Override
    public int getElevatorFloorWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getCurrentFloor();
    }

    @Override
    public int getElevatorNumWrapped() throws RemoteException {
        return m_number_of_elevators;
    }

    @Override
    public int getElevatorPositionWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getCurrentFloor() * m_floor_height;
    }

    @Override
    public int getElevatorSpeedWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getSpeed();
    }

    @Override
    public int getFloorHeightWrapped() throws RemoteException {
        return m_floor_height;
    }

    @Override
    public int getFloorNumWrapped() throws RemoteException {
        return m_number_of_floors;
    }

    @Override
    public boolean getServicesFloorsWrapped(int num, int floor) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(floor);
        return m_elevators.get(num).getServicesFloors(floor);
    }

    @Override
    public int getTargetWrapped(int num) throws RemoteException {
        checkElevatorNumber(num);
        return m_elevators.get(num).getTargetFloor();
    }

    @Override
    public void setCommittedDirectionWrapped(int num, int dir) throws RemoteException {
        checkElevatorNumber(num);
        checkDirection(dir);
        m_elevators.get(num).setDirection(dir);
    }

    @Override
    public void setServicesFloorsWrapped(int num, int floor, boolean service) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(floor);
        m_elevators.get(num).setServicesFloors(floor, service);
    }

    @Override
    public void setTargetWrapped(int num, int target) throws RemoteException {
        checkElevatorNumber(num);
        checkFloorNumber(target);
        m_elevators.get(num).setTargetFloor(target);
        m_elevators.get(num).setCurrentFloor(target);
    }

    @Override
    public long getClockTickWrapped() throws RemoteException {
        return MockElevatorConstants.CLOCK_TICK;
    }  
}