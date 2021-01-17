package at.fhhagenberg.sqelevator.tests;

import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.ColorMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import at.fhhagenberg.sqe.connection.ConnectionException;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.MainView;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sqelevator.IElevatorWrapper;

@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
class ReconnectionGUITest {
	
	private FloorsViewModel floors_view_model;
	private MainViewModel main_view_model;
	private ElevatorController elevator_controller;
	private MainView main_ui;
	
	@Mock
	private IElevatorWrapper elevator_service;
	

	@Start
	public void start(Stage stage) throws Exception {
		Mockito.when(elevator_service.getFloorNumWrapped()).thenReturn(5);
		Mockito.when(elevator_service.getElevatorNumWrapped()).thenReturn(3);
        Mockito.when(elevator_service.getElevatorNumWrapped()).thenThrow(RemoteException.class);
        Mockito.doNothing().doThrow(ConnectionException.class).when(elevator_service).reconnect();
        
    	floors_view_model = new FloorsViewModel();
    	main_view_model = new MainViewModel(floors_view_model);
    	elevator_controller = new ElevatorController(elevator_service, main_view_model);
    	main_ui = new MainView(main_view_model, stage);    

        elevator_service.setServicesFloorsWrapped(0, 3, false);    	 
        
	}
	
	@Test
    void testReconnectOnException() throws Exception {
        FxAssert.verifyThat("#statusText", hasText("ERROR"));
	}

}
