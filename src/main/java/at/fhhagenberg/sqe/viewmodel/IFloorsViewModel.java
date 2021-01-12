package at.fhhagenberg.sqe.viewmodel;

import at.fhhagenberg.sqe.view.FloorsView;

import java.util.Observable;
import java.util.Vector;

public interface IFloorsViewModel {
    public void setNumberOfFloors(int _num);

    public void setFloorsUP(Vector<Integer> _vec);

    public void setFloorsDOWN(Vector<Integer> _vec);

    public int getNumberOfFloors();

    public Vector<Integer> getFloorsUP();

    public Vector<Integer> getFloorsDOWN();

    public void updateView();

    void addObserver(FloorsView floorsView);
}
