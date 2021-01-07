package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import at.fhhagenberg.sqe.viewmodel.IMainViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import sqelevator.IElevatorWrapper;

import java.rmi.RemoteException;
import java.util.Timer;

@ExtendWith(MockitoExtension.class)
public class ViewModelMockTests {
	ElevatorController ec;

	@Mock
	IMainViewModel mvvm;

	@Mock
	IElevatorWrapper ew;

	@BeforeEach
	public void setup() {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(new FloorsViewModel());
		ec = new ElevatorController(ew, mvvm);
	}
	@Test
	void testHandleElevatorPositionChange() throws RemoteException {
		ec.handleElevatorPositionChange(1,1);
		Mockito.verify(ew).setTarget(1,1);
	}
}
