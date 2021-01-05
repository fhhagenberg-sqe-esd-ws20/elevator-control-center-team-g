package at.fhhagenberg.sqe.view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FloorsView extends GridPane implements Observer {
	
	FloorsViewModel model;
	
	GridPane gridPane = new GridPane();
	
	ArrayList<Rectangle> ups = new ArrayList<Rectangle>();
	ArrayList<Rectangle> downs = new ArrayList<Rectangle>();
	
	FloorsView(FloorsViewModel _model) {
		model = _model;
		buildUI();
		model.addObserver(this);
	}
	
	private void buildUI() {
		int pos = 0;
		for (int i = model.getNumberOfFloors() - 1; i >= 0; i--) {
			HBox left = new HBox();
			Label lbl_FloorNumber = new Label("Floor " + i);

			Rectangle rectUP = new Rectangle(80, 25);
			rectUP.setId("UP"+ Integer.toString(i));
			rectUP.setFill(Color.ORANGE);
			Text textUP = new Text ("UP");
			textUP.setFill(Color.WHITE);
			StackPane stackUP = new StackPane();
			stackUP.getChildren().addAll(rectUP, textUP);
			
			Rectangle rectDOWN = new Rectangle(80, 25);
			rectDOWN.setId("DOWN"+ Integer.toString(i));
			rectDOWN.setFill(Color.ORANGE);
			Text textDOWN = new Text ("DOWN");
			textDOWN.setFill(Color.WHITE);
			StackPane stackDOWN = new StackPane();
			stackDOWN.getChildren().addAll(rectDOWN, textDOWN);
			
			VBox right = new VBox();
			right.getChildren().add(stackUP);
			right.getChildren().add(stackDOWN);

			ups.add(rectUP);
			downs.add(rectDOWN);
			
			left.getChildren().add(lbl_FloorNumber);
			
			gridPane.add(left, 0, pos);
			gridPane.add(right, 1, pos++);
		}
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(gridPane);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		var root = new VBox();
    	root.getChildren().add(scrollPane);
    	
    	add(root, 0, 0);
    	
    	Collections.reverse(ups);
    	Collections.reverse(downs);
	}
	
	@Override
    public void update(Observable o, Object arg) {
		for (int i = model.getNumberOfFloors() - 1; i >= 0; i--) {
			
			Rectangle rectUP = ups.get(i);
			Rectangle rectDOWN = downs.get(i);
			
		    if (!model.getFloorsUP().contains(i)) {
		    	rectUP.setFill(Color.ORANGE);
		    } else {
		    	rectUP.setFill(Color.DARKORANGE);
		    }
		    
		    if (!model.getFloorsDOWN().contains(i)) {
		    	rectDOWN.setFill(Color.ORANGE);
		    } else {
		    	rectDOWN.setFill(Color.DARKORANGE);
		    }
		}
	}
}
