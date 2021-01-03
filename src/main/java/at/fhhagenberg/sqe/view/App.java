package at.fhhagenberg.sqe.view;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Vector;

import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

    	int floors = 10;
    	
        var model1 = new ElevatorViewModel(1, floors);
        var model2 = new ElevatorViewModel(2, floors);
        var model3 = new ElevatorViewModel(3, floors);
        var model4 = new ElevatorViewModel(4, floors);
        var model5 = new ElevatorViewModel(5, floors);
        var model6 = new ElevatorViewModel(6, floors);
        
        var fModel = new FloorsViewModel(floors);
        
        MainViewModel mainViewModel = new MainViewModel(fModel);
        mainViewModel.addElevatorModel(model1);
        mainViewModel.addElevatorModel(model2);
        mainViewModel.addElevatorModel(model3);
        mainViewModel.addElevatorModel(model4);
        mainViewModel.addElevatorModel(model5);
        mainViewModel.addElevatorModel(model6);
        
        var mainUI = new MainView(mainViewModel, stage);
        
    }

    public static void main(String[] args) {
        launch();
    }

}