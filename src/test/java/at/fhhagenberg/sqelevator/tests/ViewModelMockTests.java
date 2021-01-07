package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import at.fhhagenberg.sqe.viewmodel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.controller.ElevatorController;
import sqelevator.IElevatorWrapper;

import java.rmi.RemoteException;
import java.util.Timer;

@ExtendWith(MockitoExtension.class)
public class ViewModelMockTests {

	@Mock
	IMainViewModel mvvm;

	@Mock
	IElevatorWrapper ew;

	@Mock
	IFloorsViewModel fmod;

	@Test
	void testConstructor() throws RemoteException {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
		Mockito.when(ew.getFloorNum()).thenReturn(5);
		ElevatorController ec = new ElevatorController(ew, mvvm);
		Mockito.verify(fmod).setNumberOfFloors(5);
		Mockito.verify(ew).getElevatorNum();
	}


	@Test
	void testHandleElevatorPositionChange() throws RemoteException {
		Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
		ElevatorController ec = new ElevatorController(ew, mvvm);

		ec.handleElevatorPositionChange(1,1);
		Mockito.verify(ew).setTarget(1,1);
	}
}
