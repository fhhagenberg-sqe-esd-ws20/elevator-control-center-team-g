package at.fhhagenberg.sqe.view;

import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainView  implements Observer {

	MainViewModel model;
	
	MainView(MainViewModel _model, Stage stage) {
		model = _model;
		buildUI(stage);
		model.addObserver(this);
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
        
        var root = new GridPane();
    	root.setPadding(new Insets(20,20,20,20));
    	root.add(new FloorsView(model.getFloorsModel()), 0, 0);
    	root.add(scrollPane, 1, 0);
    	
    	var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
	}
	
	@Override
    public void update(Observable o, Object arg) {
    	
    }
	
}