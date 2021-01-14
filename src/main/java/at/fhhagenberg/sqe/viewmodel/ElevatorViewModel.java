package at.fhhagenberg.sqe.viewmodel;

import java.util.Observable;
import java.util.Vector;

import at.fhhagenberg.sqe.controller.IElevatorController;
import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.helper.ModeState;


public class ElevatorViewModel extends Observable {

	int id = 0;
	int speed = 0;
    ModeState modeState = ModeState.automatic;
    int floor = 1;
    DoorState doorState = DoorState.closed;
    Direction direction = Direction.down;
    Vector<Integer> disabled_floors = new Vector<Integer>();
    int number_of_floors = 5;
    int payload = 10;
    int position = 1;
    Vector<Integer> pressed_buttons = new Vector<Integer>();
    
    IElevatorController ec;
    
    public ElevatorViewModel(int _id, int _numberOfFloors, IElevatorController _ec) {
    	id = _id;
    	number_of_floors = _numberOfFloors;
    	ec = _ec;
    }
    
    public int getId() { return id; }
    public int getSpeed() { return speed; }
    public ModeState getModeState() { return modeState; }
    public int getFloor() { return floor; }
    public DoorState getDoorState() { return doorState; }
    public Direction getDirection() { return direction; }
    public Vector<Integer> getDisabledFloors() { return disabled_floors; }
    public int getNumberOfFloors() { return number_of_floors; }
    public int getPayload() { return payload; }
    public int getPosition() { return position; }
    public Vector<Integer> getPressedButtons() { return pressed_buttons; }
    
    public void setId(int _id) {
    	id = _id;
    	updateView();
    }
    public void setSpeed(int _speed) {
    	speed = _speed;
    	updateView();
    }
    public void setModeState(ModeState _state) {
    	modeState = _state;
    	updateView();
    }
    public void setFloor(int _floor) {
    	floor = _floor;
    	updateView();
    }
    public void setDoorState(DoorState _state) {
    	doorState = _state;
    	updateView();
    }
    public void setDirection(Direction _direction) {
    	direction = _direction;
    	updateView();
    }
    public void setDisabledFloors(Vector<Integer> _vec) {
    	disabled_floors = _vec;
    	updateView();
    }
    public void setPayload(int _payload) {
    	payload = _payload;
    	updateView();
    }
    public void setPosition(int _pos) { 
    	position = _pos; 
    	updateView();
    }
    public void setPressedButtons(Vector<Integer> _vec) {
    	pressed_buttons = _vec;
    	updateView();
    }
    
    public void changeMode() {
    	if(modeState == ModeState.manual)
    		modeState = ModeState.automatic;
    	else
    		modeState = ModeState.manual;
    	
    	updateView();
    }
    
    public void clickedFloor(int num) {
    	if (modeState == ModeState.manual && !disabled_floors.contains(num)) {
        	ec.handleElevatorPositionChange(id, num);	
    	}
    }
    
    public void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
