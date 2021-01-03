package at.fhhagenberg.sqe.viewmodel;

import java.util.Observable;

public class FloorsViewModel  extends Observable{

	int number_of_floors = 5;
	
	public FloorsViewModel(int _number_of_floors) {
		number_of_floors = _number_of_floors;
    }
	
	public int getNumberOfFloors() {
		return number_of_floors;
	}
}
