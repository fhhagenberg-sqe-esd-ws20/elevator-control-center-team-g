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
import at.fhhagenberg.sqe.viewmodel.MainViewModel;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        var model1 = new ElevatorViewModel(1);
        var model2 = new ElevatorViewModel(2);
        var model3 = new ElevatorViewModel(3);
        var model4 = new ElevatorViewModel(4);
        var model5 = new ElevatorViewModel(5);
        var model6 = new ElevatorViewModel(6);
        
        MainViewModel mainViewModel = new MainViewModel();
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