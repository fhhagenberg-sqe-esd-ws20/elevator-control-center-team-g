package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.stage.Stage;
import java.rmi.Naming;
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
			var floorsViewModel = new FloorsViewModel();
		    var mainViewModel = new MainViewModel(floorsViewModel);
		    var elevator = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
			var elevatorService = new ElevatorWrapper(elevator);  
			var elevatorController = new ElevatorController(elevatorService, mainViewModel);
			new MainView(mainViewModel, stage);     
			elevatorController.startController();     
    	}
    	catch (Exception e) {
    		return;
    	} 
    }

    public static void main(String[] args) {
        launch();
    }

}