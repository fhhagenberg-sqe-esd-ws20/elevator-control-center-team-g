package at.fhhagenberg.sqe.viewmodel;

import java.util.Observable;
import java.util.Vector;

public class FloorsViewModel  extends Observable{

	int number_of_floors = 0;
	
	Vector<Integer> floors_UP = new Vector<Integer>();
	Vector<Integer> floors_DOWN = new Vector<Integer>();
	
	public void setNumberOfFloors(int _num) {
		number_of_floors = _num;
		updateView();
	}
	
	public void setFloorsUP(Vector<Integer> _vec) {
		floors_UP = _vec;
    	updateView();
    }
	
	public void setFloorsDOWN(Vector<Integer> _vec) {
		floors_DOWN = _vec;
    	updateView();
    }
	
	public int getNumberOfFloors() {
		return number_of_floors;
	}
	
	public Vector<Integer> getFloorsUP() {
		return floors_UP;
    }
	
	public Vector<Integer> getFloorsDOWN() {
		return floors_DOWN;
    }
	
	public void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
