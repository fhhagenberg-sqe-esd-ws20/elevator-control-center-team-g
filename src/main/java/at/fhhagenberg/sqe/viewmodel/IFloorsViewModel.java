package at.fhhagenberg.sqe.viewmodel;

import at.fhhagenberg.sqe.view.FloorsView;

import java.util.ArrayList;

public interface IFloorsViewModel {
    public void setNumberOfFloors(int _num);

    public void setFloorsUP(ArrayList<Integer> _vec);

    public void setFloorsDOWN(ArrayList<Integer> _vec);

    public int getNumberOfFloors();

    public ArrayList<Integer> getFloorsUP();

    public ArrayList<Integer> getFloorsDOWN();

    public void updateView();

    void addObserver(FloorsView floorsView);
}
