package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.stage.Stage;

import sqelevator.IElevatorWrapper;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
        var floorsViewModel = new FloorsViewModel();
        var mainViewModel = new MainViewModel(floorsViewModel);
        IElevator elevator = null;
        var elevatorService = new ElevatorWrapper(elevator);  
        var elevatorController = new ElevatorController(elevatorService, mainViewModel);
        main_view_model.setController(elevatorController);
        new MainView(mainViewModel, stage);     
        elevatorController.doConnect();     
    	}
    	catch (Exception e){
			System.out.println("ElevatorControlCenter: Exception during Startup " + e.getMessage());
    	} 
    }

    public static void main(String[] args) {
        launch();
    }

}