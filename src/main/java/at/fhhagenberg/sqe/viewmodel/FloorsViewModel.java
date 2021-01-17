package at.fhhagenberg.sqe.viewmodel;

import at.fhhagenberg.sqe.view.FloorsView;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

public class FloorsViewModel  extends Observable implements IFloorsViewModel{

	int mNumberOfFloors = 0;	
	List<Integer> mFloorsUp = new ArrayList<>();
	List<Integer> mFloorsDown = new ArrayList<>();
	
	public void setNumberOfFloors(int num) {
		mNumberOfFloors = num;
		updateView();
	}
	
	public void setFloorsUP(List<Integer> vec) {
		mFloorsUp = vec;
    	updateView();
    }
	
	public void setFloorsDOWN(List<Integer> vec) {
		mFloorsDown = vec;
    	updateView();
    }
	
	public int getNumberOfFloors() {
		return mNumberOfFloors;
	}
	
	public List<Integer> getFloorsUP() {
		return mFloorsUp;
    }
	
	public List<Integer> getFloorsDOWN() {
		return mFloorsDown;
    }
	
	public void updateView() {
    	setChanged();
    	notifyObservers();
    }
	
    @Override
    public void addObserver(FloorsView floorsView){
		super.addObserver(floorsView);
	}
}
