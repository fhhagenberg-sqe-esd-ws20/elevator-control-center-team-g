package at.fhhagenberg.sqe.viewmodel;

import java.util.ArrayList;
import java.util.Observable;

public class MainViewModel extends Observable {

	ArrayList<ElevatorViewModel> elevators = new ArrayList<ElevatorViewModel>();
	
	public void addElevatorModel(ElevatorViewModel em) {
		elevators.add(em);
	}
	
	public ArrayList<ElevatorViewModel> getElevatorModels() {
		return elevators;
	}
	
	private void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
