package at.fhhagenberg.sqe.viewmodel;

import at.fhhagenberg.sqe.view.FloorsView;

import java.util.ArrayList;
import java.util.Observable;

public class FloorsViewModel  extends Observable implements IFloorsViewModel{

	int number_of_floors = 0;
	
	ArrayList<Integer> floors_UP = new ArrayList<Integer>();
	ArrayList<Integer> floors_DOWN = new ArrayList<Integer>();
	
	public void setNumberOfFloors(int _num) {
		number_of_floors = _num;
		updateView();
	}
	
	public void setFloorsUP(ArrayList<Integer> _vec) {
		floors_UP = _vec;
    	updateView();
    }
	
	public void setFloorsDOWN(ArrayList<Integer> _vec) {
		floors_DOWN = _vec;
    	updateView();
    }
	
	public int getNumberOfFloors() {
		return number_of_floors;
	}
	
	public ArrayList<Integer> getFloorsUP() {
		return floors_UP;
    }
	
	public ArrayList<Integer> getFloorsDOWN() {
		return floors_DOWN;
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
