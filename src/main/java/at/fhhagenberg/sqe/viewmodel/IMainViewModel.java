package at.fhhagenberg.sqe.viewmodel;

import java.util.ArrayList;

public interface IMainViewModel {
    public void addElevatorModel(ElevatorViewModel em);

    public ArrayList<ElevatorViewModel> getElevatorModels();

    public IFloorsViewModel getFloorsModel();

    public void setConnectionState(Boolean s) ;

    public Boolean getConnectionState();
    
    public void addLogText(String txt);
}