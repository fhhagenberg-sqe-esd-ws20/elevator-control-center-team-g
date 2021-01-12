package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.stage.Stage;
import sqelevator.IElevatorWrapper;

import java.rmi.Naming;
import java.rmi.RemoteException;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.MainView;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import sqelevator.ElevatorWrapper;
import sqelevator.IElevator;

/**
 * JavaFX App
 */
public class ElevatorControlCenter extends Application {

    @Override
    public void start(Stage stage) {       
    	try {
			var floors_view_model = new FloorsViewModel();
		    var main_view_model = new MainViewModel(floors_view_model);
		    var elevator = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
			var elevator_service = new ElevatorWrapper(elevator);  
			var elevator_controller = new ElevatorController(elevator_service, main_view_model);
			var main_ui = new MainView(main_view_model, stage);     
			elevator_controller.startTimer();     
    	}
    	catch (Exception e){
			System.out.println("ElevatorControlCenter: Exception during Startup " + e.getMessage());
    	} 
    }

    public static void main(String[] args) {
        launch();
    }

}