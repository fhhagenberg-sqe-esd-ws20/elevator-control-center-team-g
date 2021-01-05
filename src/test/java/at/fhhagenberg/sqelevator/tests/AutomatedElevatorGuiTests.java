package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.App;
import at.fhhagenberg.sqe.view.MainView;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class AutomatedElevatorGuiTests {
	
	private FloorsViewModel floors_view_model;
	private MainViewModel mainViewModel;
	private MockElevator elevator_service;
	private ElevatorController elevator_controller;
	private MainView mainUI;

	@Start
	public void start(Stage stage) throws Exception {
    	floors_view_model = new FloorsViewModel();
        mainViewModel = new MainViewModel(floors_view_model);
        elevator_service = new MockElevator(4, 10, 9, 10);
    	elevator_controller = new ElevatorController(elevator_service, mainViewModel);
        mainUI = new MainView(mainViewModel, stage);    

        elevator_service.setServicesFloors(0, 4, false);
        elevator_service.setServicesFloors(0, 3, false);
        elevator_service.setServicesFloors(2, 7, false);
    	elevator_controller.startTimer();     	 	
	}

	@Test
	public void testEndToEndScenario(FxRobot robot) {
		// Check the GUI 
		verifyThat("##0ElevatorHeader", hasText("Elevator 0"));
		verifyThat("##0DirectionLabel", hasText("Direction: DOWN"));
		verifyThat("##0SpeedLabel", hasText("Speed: 20"));
		verifyThat("##0DoorLabel", hasText("Door Status: CLOSED"));
		verifyThat("##0PayloadLabel", hasText("Payload: 700 kg"));
		
		// Navigate Elevator 0
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));

		robot.clickOn("##0Button9");
		wait(robot);
		verifyThat("##0PositionLabel", hasText("Position: 81 feet"));
		verifyThat("##0FloorLabel", hasText("Current Floor: 9"));	
		
		// Click on disabled Floor
		robot.clickOn("##0Button3");
		wait(robot);
		verifyThat("##0PositionLabel", hasText("Position: 81 feet"));
		verifyThat("##0FloorLabel", hasText("Current Floor: 9"));	
		
		// Navigate Elevator 2
		robot.clickOn("##2ChangeButton");
		wait(robot);
		verifyThat("##2ManualLabel", hasText("Mode: MANU"));		
		
		robot.clickOn("##2Button2");
		wait(robot);
		verifyThat("##2PositionLabel", hasText("Position: 18 feet"));
		verifyThat("##2FloorLabel", hasText("Current Floor: 2"));	
		
		// Set the elevators back to auto mode
		robot.clickOn("##0ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
		
		robot.clickOn("##2ChangeButton");
		wait(robot);
		verifyThat("##0ManualLabel", hasText("Mode: AUTO"));
	}

	private void wait(FxRobot robot) {
		robot.sleep(250); 
	}
}