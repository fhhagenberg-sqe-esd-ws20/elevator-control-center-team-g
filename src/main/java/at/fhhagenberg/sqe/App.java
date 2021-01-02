package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.Vector;

/**
 * JavaFX App
 */
public class App extends Application {

    public enum Door {
        Open,
        Closed
    }

    public GridPane drawButtons(int count, Vector<Integer> disabled){
        var pane = new GridPane();
        int pos = 0;
        for(int i = count; i > 0; i--){
            var elevatorButton = new Button(String.valueOf(i));
            elevatorButton.setId("#Button"+String.valueOf(i));
            if(disabled.contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200;");
            }else{
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200");
            }

            pane.add(elevatorButton, 0, pos++);
        }

        return pane;
    }

    @Override
    public void start(Stage stage) {
        var c_floor_label = new Label("Current Floor:\t");
        var c_door_label = new Label("Door Status:\t" + Door.Closed);

        var topPanel = new GridPane();
        var bottomPanel = new GridPane();
        var allPanel = new GridPane();

        var scene = new Scene(allPanel);


        ColumnConstraints cc = new ColumnConstraints(
                90,
                90,
                90,
                Priority.ALWAYS,
                HPos.LEFT,
                true);

        ColumnConstraints spacer = new ColumnConstraints(
                10,
                10,
                10,
                Priority.ALWAYS,
                HPos.LEFT,
                true);


        topPanel.add(c_floor_label, 0, 0);
        topPanel.add(c_door_label, 0, 1);

        int count = 5;
        int pos = 2;
        for(int i = count; i > 0; i--){
            var elevatorButton = new Button(String.valueOf(i));
            elevatorButton.setId("#Button"+String.valueOf(i));
            elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200");
            topPanel.add(elevatorButton, 0, pos++);
        }



        bottomPanel.getColumnConstraints().add(spacer);
        bottomPanel.getColumnConstraints().add(cc);
        bottomPanel.getColumnConstraints().add(cc);


        allPanel.add(topPanel, 0, 0);
        allPanel.add(bottomPanel, 0, 1);

        allPanel.setStyle("-fx-background-color: #ffffff");

        stage.setScene(scene);
        stage.setTitle("Java scheiße");
        stage.show();
    }

    private Object getResolveDoor(Door door_status) {
        return door_status == Door.Open;
    }

    public static void main(String[] args) {
        launch();
    }

}