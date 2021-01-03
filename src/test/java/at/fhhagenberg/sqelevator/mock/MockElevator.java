package at.fhhagenberg.sqelevator.mock;

import at.fhhagenberg.sqelevator.IElevator;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class MockElevator implements IElevator {
	// private member variables
    private List<MockElevatorState> m_elevators;
    private List<MockFloorState> m_floors;
    private int m_number_of_elevators;
    private int m_number_of_floors;
    private int m_floor_height;

    // CTOR
    public MockElevator(int number_elevators, int number_floors, int floor_height, int capacity) {
        m_elevators = new ArrayList<>(number_elevators);
        m_floors = new ArrayList<>(number_floors);
    	m_number_of_elevators = number_elevators;
    	m_number_of_floors = number_floors;
    	m_floor_height = floor_height;

        for (int i = 0; i < number_elevators; i++) {
            var elevator = new MockElevatorState(number_floors);
            elevator.setAcceleration(MockElevatorConstants.ELEVATOR_ACCELERATION);
            elevator.setSpeed(MockElevatorConstants.ELEVATOR_SPEED);
            elevator.setWeight(MockElevatorConstants.ELEVATOR_WEIGHT);
            elevator.setCapacity(capacity);
            m_elevators.add(elevator);
        }

        for (int i = 0; i < number_floors; i++) {
        	m_floors.add(new MockFloorState());
        }
    }
    
    @Override
    public int getElevatorWeight(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getWeight();
    }

    @Override
    public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getCapacity();
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
    public int getCommittedDirection(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getDirection();
    }

    @Override
    public int getElevatorAccel(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getAcceleration();
    }

    @Override
    public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        checkFloorNumber(floor);
        return m_elevators.get(elevatorNumber).isFloorButtonActive(floor);
    }

    @Override
    public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getDoorStatus();
    }

    @Override
    public int getElevatorFloor(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getCurrentFloor();
    }

    @Override
    public int getElevatorNum() throws RemoteException {
        return m_number_of_elevators;
    }

    @Override
    public int getElevatorPosition(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getCurrentFloor() * m_floor_height;
    }

    @Override
    public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getSpeed();
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
    public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        checkFloorNumber(floor);
        return m_elevators.get(elevatorNumber).getServicesFloors(floor);
    }

    @Override
    public int getTarget(int elevatorNumber) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        return m_elevators.get(elevatorNumber).getTargetFloor();
    }

    @Override
    public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        checkDirection(direction);
        m_elevators.get(elevatorNumber).setDirection(direction);
    }

    @Override
    public void setServicesFloors(int elevatorNumber, int floor, boolean service) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        checkFloorNumber(floor);
        m_elevators.get(elevatorNumber).setServicesFloors(floor, service);
    }

    @Override
    public void setTarget(int elevatorNumber, int target) throws RemoteException {
        checkElevatorNumber(elevatorNumber);
        checkFloorNumber(target);
        m_elevators.get(elevatorNumber).setTargetFloor(target);
        m_elevators.get(elevatorNumber).setCurrentFloor(target);
    }

    @Override
    public long getClockTick() throws RemoteException {
        return MockElevatorConstants.ELEVATOR_CLOCK_TICK;
    }
    
    private void checkElevatorNumber(int elevatorNumber) throws MockElevatorException {
        if (elevatorNumber < 0 || elevatorNumber >= m_number_of_elevators) {
            throw new MockElevatorException("Invalid MockElevatorState!");
        }
    }

    private void checkFloorNumber(int floorNumber) throws MockElevatorException {
        if (floorNumber < 0 || floorNumber >= m_number_of_floors) {
            throw new MockElevatorException("Invalid MockFloorState!");
        }
    }

    private void checkDirection(int direction) throws MockElevatorException {
        if (direction != IElevator.ELEVATOR_DIRECTION_DOWN &&
                direction != IElevator.ELEVATOR_DIRECTION_UNCOMMITTED &&
                direction != IElevator.ELEVATOR_DIRECTION_UP) {
            throw new MockElevatorException("Invalid direction number!");
        }
    }
    
    public List<MockElevatorState> getElevators() {
        return m_elevators;   
    }

    public List<MockFloorState> getFloors() {
        return m_floors;   
    }

}