package at.fhhagenberg.sqelevator.tests;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.MainView;

@ExtendWith(ApplicationExtension.class)
public class AutomatedGuiTest {
	
	private FloorsViewModel floors_view_model;
	private MainViewModel main_view_model;
	private MockElevator elevator_service;
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
        elevator_service = new MockElevator(INITIAL_NUMBER_OF_ELEVATORS, INITIAL_NUMBER_OF_FLOORS, INITIAL_FLOOR_HEIGHT, INITIAL_CAPACITY);
    	elevator_controller = new ElevatorController(elevator_service, main_view_model);
    	main_ui = new MainView(main_view_model, stage);    

        elevator_service.setServicesFloorsWrapped(0, 3, false);
    	elevator_controller.startController();     	 	
	}

	@Test
	public void testBasicUiSetupFromMockHeader(FxRobot robot) {
		verifyThat("##0ElevatorHeader", hasText("Elevator 0"));
	}
	
	@Test
	public void testBasicUiSetupFromMockDirection(FxRobot robot) {
		verifyThat("##0DirectionLabel", hasText("Direction: DOWN"));
	}
	
	@Test
	public void testBasicUiSetupFromMockSpeed(FxRobot robot) {
		verifyThat("##0SpeedLabel", hasText("Speed: 20"));
	}
	
	@Test
	public void testBasicUiSetupFromMockDoor(FxRobot robot) {
		verifyThat("##0DoorLabel", hasText("Door Status: CLOSED"));
	}
	
	@Test
	public void testBasicUiSetupFromMockPayload(FxRobot robot) {
		verifyThat("##0PayloadLabel", hasText("Payload: 700 kg"));
	}
	
	@Test
	public void testSwitchingToManualMode(FxRobot robot) {
		robot.clickOn("##0ChangeButton");		
		wait(robot);		
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));
	}
	
	@Test
	public void testSwitchingToManualModeAndBack(FxRobot robot) {
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));
		
		robot.clickOn("##0ChangeButton");		
		wait(robot);		
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
	}
	
	@Test
	public void testManualNavigation(FxRobot robot) {
		// Put elevator 0 into manual mode
		robot.clickOn("##0ChangeButton");		
		wait(robot);		
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));

		// Navigate elevator 0 to target floor
		robot.clickOn("##0Button9");
		wait(robot);
		verifyThat("##0PositionLabel", hasText("Position: 81 feet"));
		verifyThat("##0FloorLabel", hasText("Current Floor: 9"));	
		
		// Put elevator 0 into automatic mode
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
	}
	
	@Test
	public void testManualNavigationToDisabledFloor(FxRobot robot) {
		// Put elevator 0 into manual mode
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));
		
		// Navigate elevator 0 to disabled floor
		robot.clickOn("##0Button3");
		wait(robot);
		verifyThat("##0PositionLabel", hasText("Position: 0 feet"));
		verifyThat("##0FloorLabel", hasText("Current Floor: 0"));	
		
		// Put elevator 0 into automatic mode
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
	}	
	
	@Test
	public void testConnectionError(FxRobot robot) {
		// Put elevator 0 into manual mode
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));
		
		// Navigate elevator 0 to disabled floor
		robot.clickOn("##0Button3");
		wait(robot);
		verifyThat("##0PositionLabel", hasText("Position: 0 feet"));
		verifyThat("##0FloorLabel", hasText("Current Floor: 0"));	
		
		// Put elevator 0 into automatic mode
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
	}

	private void wait(FxRobot robot) {
		robot.sleep(250); 
	}
}