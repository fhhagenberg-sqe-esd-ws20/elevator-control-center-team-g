package sqelevator;

import at.fhhagenberg.sqe.connection.ConnectionException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ElevatorWrapper implements IElevatorWrapper {
    private IElevator mElevator;

    public ElevatorWrapper(IElevator elevator) throws NullPointerException {
        mElevator = elevator;
    }      
    
    @Override
    public void reconnect() throws ConnectionException {
    	boolean connected = false;
    	String exceptionMessage = "";
    	
    	for (int i = 0; i < 3; i++) {
            try {
            	mElevator = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
            	connected = true;
            } catch (RemoteException | NotBoundException | MalformedURLException e) {
            	exceptionMessage = e.getMessage();
            }            
            if(connected)break;
            //Thread.sleep(1000);
    	}   
    	if(!connected) throw new ConnectionException("Connection error with RMI: " + exceptionMessage);
    }

    /**
     * Retrieves the committed direction of the specified elevator (up / down / uncommitted).
     *
     * @param elevatorNumber - elevator number whose committed direction is being retrieved
     * @return the current direction of the specified elevator where up=0, down=1 and uncommitted=2
     */
    @Override
    public int getCommittedDirectionWrapped(int elevatorNumber) throws java.rmi.RemoteException {
        return mElevator.getCommittedDirection(elevatorNumber);
    }

    /**
     * Provides the current acceleration of the specified elevator in feet per sec^2.
     *
     * @param elevatorNumber - elevator number whose acceleration is being retrieved
     * @return returns the acceleration of the indicated elevator where positive speed is acceleration and negative is deceleration
     */
    @Override
    public int getElevatorAccelWrapped(int elevatorNumber) throws RemoteException {
        return mElevator.getElevatorAccel(elevatorNumber);
    }

    /**
     * Provides the status of a floor request button on a specified elevator (on/off).
     *
     * @param elevatorNumber - elevator number whose button status is being retrieved
     * @param floor          - floor number button being checked on the selected elevator
     * @return returns boolean to indicate if floor button on the elevator is active (true) or not (false)
     */
    @Override
    public boolean getElevatorButtonWrapped(int elevatorNumber, int floor) throws RemoteException {
        return mElevator.getElevatorButton(elevatorNumber, floor);
    }

    /**
     * Provides the current status of the doors of the specified elevator (open/closed).
     *
     * @param elevatorNumber - elevator number whose door status is being retrieved
     * @return returns the door status of the indicated elevator where 1=open and 2=closed
     */
    @Override
    public int getElevatorDoorStatusWrapped(int elevatorNumber) throws java.rmi.RemoteException {
       return mElevator.getElevatorDoorStatus(elevatorNumber);
    }

    /**
     * Provides the current location of the specified elevator to the nearest floor
     *
     * @param elevatorNumber - elevator number whose location is being retrieved
     * @return returns the floor number of the floor closest to the indicated elevator
     */
    @Override
    public int getElevatorFloorWrapped(int elevatorNumber) throws RemoteException {
        return mElevator.getElevatorFloor(elevatorNumber);
    }

    /**
     * Retrieves the number of elevators in the building.
     *
     * @return total number of elevators
     */
    @Override
    public int getElevatorNumWrapped() throws RemoteException {
        return mElevator.getElevatorNum();
    }

    /**
     * Provides the current location of the specified elevator in feet from the bottom of the building.
     *
     * @param elevatorNumber - elevator number whose location is being retrieved
     * @return returns the location in feet of the indicated elevator from the bottom of the building
     */
    @Override
    public int getElevatorPositionWrapped(int elevatorNumber) throws RemoteException {
        return mElevator.getElevatorPosition(elevatorNumber);
    }

    /**
     * Provides the current speed of the specified elevator in feet per sec.
     *
     * @param elevatorNumber - elevator number whose speed is being retrieved
     * @return returns the speed of the indicated elevator where positive speed is up and negative is down
     */
    @Override
    public int getElevatorSpeedWrapped(int elevatorNumber) throws RemoteException {
        return mElevator.getElevatorSpeed(elevatorNumber);
    }

    /**
     * Retrieves the weight of passengers on the specified elevator.
     *
     * @param elevatorNumber - elevator number whose service is being retrieved
     * @return total weight of all passengers in lbs
     */
    @Override
    public int getElevatorWeightWrapped(int elevatorNumber) throws RemoteException {
        return mElevator.getElevatorWeight(elevatorNumber);
    }

    /**
     * Retrieves the maximum number of passengers that can fit on the specified elevator.
     *
     * @param elevatorNumber - elevator number whose service is being retrieved
     * @return number of passengers
     */
    @Override
    public int getElevatorCapacityWrapped(int elevatorNumber) throws RemoteException {
        return mElevator.getElevatorCapacity(elevatorNumber);
    }

    /**
     * Provides the status of the Down button on specified floor (on/off).
     *
     * @param floor - floor number whose Down button status is being retrieved
     * @return returns boolean to indicate if button is active (true) or not (false)
     */
    @Override
    public boolean getFloorButtonDownWrapped(int floor) throws RemoteException {
        return mElevator.getFloorButtonDown(floor);
    }

    /**
     * Provides the status of the Up button on specified floor (on/off).
     *
     * @param floor - floor number whose Up button status is being retrieved
     * @return returns boolean to indicate if button is active (true) or not (false)
     */
    @Override
    public boolean getFloorButtonUpWrapped(int floor) throws RemoteException {
        return mElevator.getFloorButtonUp(floor);
    }

    /**
     * Retrieves the height of the floors in the building.
     *
     * @return floor height (ft)
     */
    @Override
    public int getFloorHeightWrapped() throws RemoteException {
        return mElevator.getFloorHeight();
    }

    /**
     * Retrieves the number of floors in the building.
     *
     * @return total number of floors
     */
    @Override
    public int getFloorNumWrapped() throws RemoteException {
        return mElevator.getFloorNum();
    }

    /**
     * Retrieves whether or not the specified elevator will service the specified floor (yes/no).
     *
     * @param elevatorNumber elevator number whose service is being retrieved
     * @param floor          floor whose service status by the specified elevator is being retrieved
     * @return service status whether the floor is serviced by the specified elevator (yes=true,no=false)
     */
    @Override
    public boolean getServicesFloorsWrapped(int elevatorNumber, int floor) throws RemoteException {
        return mElevator.getServicesFloors(elevatorNumber, floor);
    }

    /**
     * Retrieves the floor target of the specified elevator.
     *
     * @param elevatorNumber elevator number whose target floor is being retrieved
     * @return current floor target of the specified elevator
     */
    @Override
    public int getTargetWrapped(int elevatorNumber) throws RemoteException {
        return mElevator.getTarget(elevatorNumber);
    }

    /**
     * Sets the committed direction of the specified elevator (up / down / uncommitted).
     *
     * @param elevatorNumber elevator number whose committed direction is being set
     * @param direction      direction being set where up=0, down=1 and uncommitted=2
     */
    @Override
    public void setCommittedDirectionWrapped(int elevatorNumber, int direction) throws RemoteException {
        mElevator.setCommittedDirection(elevatorNumber, direction);
    }

    /**
     * Sets whether or not the specified elevator will service the specified floor (yes/no).
     *
     * @param elevatorNumber elevator number whose service is being defined
     * @param floor          floor whose service by the specified elevator is being set
     * @param service        indicates whether the floor is serviced by the specified elevator (yes=true,no=false)
     */
    @Override
    public void setServicesFloorsWrapped(int elevatorNumber, int floor, boolean service) throws RemoteException {
        mElevator.setServicesFloors(elevatorNumber,floor,service);
    }

    /**
     * Sets the floor target of the specified elevator.
     *
     * @param elevatorNumber elevator number whose target floor is being set
     * @param target         floor number which the specified elevator is to target
     */
    @Override
    public void setTargetWrapped(int elevatorNumber, int target) throws RemoteException {
        mElevator.setTarget(elevatorNumber,target);
    }

    /**
     * Retrieves the current clock tick of the elevator control system.
     *
     * @return clock tick
     */
    @Override
    public long getClockTickWrapped() throws RemoteException {
        return mElevator.getClockTick();
    }
}
