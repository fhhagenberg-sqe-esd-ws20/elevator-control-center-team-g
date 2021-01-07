package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.fail;
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
public class EndToEndTest {
	
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

    	elevator_controller.startTimer();     	 	
	}
	
	@Test
	public void testSetTargetFloorFromUi(FxRobot robot) {
		robot.clickOn("##0ChangeButton");
		verifyThat("##0ManualLabel", hasText("Mode: MANU"));		
		robot.clickOn("##0Button9");
		
		int target_floor = 0;
		
		try{
			target_floor = elevator_service.getTarget(0);
		}
		catch (Exception e){
			fail("Exception occured during call of getTarget method");
		}
        Assertions.assertEquals(9, target_floor);
	}
}