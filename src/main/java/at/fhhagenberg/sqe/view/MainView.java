package at.fhhagenberg.sqe.view;

import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView  implements Observer {

	MainViewModel model;
	
	Rectangle statusRect;
	Label statusText;
	
	private Stage stage;
	
	private Button connectbtn = new Button();
	private TextArea log = new TextArea();
	
	private int elevatorsbefore = 0;
	
	public MainView(MainViewModel _model, Stage stage) {
		model = _model;
		this.stage = stage;
		buildUI(stage);
		model.addObserver(this);
		elevatorsbefore = model.getElevatorModels().size();
	}
	
	EventHandler<ActionEvent> connectHandler = new EventHandler<ActionEvent>() {
		@Override
	    public void handle(ActionEvent event) {
	        model.connectToRMI();
	        event.consume();
	    }
	};
	
	private void buildUI(Stage stage) {
		GridPane gridPaneMain = new GridPane();
		
		int i = 0;
		for(var elem : model.getElevatorModels()) {
			gridPaneMain.add(new ElevatorView(elem), i++, 0);
		}
       
		gridPaneMain.setPadding(new Insets(20,20,20,20));
		gridPaneMain.setHgap(10);
		gridPaneMain.setVgap(10);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(gridPaneMain);
		
		statusRect = new Rectangle(80, 25);
		statusText = new Label ("");
		statusText.setTextFill(Color.WHITE);
		statusText.setId("statusText");
		StackPane stackP = new StackPane();
		stackP.getChildren().addAll(statusRect, statusText);
		
		if(model.getConnectionState()) 
    		statusRect.setFill(Color.GREEN);
    	else {
    		statusRect.setFill(Color.RED);
    		statusText.setText("ERROR");
    	}
		
		connectbtn.addEventHandler(ActionEvent.ACTION, connectHandler);
        connectbtn.setText("CONNECT");
		
		var vb = new VBox();
		vb.setSpacing(5);
		vb.getChildren().add(connectbtn);
		vb.getChildren().add(new FloorsView(model.getFloorsModel()));
		
        var root = new GridPane();
    	root.setPadding(new Insets(20,20,20,20));
    	root.add(vb, 0, 0);
    	root.add(scrollPane, 1, 0);
    	root.setMaxHeight(600);
    	root.setMinSize(1000, 600);
    	
    	log.setEditable(false);
    	root.add(log, 1, 1);
    	
    	root.add(stackP, 0, 1);
    	
    	var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	@Override
    public void update(Observable o, Object arg) {
      
		if(elevatorsbefore != model.getElevatorModels().size()) {
			buildUI(stage);
			elevatorsbefore = model.getElevatorModels().size();
		}
		
    	if(model.getConnectionState()) {
    		statusRect.setFill(Color.GREEN);
    		statusText.setText("");
    	}
    	else {
    		statusRect.setFill(Color.RED);
    		statusText.setText("ERROR");
    	}
    	log.setText(model.getLogText());
    }
}
