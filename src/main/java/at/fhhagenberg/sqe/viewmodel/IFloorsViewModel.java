package at.fhhagenberg.sqe.viewmodel;

import at.fhhagenberg.sqe.view.FloorsView;

import java.util.ArrayList;
import java.util.List;

public interface IFloorsViewModel {
    public void setNumberOfFloors(int num);

    public void setFloorsUP(List<Integer> vec);

    public void setFloorsDOWN(List<Integer> vec);

    public int getNumberOfFloors();

    public List<Integer> getFloorsUP();

    public List<Integer> getFloorsDOWN();

    public void updateView();

    void addObserver(FloorsView floorsView);
}
