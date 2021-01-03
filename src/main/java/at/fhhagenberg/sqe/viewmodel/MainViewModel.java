package at.fhhagenberg.sqe.viewmodel;

import java.util.ArrayList;
import java.util.Observable;

public class MainViewModel extends Observable {

	ArrayList<ElevatorViewModel> elevators = new ArrayList<ElevatorViewModel>();
	FloorsViewModel floorsViewModel;
	
	public MainViewModel(FloorsViewModel _floorsViewModel) {
		floorsViewModel = _floorsViewModel;
	}
	
	public void addElevatorModel(ElevatorViewModel em) {
		elevators.add(em);
	}
	
	public ArrayList<ElevatorViewModel> getElevatorModels() {
		return elevators;
	}
	
	public FloorsViewModel getFloorsModel() {
		return floorsViewModel;
	}
	
	private void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
