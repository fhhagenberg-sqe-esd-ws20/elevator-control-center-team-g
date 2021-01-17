package at.fhhagenberg.sqe.viewmodel;

import java.util.ArrayList;
import java.util.Observable;

public class MainViewModel extends Observable implements IMainViewModel {

	Boolean mConnectionState = false;
	
	ArrayList<ElevatorViewModel> mElevators = new ArrayList<>();
	IFloorsViewModel mFloorsViewModel;
	
	public MainViewModel(FloorsViewModel floorsViewModel) {
		mFloorsViewModel = floorsViewModel;
	}
	
	public void addElevatorModel(ElevatorViewModel em) {
		mElevators.add(em);
	}
	
	public ArrayList<ElevatorViewModel> getElevatorModels() {
		return mElevators;
	}
	
	public IFloorsViewModel getFloorsModel() {
		return mFloorsViewModel;
	}
	
	public void setConnectionState(Boolean s) {
		mConnectionState = s;
		updateView();
	}
	
	public Boolean getConnectionState() {
		return mConnectionState;
	}
	
	private void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
