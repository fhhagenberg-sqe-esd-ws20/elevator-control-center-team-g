package at.fhhagenberg.sqelevator.tests;

import java.rmi.RemoteException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.mockito.Mockito.never;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.view.MainView;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.IFloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.IMainViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import javafx.stage.Stage;
import sqelevator.ElevatorWrapper;
import sqelevator.IElevator;
import sqelevator.IElevatorWrapper;

@ExtendWith(MockitoExtension.class)
public class ReconnectionTest {

	@Mock
    IMainViewModel mvvm;

	@Mock
    IElevatorWrapper ew;

    @Mock
    IFloorsViewModel fmod;
    
    private final int INITIAL_NUMBER_OF_ELEVATORS = 4;
	private final int INITIAL_NUMBER_OF_FLOORS = 10;
	private final int INITIAL_FLOOR_HEIGHT = 9;
	private final int INITIAL_CAPACITY = 10;
	
    
	@Test
    void testReconnectOnExceptionFloorNum() throws Exception {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
        Mockito.when(ew.getFloorNumWrapped()).thenThrow(RemoteException.class);
        
        ElevatorController ec = new ElevatorController(ew, mvvm);
        
        Mockito.verify(ew).reconnect();
	}
	
	@Test
    void testReconnectOnExceptionElevatorNum() throws Exception {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
        Mockito.when(ew.getElevatorNumWrapped()).thenThrow(RemoteException.class);
        
        ElevatorController ec = new ElevatorController(ew, mvvm);
        
        Mockito.verify(ew).reconnect();
	}
	
	@Test
    void testReconnectOnException() throws Exception {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
		Mockito.when(ew.getFloorNumWrapped()).thenReturn(5);
		Mockito.when(ew.getElevatorNumWrapped()).thenReturn(3);
        Mockito.when(ew.getElevatorSpeedWrapped(0)).thenThrow(RemoteException.class);
        
        ElevatorController ec = new ElevatorController(ew, mvvm);        
        ec.startController();    
        
        Mockito.verify(ew).reconnect();
	}
}
