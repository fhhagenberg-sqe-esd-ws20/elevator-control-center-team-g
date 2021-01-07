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
	private MainViewModel mainViewModel;
	private MockElevator elevator_service;
	private ElevatorController elevator_controller;
	private MainView mainUI;
	
	private final int INITIAL_NUMBER_OF_ELEVATORS = 4;
	private final int INITIAL_NUMBER_OF_FLOORS = 10;
	private final int INITIAL_FLOOR_HEIGHT = 9;
	private final int INITIAL_CAPACITY = 10;

	@Start
	public void start(Stage stage) throws Exception {
    	floors_view_model = new FloorsViewModel();
        mainViewModel = new MainViewModel(floors_view_model);
        elevator_service = new MockElevator(INITIAL_NUMBER_OF_ELEVATORS, INITIAL_NUMBER_OF_FLOORS, INITIAL_FLOOR_HEIGHT, INITIAL_CAPACITY);
    	elevator_controller = new ElevatorController(elevator_service, mainViewModel);
        mainUI = new MainView(mainViewModel, stage);    

        elevator_service.setServicesFloors(0, 4, false);
        elevator_service.setServicesFloors(0, 3, false);
        elevator_service.setServicesFloors(2, 7, false);
    	elevator_controller.startTimer();     	 	
	}

	@Test
	public void testBasicUiSetupFromMock(FxRobot robot) {
		verifyThat("##0ElevatorHeader", hasText("Elevator 0"));
		verifyThat("##0DirectionLabel", hasText("Direction: DOWN"));
		verifyThat("##0SpeedLabel", hasText("Speed: 20"));
		verifyThat("##0DoorLabel", hasText("Door Status: CLOSED"));
		verifyThat("##0PayloadLabel", hasText("Payload: 700 kg"));
	}
	
	@Test
	public void testManualNavigation(FxRobot robot) {
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));

		robot.clickOn("##0Button9");
		wait(robot);
		verifyThat("##0PositionLabel", hasText("Position: 81 feet"));
		verifyThat("##0FloorLabel", hasText("Current Floor: 9"));	
		
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
	}
	
	@Test
	public void testManualNavigationToDisabledFloor(FxRobot robot) {
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));
		
		robot.clickOn("##0Button3");
		wait(robot);
		verifyThat("##0PositionLabel", hasText("Position: 0 feet"));
		verifyThat("##0FloorLabel", hasText("Current Floor: 0"));	
		
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
	}
	
	@Test
	public void testEndToEndUiToModel(FxRobot robot) {
		// TODO:
		Assertions.assertEquals("test", "test");
	}
	
	@Test
	public void testEndToEndModelToUi(FxRobot robot) {
		// TODO:
		Assertions.assertEquals("test", "test");
	}

	private void wait(FxRobot robot) {
		robot.sleep(250); 
	}
}