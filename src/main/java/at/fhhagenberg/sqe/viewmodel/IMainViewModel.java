package at.fhhagenberg.sqe.viewmodel;

import java.util.ArrayList;

public interface IMainViewModel {
    public void addElevatorModel(ElevatorViewModel em);

    public ArrayList<ElevatorViewModel> getElevatorModels();

    public FloorsViewModel getFloorsModel();

    public void setConnectionState(Boolean s) ;

    public Boolean getConnectionState();
}
