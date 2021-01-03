package at.fhhagenberg.sqe.viewmodel;

import java.util.Observable;
import java.util.Vector;

import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.helper.ModeState;


public class ElevatorViewModel extends Observable {

	int id = 0;
	float speed = 0;
    ModeState modeState = ModeState.manual;
    int floor = 0;
    DoorState doorState = DoorState.closed;
    Direction direction = Direction.down;
    Vector<Integer> disabled_floors = new Vector<Integer>();
    int number_of_floors = 5;
    int payload = 10;
    
    public ElevatorViewModel(int _id) {
    	id = _id;
    }
    
    public int getId() { return id; }
    public float getSpeed() { return speed; }
    public ModeState getModeState() { return modeState; }
    public int getFloor() { return floor; }
    public DoorState getDoorState() { return doorState; }
    public Direction getDirection() { return direction; }
    public Vector<Integer> getDisabledFloors() { return disabled_floors; }
    public int getNumberOfFloors() { return number_of_floors; }
    public int getPayload() { return payload; }
    
    public void changeMode() {
    	if(modeState == ModeState.manual)
    		modeState = ModeState.automatic;
    	else
    		modeState = ModeState.manual;
    	
    	updateView();
    }
    
    public void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
