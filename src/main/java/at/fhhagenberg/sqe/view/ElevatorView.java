package at.fhhagenberg.sqe.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import at.fhhagenberg.sqe.helper.*;
import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;

import java.util.Observable;
import java.util.Observer;

public class ElevatorView extends GridPane implements Observer{

	private ElevatorViewModel mModel;	
	private String idPrefix;	
	private Label lblTitle = new Label("Elevator");
	private Label lblDirection = new Label("Direction:");
	private Label lblSpeed = new Label("Speed:");
	private Label lblFloor = new Label("Current Floor:");
	private Label lblDoor = new Label("Door Status:");
	private Label lblPayload = new Label("Payload:");
	private Label lblPosition = new Label("Position:");
	private Label lblMode = new Label("Manual Mode");
	private Button switchbtnMode = new Button();
	private final String strButton = "Button";
	private int setTarget = -1;
    
	GridPane pane = new GridPane();

    ElevatorView(ElevatorViewModel model) {
    	mModel = model;
        idPrefix = Integer.toString(mModel.getId());
        mModel.addObserver(this);
        buildUI();
    }
    
    EventHandler<ActionEvent> changeModeHandler = new EventHandler<ActionEvent>() {
		@Override
	    public void handle(ActionEvent event) {
	        mModel.changeMode();
	        event.consume();
	    }
	};

    private GridPane drawButtons() {
        int pos = 0;
        for (int i = mModel.getNumberOfFloors() -1; i >= 0; i--) {
            var elevatorButton = new Button(String.valueOf(i));
            elevatorButton.setId("#" + idPrefix + strButton + Integer.toString(i));
            if (mModel.getDisabledFloors().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            } else {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            }
            final int index = i;
            elevatorButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            	@Override
        	    public void handle(ActionEvent event) {
        	        mModel.clickedFloor(index);
        	        Button elevatorButton = (Button) pane.lookup("##" + idPrefix + strButton + Integer.toString(index));
        	        setTarget = index;
        	        event.consume();
        	    }
            });

            pane.add(elevatorButton, 0, pos++);
        }

        return pane;
    }

    private void buildUI() {
        lblTitle.setId("#" + idPrefix + "ElevatorHeader");
        lblTitle.setFont(new Font(20));
        lblDirection.setId("#" + idPrefix + "DirectionLabel");
        lblSpeed.setId("#" + idPrefix + "SpeedLabel");
        lblFloor.setId("#" + idPrefix + "FloorLabel");
        lblDoor.setId("#" + idPrefix + "DoorLabel");
        lblMode.setId("#" + idPrefix + "ManualLabel");
        lblPayload.setId("#" + idPrefix + "PayloadLabel");
        lblPosition.setId("#" + idPrefix + "PositionLabel");
        
        switchbtnMode.setId("#" + idPrefix + "ChangeButton");
        switchbtnMode.setText("Change Mode");
        switchbtnMode.addEventHandler(ActionEvent.ACTION, changeModeHandler);

        var topPanel = new GridPane();
        var midPanel = new GridPane();
        var bottomPanel = new GridPane();
        var panel = new GridPane();

        var gridPane = new GridPane();
        topPanel.add(lblTitle, 0, 0);
        topPanel.add(lblSpeed, 0, 2);
        topPanel.add(lblDirection, 0, 1);
        midPanel.add(lblFloor, 0, 0);
        midPanel.add(lblDoor, 0, 1);
        midPanel.add(lblPosition, 0, 2);
        midPanel.add(drawButtons(), 0, 3);

        var payload_grid = new GridPane();
        payload_grid.add(lblPayload, 0, 0);
        bottomPanel.add(payload_grid, 0, 0);
        
        gridPane.add(switchbtnMode, 0, 0);
        topPanel.add(gridPane, 1, 3);
        topPanel.add(lblMode, 0, 3);

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
    	lblTitle.setText("Elevator " + Integer.toString(mModel.getId()));
    	
    	if(mModel.getDirection() == Direction.UP) 
    		lblDirection.setText("Direction: UP");
    	else if(mModel.getDirection() == Direction.DOWN)
    		lblDirection.setText("Direction: DOWN");
    	else
    		lblDirection.setText("Direction: UNCOMMITED");
    	
    	lblSpeed.setText("Speed: " + Integer.toString(mModel.getSpeed()));
    	
    	lblFloor.setText("Current Floor: " + Integer.toString(mModel.getFloor()));
    	
    	if(mModel.getDoorState() == DoorState.OPEN) 
    		lblDoor.setText("Door Status: " + "OPEN");
    	else
    		lblDoor.setText("Door Status: " + "CLOSED");
    	
    	lblPayload.setText("Payload: " + Integer.toString(mModel.getPayload()) + " kg");
    	
    	if(mModel.getModeState() == ModeState.AUTOMATIC) 
    		lblMode.setText("Mode: AUTO");
    	else
    		lblMode.setText("Mode: MANU");
    	
    	for (int i = mModel.getNumberOfFloors() -1; i >= 0; i--) {
    		
            Button elevatorButton = (Button) pane.lookup("##" + idPrefix + strButton + Integer.toString(i));
            
            if (mModel.getDisabledFloors().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else if(i == mModel.getFloor()) {
            	elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffa500; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else if(i == setTarget) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #8F91EF; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else if (mModel.getPressedButtons().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            }
        }
    	
    	if(setTarget == mModel.getFloor())
    		setTarget = -1;
    	
    	lblPosition.setText("Position: " + mModel.getPosition() + " feet");
    }
}
