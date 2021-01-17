package at.fhhagenberg.sqe.viewmodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import at.fhhagenberg.sqe.controller.IElevatorController;

public class MainViewModel extends Observable implements IMainViewModel {

	Boolean mConnectionState = false;
	
	ArrayList<ElevatorViewModel> mElevators = new ArrayList<>();
	IFloorsViewModel mFloorsViewModel;
	
	IElevatorController mEc;
	
	String logTxt = "";
	
	public MainViewModel(FloorsViewModel floorsViewModel) {
		mFloorsViewModel = floorsViewModel;
	}
	
	public void setController(IElevatorController ec) {
		mEc = ec;
	}
	
	public void addElevatorModel(ElevatorViewModel em) {
		mElevators.add(em);
		updateView();
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
	
	public void addLogText(String txt) {
		logTxt = (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + " : " + txt) + "\n" + logTxt;
		updateView();
	}
	
	public String getLogText() {
		return logTxt;
	}
	
	public Boolean getConnectionState() {
		return mConnectionState;
	}
	
	public void connectToRMI() {
		mEc.doConnect();
	}
	
	private void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
