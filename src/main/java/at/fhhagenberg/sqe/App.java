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

        var switchButton = new SwitchButton();
        var gridPane = new GridPane();

        var header = new Label("Elevator\t"+ID_prefix);

        var c_floor_label = new Label("Current Floor:\t");
        var c_door_label = new Label("Door Status:\t" + Door.closed);
        var c_speed = new Label("Speed:\t"+  String.valueOf(0));
        var c_manual = new Label("Manual Mode\t");

        header.setId("#"+ID_prefix+"ElevatorHeader");
        header.setFont(new Font(100));

        c_floor_label.setId("#"+ID_prefix+"FloorLabel");
        c_door_label.setId("#"+ID_prefix+"DoorLabel");
        c_speed.setId("#"+ID_prefix+"SpeedLabel");
        c_manual.setId("#"+ID_prefix+"ManualLabel");

        var topPanel = new GridPane();
        var midPanel = new GridPane();
        var bottomPanel = new GridPane();
        var allPanel = new GridPane();

        topPanel.add(header,0,1);
        topPanel.add(c_speed, 0, 2);
        midPanel.add(c_floor_label, 0, 0);
        midPanel.add(c_door_label, 0, 1);

        midPanel.add(drawButtons(5, disabled),0,2);

        var scene = new Scene(allPanel);
        {
            var payload = new GridPane();
            var c_payload_label = new Label("Passengers:\t" + String.valueOf(0));
            payload.add(c_payload_label, 0,0);
            bottomPanel.add(payload, 0,0);
        }

        gridPane.add(switchButton,0,0);
        topPanel.add(gridPane, 1,3);
        topPanel.add(c_manual, 0,3);

        allPanel.setHgap(100);
        allPanel.setVgap(100);

        allPanel.add(topPanel, 0, 0);
        allPanel.add(midPanel, 0, 1);
        allPanel.add(bottomPanel, 0,2);

        allPanel.setStyle("-fx-background-color: #fbfbfb");

        stage.setScene(scene);
        stage.setTitle("Java");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}