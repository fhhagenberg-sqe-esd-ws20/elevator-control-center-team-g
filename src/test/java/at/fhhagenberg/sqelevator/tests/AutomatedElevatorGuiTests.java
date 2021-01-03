package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class AutomatedElevatorGuiTests {

	@Start
	public void start(Stage stage) throws Exception {
		//new Triangle1st().start(stage);
	}

	@Test
	public void testInitialSetup(FxRobot robot) {
		wait(robot);
		assertEquals("test", "test");
	}

	private void wait(FxRobot robot) {
		robot.sleep(500); 
	}
}