package at.fhhagenberg.sqe.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import at.fhhagenberg.sqe.controller.IElevatorController;
import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.helper.ModeState;


public class ElevatorViewModel extends Observable {

	int mId = 0;
	int mSpeed = 0;
    ModeState mModeState = ModeState.AUTOMATIC;
    int mFloor = 1;
    DoorState mDoorState = DoorState.CLOSED;
    Direction mDirection = Direction.DOWN;
    List<Integer> mDisabledFloors = new ArrayList<>();
    int mNumberOfFloors = 5;
    int mPayload = 10;
    int mPosition = 1;
    List<Integer> mPressedButtons = new ArrayList<>();
    
    IElevatorController mEc;
    
    public ElevatorViewModel(int id, int numberOfFloors, IElevatorController ec) {
    	mId = id;
    	mNumberOfFloors = numberOfFloors;
    	mEc = ec;
    }
    
    public int getId() { return mId; }
    public int getSpeed() { return mSpeed; }
    public ModeState getModeState() { return mModeState; }
    public int getFloor() { return mFloor; }
    public DoorState getDoorState() { return mDoorState; }
    public Direction getDirection() { return mDirection; }
    public List<Integer> getDisabledFloors() { return mDisabledFloors; }
    public int getNumberOfFloors() { return mNumberOfFloors; }
    public int getPayload() { return mPayload; }
    public int getPosition() { return mPosition; }
    public List<Integer> getPressedButtons() { return mPressedButtons; }
    
    public void setId(int id) {
    	mId = id;
    	updateView();
    }
    public void setSpeed(int speed) {
    	mSpeed = speed;
    	updateView();
    }
    public void setModeState(ModeState state) {
    	mModeState = state;
    	updateView();
    }
    public void setFloor(int floor) {
    	mFloor = floor;
    	updateView();
    }
    public void setDoorState(DoorState state) {
    	mDoorState = state;
    	updateView();
    }
    public void setDirection(Direction direction) {
    	mDirection = direction;
    	updateView();
    }
    public void setDisabledFloors(List<Integer> vec) {
    	mDisabledFloors = vec;
    	updateView();
    }
    public void setPayload(int payload) {
    	mPayload = payload;
    	updateView();
    }
    public void setPosition(int pos) { 
    	mPosition = pos; 
    	updateView();
    }
    public void setPressedButtons(List<Integer> vec) {
    	mPressedButtons = vec;
    	updateView();
    }
    
    public void changeMode() {
    	if(mModeState == ModeState.MANUAL)
    		mModeState = ModeState.AUTOMATIC;
    	else
    		mModeState = ModeState.MANUAL;
    	
    	updateView();
    }
    
    public void clickedFloor(int num) {
    	if (mModeState == ModeState.MANUAL && !mDisabledFloors.contains(num)) {
        	mEc.handleElevatorPositionChange(mId, num);	
    	}
    }
    
    public void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
