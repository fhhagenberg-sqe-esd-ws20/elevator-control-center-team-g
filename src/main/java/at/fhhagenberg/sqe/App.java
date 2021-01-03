package at.fhhagenberg.sqe;

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

/**
 * JavaFX App
 */
public class App extends Application {

    final String ID_prefix = "App";

    @Override
    public void start(Stage stage) {
        var disabled = new Vector<Integer>();
        disabled.add(2);
        var allPanel = new GridPane();
        var scene = new Scene(allPanel);

        var ui = new ElevatorUI("B", disabled, 1);
        ui.setOnMouseClicked( e -> clicked());
        allPanel.add(ui, 0,0);

        stage.setScene(scene);
        stage.setTitle("Java");
        stage.show();
    }

    private void clicked(){

    }

    public static void main(String[] args) {
        launch();
    }

}