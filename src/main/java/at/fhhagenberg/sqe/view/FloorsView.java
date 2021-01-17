package at.fhhagenberg.sqe.view;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.viewmodel.IFloorsViewModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

public class FloorsView extends GridPane implements Observer {
	
	IFloorsViewModel mModel;	
	GridPane mGridPane = new GridPane();
	ArrayList<Rectangle> mUps = new ArrayList<>();
	ArrayList<Rectangle> mDowns = new ArrayList<>();	
	int mFloorsBefore = 0;
	
	FloorsView(IFloorsViewModel model) {
		mModel = model;
		buildUI();
		mModel.addObserver(this);
		mFloorsBefore = model.getNumberOfFloors();
	}
	
	private void buildUI() {
		int pos = 0;

		mGridPane = new GridPane();
		for (int i = mModel.getNumberOfFloors() - 1; i >= 0; i--) {
			HBox left = new HBox();
			Label lblFloorNumber = new Label("Floor " + i);

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

			mUps.add(rectUP);
			mDowns.add(rectDOWN);
			
			left.getChildren().add(lblFloorNumber);
			
			mGridPane.add(left, 0, pos);
			mGridPane.add(right, 1, pos++);
		}
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(mGridPane);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		var root = new VBox();
    	root.getChildren().add(scrollPane);
    	
    	add(root, 0, 0);
    	
    	Collections.reverse(mUps);
    	Collections.reverse(mDowns);
	}
	
	@Override
    public void update(Observable o, Object arg) {
		
		if(mFloorsBefore != mModel.getNumberOfFloors()) {
			buildUI();
			mFloorsBefore = mModel.getNumberOfFloors();
		}
      
		for (int i = mModel.getNumberOfFloors() - 1; i >= 0; i--) {
			
			Rectangle rectUP = mUps.get(i);
			Rectangle rectDOWN = mDowns.get(i);
			
		    if (!mModel.getFloorsUP().contains(i)) {
		    	rectUP.setFill(Color.ORANGE);
		    } else {
		    	rectUP.setFill(Color.DARKORANGE);
		    }
		    
		    if (!mModel.getFloorsDOWN().contains(i)) {
		    	rectDOWN.setFill(Color.ORANGE);
		    } else {
		    	rectDOWN.setFill(Color.DARKORANGE);
		    }
		}
	}
}
