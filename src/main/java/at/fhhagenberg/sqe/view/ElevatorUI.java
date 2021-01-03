package at.fhhagenberg.sqe.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import at.fhhagenberg.sqe.helper.*;

import java.util.Vector;

public class ElevatorUI extends GridPane {

	//---------------------------------------------------------
	String ID_prefix;
	
	float speed = 0;
	
    private Label c_manual = new Label("Manual Mode\t");
    private boolean c_manual_is_manual = false;
    
    int floor = 0;
    
    DoorState state = DoorState.open;
    
    private Label direction = new Label("unknown");

    Vector<Integer> disabled;
    int number_of_floors;

    int payload = 0;
    //---------------------------------------------------------
    

    public void setSpeed(float _speed) {
        speed = _speed;
    }
    public void setFloor(int _floor) {
        floor = _floor;
    }
    public void setPayload(int _pax) {
        payload = _pax;
    }
    public void setDoorState(DoorState _state){
        state = _state;
    }
    public void setDirection(Direction _dir) {
    	if(_dir == Direction.up)
    		direction.setText("UP");
    	else
    		direction.setText("DOWN");
    }

    /*
    public float getSpeed() {
        return speed;
    }
    public int getFloor() {
        return floor;
    }
    public int getPayload() {
        return payload;
    }
    public DoorState getState() {
        return state;
    }
    */

    ElevatorUI(String id, Vector<Integer> disabled_stages, int buttons_size) {
        ID_prefix = id;
        disabled = disabled_stages;
        number_of_floors = buttons_size;

        buildUI();
    }


    private GridPane drawButtons(int count, Vector<Integer> disabled) {
        var pane = new GridPane();
        int pos = 0;
        for (int i = count; i > 0; i--) {
            var elevatorButton = new Button(String.valueOf(i));
            elevatorButton.setId("#" + ID_prefix + "Button" + String.valueOf(i));
            if (disabled.contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            } else {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            }

            pane.add(elevatorButton, 0, pos++);
        }

        return pane;
    }

    private void onToggleSwitch(){
        if(c_manual_is_manual) {
            c_manual.setText("Manual Mode");
            c_manual_is_manual = false;
        }else{
            c_manual.setText("Automatic Mode");
            c_manual_is_manual = true;
        }
    }

    private void buildUI() {
        var disabled = new Vector<Integer>();
        disabled.add(2);

        var switchButton = new SwitchButton();
        var gridPane = new GridPane();

        var header = new Label("Elevator " + ID_prefix);

        var c_floor_label = new Label("Current Floor:\t" + String.valueOf(floor));
        var c_door_label = new Label("Door Status:\t" + state);
        var c_speed = new Label("Speed:\t" + String.valueOf(speed));

        header.setId("#" + ID_prefix + "ElevatorHeader");
        header.setFont(new Font(20));

        c_floor_label.setId("#" + ID_prefix + "FloorLabel");
        c_door_label.setId("#" + ID_prefix + "DoorLabel");
        c_speed.setId("#" + ID_prefix + "SpeedLabel");
        c_manual.setId("#" + ID_prefix + "ManualLabel");

        var topPanel = new GridPane();
        var midPanel = new GridPane();
        var bottomPanel = new GridPane();
        var panel = new GridPane();

        topPanel.add(header, 0, 1);
        topPanel.add(c_speed, 0, 2);
        topPanel.add(direction, 0, 0);
        midPanel.add(c_floor_label, 0, 0);
        midPanel.add(c_door_label, 0, 1);

        midPanel.add(drawButtons(5, disabled), 0, 2);

        {
            var payload_grid = new GridPane();
            var c_payload_label = new Label("Payload:\t" + String.valueOf(payload));
            payload_grid.add(c_payload_label, 0, 0);
            bottomPanel.add(payload_grid, 0, 0);
        }

        gridPane.add(switchButton, 0, 0);
        topPanel.add(gridPane, 1, 3);
        topPanel.add(c_manual, 0, 3);

        panel.setHgap(100);
        panel.setVgap(100);

        panel.add(topPanel, 0, 0);
        panel.add(midPanel, 0, 1);
        panel.add(bottomPanel, 0, 2);

        panel.setStyle("-fx-background-color: #ffffff");
        add(panel, 0, 0);

        switchButton.setOnMouseClicked(e -> onToggleSwitch());
    }


}
