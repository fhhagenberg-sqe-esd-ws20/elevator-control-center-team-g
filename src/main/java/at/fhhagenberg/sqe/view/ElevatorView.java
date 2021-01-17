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

	private ElevatorViewModel model;
	
	private String IDPREFIX;
	
	private Label lblTitle = new Label("Elevator");
	private Label lblDirection = new Label("Direction:");
	private Label lblSpeed = new Label("Speed:");
	private Label lblFloor = new Label("Current Floor:");
	private Label lblDoor = new Label("Door Status:");
	private Label lblPayload = new Label("Payload:");
	private Label lblPosition = new Label("Position:");
	private Label lblMode = new Label("Manual Mode");
	private Button switchbtn_mode = new Button();
	private final String strButton = "Button";
	private int setTarget = -1;
    
	GridPane pane = new GridPane();

    ElevatorView(ElevatorViewModel _model) {
    	model = _model;
        IDPREFIX = Integer.toString(model.getId());
        model.addObserver(this);
        buildUI();
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
        for (int i = model.getNumberOfFloors() -1; i >= 0; i--) {
            var elevatorButton = new Button(String.valueOf(i));
            elevatorButton.setId("#" + IDPREFIX + strButton + Integer.toString(i));
            if (model.getDisabledFloors().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            } else {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            }
            final int index = i;
            elevatorButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            	@Override
        	    public void handle(ActionEvent event) {
        	        model.clickedFloor(index);
        	        Button elevatorButton = (Button) pane.lookup("##" + IDPREFIX + strButton + Integer.toString(index));
        	        setTarget = index;
        	        event.consume();
        	    }
            });

            pane.add(elevatorButton, 0, pos++);
        }

        return pane;
    }

    private void buildUI() {
        lblTitle.setId("#" + IDPREFIX + "ElevatorHeader");
        lblTitle.setFont(new Font(20));
        lblDirection.setId("#" + IDPREFIX + "DirectionLabel");
        lblSpeed.setId("#" + IDPREFIX + "SpeedLabel");
        lblFloor.setId("#" + IDPREFIX + "FloorLabel");
        lblDoor.setId("#" + IDPREFIX + "DoorLabel");
        lblMode.setId("#" + IDPREFIX + "ManualLabel");
        lblPayload.setId("#" + IDPREFIX + "PayloadLabel");
        lblPosition.setId("#" + IDPREFIX + "PositionLabel");
        
        switchbtn_mode.setId("#" + IDPREFIX + "ChangeButton");
        switchbtn_mode.setText("Change Mode");
        switchbtn_mode.addEventHandler(ActionEvent.ACTION, changeModeHandler);

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
        
        gridPane.add(switchbtn_mode, 0, 0);
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
    	lblTitle.setText("Elevator " + Integer.toString(model.getId()));
    	
    	if(model.getDirection() == Direction.UP) 
    		lblDirection.setText("Direction: UP");
    	else if(model.getDirection() == Direction.DOWN)
    		lblDirection.setText("Direction: DOWN");
    	else
    		lblDirection.setText("Direction: UNCOMMITED");
    	
    	lblSpeed.setText("Speed: " + Integer.toString(model.getSpeed()));
    	
    	lblFloor.setText("Current Floor: " + Integer.toString(model.getFloor()));
    	
    	if(model.getDoorState() == DoorState.OPEN) 
    		lblDoor.setText("Door Status: " + "OPEN");
    	else
    		lblDoor.setText("Door Status: " + "CLOSED");
    	
    	lblPayload.setText("Payload: " + Integer.toString(model.getPayload()) + " kg");
    	
    	if(model.getModeState() == ModeState.AUTOMATIC) 
    		lblMode.setText("Mode: AUTO");
    	else
    		lblMode.setText("Mode: MANU");
    	
    	for (int i = model.getNumberOfFloors() -1; i >= 0; i--) {
    		
            Button elevatorButton = (Button) pane.lookup("##" + IDPREFIX + strButton + Integer.toString(i));
            
            if (model.getDisabledFloors().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else if(i == model.getFloor()) {
            	elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffa500; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else if(i == setTarget) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #8F91EF; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else if (model.getPressedButtons().contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            } else {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0;");
            }
        }
    	
    	if(setTarget == model.getFloor())
    		setTarget = -1;
    	
    	lblPosition.setText("Position: " + model.getPosition() + " feet");
    }
}
