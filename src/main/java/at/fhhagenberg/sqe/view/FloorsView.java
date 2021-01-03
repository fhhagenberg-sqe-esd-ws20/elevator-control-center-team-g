package at.fhhagenberg.sqe.view;


import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class FloorsView extends GridPane implements Observer {
	FloorsViewModel model;
	
	FloorsView(FloorsViewModel _model) {
		model = _model;
		buildUI();
		model.addObserver(this);
	}
	
	private void buildUI() {
		
		var gridPane = new GridPane();
		
		int pos = 0;
		for (int i = model.getNumberOfFloors(); i > 0; i--) {
			VBox left = new VBox();
			Label lbl_FloorNumber = new Label("Floor " + i);
			left.getChildren().add(lbl_FloorNumber);
			gridPane.add(left, 0, pos++);
		}
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(gridPane);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		var root = new VBox();
    	root.setPadding(new Insets(20,20,20,20));
    	root.getChildren().add(scrollPane);
    	
    	add(root, 0, 0);
    	
	}
	
	@Override
    public void update(Observable o, Object arg) {
	}
}
