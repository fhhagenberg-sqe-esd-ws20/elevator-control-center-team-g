package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Vector;

/**
 * JavaFX App
 */
public class App extends Application {

    final String ID_prefix = "App";

    public enum Door {
        open,
        closed
    }

    public GridPane drawButtons(int count, Vector<Integer> disabled){
        var pane = new GridPane();
        int pos = 0;
        for(int i = count; i > 0; i--){
            var elevatorButton = new Button(String.valueOf(i));
            elevatorButton.setId("#"+ID_prefix+"Button"+String.valueOf(i));
            if(disabled.contains(i)) {
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #726f68; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            }else{
                elevatorButton.setStyle("-fx-border-width: 0; -fx-background-color: #ffc72b; -fx-stroke-width: 1; -fx-pref-width: 200; -fx-background-radius: 0");
            }

            pane.add(elevatorButton, 0, pos++);
        }

        return pane;
    }

    @Override
    public void start(Stage stage) {
        var disabled = new Vector<Integer>();
        disabled.add(2);
        var allPanel = new GridPane();
        var scene = new Scene(allPanel);

        var ui = new ElevatorUI("B", disabled, 1);
        allPanel.add(ui, 0,0);

        stage.setScene(scene);
        stage.setTitle("Java");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}