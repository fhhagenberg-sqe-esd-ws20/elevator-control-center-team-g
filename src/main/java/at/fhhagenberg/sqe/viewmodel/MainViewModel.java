package at.fhhagenberg.sqe.viewmodel;

import java.util.ArrayList;
import java.util.Observable;

import at.fhhagenberg.sqe.controller.IElevatorController;

public class MainViewModel extends Observable {

	Boolean connection_state = false;
	
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
	
	public void setConnectionState(Boolean s) {
		connection_state = s;
		updateView();
	}
	
	public Boolean getConnectionState() {
		return connection_state;
	}
	
	private void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
