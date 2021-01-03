package at.fhhagenberg.sqe.model;

import java.util.Vector;

import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.helper.ModeState;
import javafx.scene.control.Label;

public class ElevatorModel {

	int id;
	float speed = 0;
    ModeState modeState;
    int floor = 0;
    DoorState doorState;
    Direction direction;

    Vector<Integer> disabled_floors;
    int number_of_floors;

    int payload = 0;
}
