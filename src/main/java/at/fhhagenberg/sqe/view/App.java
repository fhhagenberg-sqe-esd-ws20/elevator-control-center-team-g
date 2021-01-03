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

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainViewModel mainViewModel = new MainViewModel();
        var mainUI = new MainView(mainViewModel, stage);  
        var elevator_service = new MockElevator(4, 5, 9, 10);
    	var elevator_controller = new ElevatorController(elevator_service, mainViewModel);
    	
    	elevator_controller.startTimer();
    }

    public static void main(String[] args) {
        launch();
    }

}