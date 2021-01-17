package at.fhhagenberg.sqelevator.tests;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.Timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
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

import at.fhhagenberg.sqe.connection.ConnectionException;

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
        Mockito.doThrow(ConnectionException.class).when(ew).reconnect();
        
        ElevatorController ec = new ElevatorController(ew, mvvm);
        
        Mockito.verify(ew).reconnect();
	}
	
	@Test
    void testReconnectOnExceptionElevatorNum() throws Exception {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
        Mockito.when(ew.getElevatorNumWrapped()).thenThrow(RemoteException.class);
        Mockito.doNothing().doThrow(ConnectionException.class).when(ew).reconnect();
        
        ElevatorController ec = new ElevatorController(ew, mvvm);
        
        Mockito.verify(ew, Mockito.atLeast(1)).reconnect();
	}
	
	@Test
    void testReconnectOnException() throws Exception {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
		Mockito.when(ew.getFloorNumWrapped()).thenReturn(5);
		Mockito.when(ew.getElevatorNumWrapped()).thenReturn(3);
        Mockito.when(ew.getElevatorSpeedWrapped(0)).thenThrow(RemoteException.class);
        Mockito.doNothing().doThrow(ConnectionException.class).when(ew).reconnect();
        
        ElevatorController ec = new ElevatorController(ew, mvvm);
        
        Mockito.verify(ew, Mockito.atLeast(1)).reconnect();
	}
}
