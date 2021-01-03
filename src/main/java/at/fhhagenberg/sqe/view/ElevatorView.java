package at.fhhagenberg.sqe.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import at.fhhagenberg.sqe.helper.*;
import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class ElevatorView extends GridPane implements Observer{

	private ElevatorViewModel model;
	
	private String ID_prefix;
	
	private Label lbl_title = new Label("Elevator");
	private Label lbl_direction = new Label("Direction:");
	private Label lbl_speed = new Label("Speed:");
	private Label lbl_floor = new Label("Current Floor:");
	private Label lbl_door = new Label("Door Status:");
	private Label lbl_payload = new Label("Payload:");
	private Label lbl_position = new Label("Position:");
	private Label lbl_mode = new Label("Manual Mode");
	private Button switchbtn_mode = new Button();
    
	GridPane pane = new GridPane();

    ElevatorView(ElevatorViewModel _model) {
    	model = _model;
        ID_prefix = Integer.toString(model.getId());
        model.addObserver(this);
        buildUI();
        model.updateView();
    }
    
    EventHandler<ActionEvent> changeModeHandler = new EventHandler<ActionEvent>() {
		@Override
	    public void handle(ActionEvent event) {
	        model.changeMode();
	        event.consume();
	    }
	};

    private GridPane drawButtons() {
        
        int pos = 0;
        for (int i = model.getNumberOfFloors(); i > 0; i--) {
            var elevatorButton = new Button(String.valueOf(i));
            elevatorButton.setId("#" + ID_prefix + "Button" + Integer.toString(i));
            if (model.getDisabledFloors().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            } else {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            }

            pane.add(elevatorButton, 0, pos++);
        }

        return pane;
    }

    private void buildUI() {
        lbl_title.setId("#" + ID_prefix + "ElevatorHeader");
        lbl_title.setFont(new Font(20));
        lbl_direction.setId("#" + ID_prefix + "Direction");
        lbl_speed.setId("#" + ID_prefix + "SpeedLabel");
        lbl_floor.setId("#" + ID_prefix + "FloorLabel");
        lbl_door.setId("#" + ID_prefix + "DoorLabel");
        lbl_mode.setId("#" + ID_prefix + "ManualLabel");
        lbl_payload.setId("#" + ID_prefix + "PayloadLabel");
        lbl_position.setId("#" + ID_prefix + "PositionLabel");
        
        switchbtn_mode.setText("Change Mode");
        switchbtn_mode.addEventHandler(ActionEvent.ACTION, changeModeHandler);

        var topPanel = new GridPane();
        var midPanel = new GridPane();
        var bottomPanel = new GridPane();
        var panel = new GridPane();

        var gridPane = new GridPane();
        topPanel.add(lbl_title, 0, 0);
        topPanel.add(lbl_speed, 0, 2);
        topPanel.add(lbl_direction, 0, 1);
        midPanel.add(lbl_floor, 0, 0);
        midPanel.add(lbl_door, 0, 1);
        midPanel.add(lbl_position, 0, 2);
        midPanel.add(drawButtons(), 0, 3);

        var payload_grid = new GridPane();
        payload_grid.add(lbl_payload, 0, 0);
        bottomPanel.add(payload_grid, 0, 0);
        
        gridPane.add(switchbtn_mode, 0, 0);
        topPanel.add(gridPane, 1, 3);
        topPanel.add(lbl_mode, 0, 3);

        setHgap(100);
        setVgap(100);

        add(topPanel, 0, 0);
        add(midPanel, 0, 1);
        add(bottomPanel, 0, 2);
        
        setStyle("-fx-background-color: #ffffff; "
        		+ "-fx-border-color:  #545454;\r\n"
        		+ "  -fx-border-width: 1px;\r\n"
        		+ "  -fx-border-style: solid;");
    }
    
    @Override
    public void update(Observable o, Object arg) {
    	lbl_title.setText("Elevator " + Integer.toString(model.getId()));
    	
    	if(model.getDirection() == Direction.up) 
    		lbl_direction.setText("Direction: UP");
    	else
    		lbl_direction.setText("Direction: DOWN");
    	
    	lbl_speed.setText("Speed: " + Integer.toString(model.getSpeed()));
    	
    	lbl_floor.setText("Current Floor: " + Integer.toString(model.getFloor()));
    	
    	if(model.getDoorState() == DoorState.open) 
    		lbl_door.setText("Door Status: " + "OPEN");
    	else
    		lbl_door.setText("Door Status: " + "CLOSED");
    	
    	lbl_payload.setText("Payload: " + Integer.toString(model.getPayload()));
    	
    	if(model.getModeState() == ModeState.automatic) 
    		lbl_mode.setText("Mode: AUTO");
    	else
    		lbl_mode.setText("Mode: MANU");
    	
    	for (int i = model.getNumberOfFloors(); i > 0; i--) {
    		
            //Button elevatorButton = (Button) pane.lookup("#" + ID_prefix + "Button" + Integer.toString(i));
            //elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffa500; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            /*
            if (model.getDisabledFloors().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            } else if(i == model.getFloor()) {
            	elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffa500; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            } else {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            }*/
        }
    	
    	lbl_position.setText("Position: " + model.getPosition());
    }
}
