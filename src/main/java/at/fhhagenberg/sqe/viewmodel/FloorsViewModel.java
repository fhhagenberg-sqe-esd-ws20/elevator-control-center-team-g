package at.fhhagenberg.sqe.viewmodel;

import at.fhhagenberg.sqe.view.FloorsView;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

public class FloorsViewModel  extends Observable implements IFloorsViewModel{

	int number_of_floors = 0;
	
	List<Integer> floorsUp = new ArrayList<>();
	List<Integer> floorsDown = new ArrayList<>();
	
	public void setNumberOfFloors(int _num) {
		number_of_floors = _num;
		updateView();
	}
	
	public void setFloorsUP(List<Integer> vec) {
		floorsUp = vec;
    	updateView();
    }
	
	public void setFloorsDOWN(List<Integer> vec) {
		floorsDown = vec;
    	updateView();
    }
	
	public int getNumberOfFloors() {
		return number_of_floors;
	}
	
	public List<Integer> getFloorsUP() {
		return floorsUp;
    }
	
	public List<Integer> getFloorsDOWN() {
		return floorsDown;
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
