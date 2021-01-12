package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Window;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;
import org.testfx.matcher.base.ColorMatchers;
import org.testfx.service.finder.NodeFinder;
import org.testfx.service.finder.impl.NodeFinderImpl;
import org.testfx.service.query.NodeQuery;
import sqelevator.IElevator;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.MainView;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

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
	public void testInitialFloorFromModelToUi(FxRobot robot) {		
		int floor = 0;		
		try{
			floor = elevator_service.getElevatorFloor(0);
		}
		catch (Exception e){
			fail("Exception occured during call of getElevatorFloor method");
		}	
		verifyThat("##0FloorLabel", hasText("Current Floor: 0"));	
        Assertions.assertEquals(0, floor);
	}
	
	@Test
	public void testDirectionFromModelToUi(FxRobot robot) {	
		int direction = 0;		
		try{
			direction = elevator_service.getCommittedDirection(0);
		}
		catch (Exception e){
			fail("Exception occured during call of getCommittedDirection method");
		}	
		verifyThat("##0DirectionLabel", hasText("Direction: DOWN"));		
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, direction);
	}
	
	@Test
	public void testPayloadFromModelToUi(FxRobot robot) {		
		int payload = 0;		
		try{
			payload = elevator_service.getElevatorWeight(0);
		}
		catch (Exception e){
			fail("Exception occured during call of getElevatorWeight method");
		}	
		verifyThat("##0PayloadLabel", hasText("Payload: 700 kg"));	
        Assertions.assertEquals(700, payload);
	}
	
	@Test
	public void testPositionFromModelToUi(FxRobot robot) {		
		int position = 0;		
		try{
			position = elevator_service.getElevatorPosition(0);
		}
		catch (Exception e){
			fail("Exception occured during call of getElevatorPosition method");
		}		
		verifyThat("##0PositionLabel", hasText("Position: 0 feet"));	
        Assertions.assertEquals(0, position);
	}
	
	@Test
	public void testDoorStatusFromModelToUi(FxRobot robot) {	
		int door_status = 0;		
		try{
			door_status = elevator_service.getElevatorDoorStatus(0);
		}
		catch (Exception e){
			fail("Exception occured during call of getElevatorDoorStatus method");
		}	
		verifyThat("##0DoorLabel", hasText("Door Status: CLOSED"));		
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, door_status);
	}
	
	@Test
	public void testSpeedFromModelToUi(FxRobot robot) {
		int speed = 0;		
		try{
			speed = elevator_service.getElevatorSpeed(0);
		}
		catch (Exception e){
			fail("Exception occured during call of getElevatorSpeed method");
		}		
		verifyThat("##0SpeedLabel", hasText("Speed: 20"));	
        Assertions.assertEquals(20, speed);
	}
	
	@Test
	public void testSetTargetFloorFromUiToModel(FxRobot robot) {

		var rectangle = robot.lookup("##0ChangeButton");
		var t = (Button)rectangle.query();

		var fill = (Color)t.getBackground().getFills().get(0).getFill();
		var c = Color.rgb((int)(fill.getRed()*255),(int)(fill.getGreen()*255),(int)(fill.getBlue()*255));
		Assertions.assertEquals(fill,c);


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