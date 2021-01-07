package at.fhhagenberg.sqe;

import javafx.application.Application;
import javafx.stage.Stage;
import java.rmi.Naming;
import java.rmi.RemoteException;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.MainView;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.ElevatorWrapper;
import at.fhhagenberg.sqelevator.IElevator;

/**
 * JavaFX App
 */
public class ElevatorControlCenter extends Application {

    @Override
    public void start(Stage stage) throws RemoteException {
    	var floors_view_model = new FloorsViewModel();
        var mainViewModel = new MainViewModel(floors_view_model);
        
        try {
            var elevator = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");	
            var elevator_service = new ElevatorWrapper(elevator);        
        	var elevator_controller = new ElevatorController(elevator_service, mainViewModel);
            var mainUI = new MainView(mainViewModel, stage);     
        	elevator_controller.startTimer();        	
        	elevator_service.setServicesFloors(0, 2, false);
        	elevator_service.setServicesFloors(0, 3, false);
        	elevator_service.setCommittedDirection(0, 2);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }       
    }

    public static void main(String[] args) {
        launch();
    }

}