package at.fhhagenberg.sqe.viewmodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import at.fhhagenberg.sqe.controller.IElevatorController;

public class MainViewModel extends Observable implements IMainViewModel {

	Boolean connection_state = false;
	
	ArrayList<ElevatorViewModel> elevators = new ArrayList<ElevatorViewModel>();
	IFloorsViewModel floorsViewModel;
	
	IElevatorController ec;
	
	String log_txt = "";
	
	public MainViewModel(FloorsViewModel _floorsViewModel) {
		floorsViewModel = _floorsViewModel;
	}
	
	public void setController(IElevatorController _ec) {
		ec = _ec;
	}
	
	public void addElevatorModel(ElevatorViewModel em) {
		elevators.add(em);
		updateView();
	}
	
	public ArrayList<ElevatorViewModel> getElevatorModels() {
		return elevators;
	}
	
	public IFloorsViewModel getFloorsModel() {
		return floorsViewModel;
	}
	
	public void setConnectionState(Boolean s) {
		connection_state = s;
		updateView();
	}
	
	public void addLogText(String txt) {
		log_txt = (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + " : " + txt) + "\n" + log_txt;
		updateView();
	}
	
	public String getLogText() {
		return log_txt;
	}
	
	public Boolean getConnectionState() {
		return connection_state;
	}
	
	public void connectToRMI() {
		ec.doConnect();
	}
	
	private void updateView() {
    	setChanged();
    	notifyObservers();
    }
}
