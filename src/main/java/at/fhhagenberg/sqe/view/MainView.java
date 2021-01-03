package at.fhhagenberg.sqe.view;

import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.helper.ModeState;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView  implements Observer {

	MainViewModel model;
	
	MainView(MainViewModel _model, Stage stage) {
		model = _model;
		buildUI(stage);
	}
	
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
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        var root = new VBox();
    	root.setPadding(new Insets(20,20,20,20));
    	root.getChildren().add(scrollPane);
    	
    	var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	@Override
    public void update(Observable o, Object arg) {
    	
    }
	
}
