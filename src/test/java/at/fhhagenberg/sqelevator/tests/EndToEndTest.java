package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;
import sqelevator.IElevator;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.MainView;

@ExtendWith(ApplicationExtension.class)
public class EndToEndTest {
	
	private FloorsViewModel floors_view_model;
	private MainViewModel main_view_model;
	private MockElevator elevator;
	private ElevatorController elevator_controller;
	private MainView main_ui;
	
	private final int INITIAL_NUMBER_OF_ELEVATORS = 4;
	private final int INITIAL_NUMBER_OF_FLOORS = 10;
	private final int INITIAL_FLOOR_HEIGHT = 9;
	private final int INITIAL_CAPACITY = 10;

	@Start
	public void start(Stage stage) throws Exception {
    	floors_view_model = new FloorsViewModel();
    	main_view_model = new MainViewModel(floors_view_model);
    	elevator = new MockElevator(INITIAL_NUMBER_OF_ELEVATORS, INITIAL_NUMBER_OF_FLOORS, INITIAL_FLOOR_HEIGHT, INITIAL_CAPACITY);
    	elevator_controller = new ElevatorController(elevator, main_view_model);
    	main_ui = new MainView(main_view_model, stage);   

    	elevator_controller.startController();     	 	
	}
	
	@Test
	void testInitialFloorFromModelToUi(FxRobot robot) throws RemoteException {		
		int floor = elevator.getElevatorFloorWrapped(0);
	
		verifyThat("##0FloorLabel", hasText("Current Floor: 0"));	
		
        Assertions.assertEquals(0, floor);
	}
	
	@Test
	void testDirectionFromModelToUi(FxRobot robot) throws RemoteException {	
		int direction = elevator.getCommittedDirectionWrapped(0);
	
		verifyThat("##0DirectionLabel", hasText("Direction: DOWN"));	
		
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, direction);
	}
	
	@Test
	void testPayloadFromModelToUi(FxRobot robot) throws RemoteException {		
		int payload = elevator.getElevatorWeightWrapped(0);
	
		verifyThat("##0PayloadLabel", hasText("Payload: 700 kg"));	
		
        Assertions.assertEquals(700, payload);
	}
	
	@Test
	void testPositionFromModelToUi(FxRobot robot) throws RemoteException {		
		int position = elevator.getElevatorPositionWrapped(0);
		
		verifyThat("##0PositionLabel", hasText("Position: 0 feet"));
		
        Assertions.assertEquals(0, position);
	}
	
	@Test
	void testDoorStatusFromModelToUi(FxRobot robot) throws RemoteException {	
		int door_status = elevator.getElevatorDoorStatusWrapped(0);
		
		verifyThat("##0DoorLabel", hasText("Door Status: CLOSED"));	
		
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, door_status);
	}
	
	@Test
	void testSpeedFromModelToUi(FxRobot robot) throws RemoteException {
		int speed = elevator.getElevatorSpeedWrapped(0);
		
		verifyThat("##0SpeedLabel", hasText("Speed: 20"));	
		
        Assertions.assertEquals(20, speed);
	}
	
	@Test
	void testSetTargetFloorFromUiToModel(FxRobot robot) throws RemoteException {
		robot.clickOn("##0ChangeButton");
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));		
		robot.clickOn("##0Button9");	
		
		int target_floor = elevator.getTargetWrapped(0);	
		
        Assertions.assertEquals(9, target_floor);
	}
}