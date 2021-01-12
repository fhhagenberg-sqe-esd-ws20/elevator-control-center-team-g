package at.fhhagenberg.sqelevator.tests;

import at.fhhagenberg.sqe.viewmodel.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.controller.ElevatorController;
import sqelevator.IElevatorWrapper;

import java.rmi.RemoteException;

@ExtendWith(MockitoExtension.class)
public class ElevatorControllerMockTests {

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
    void testInitFloors_WithError() throws RemoteException {
        Mockito.when(ew.getFloorNum()).thenThrow(new RemoteException());
        Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
        ElevatorController ec = new ElevatorController(ew, mvvm);
        Mockito.verify(ew).getElevatorNum();
        Mockito.verify(mvvm).setConnectionState(false);
    }

    @Test
    void testInitElevators_WithError() throws RemoteException {
        Mockito.when(ew.getFloorNum()).thenReturn(5);
        Mockito.when(ew.getElevatorNum()).thenThrow(new RemoteException());
        Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);
        ElevatorController ec = new ElevatorController(ew, mvvm);
        Mockito.verify(ew).getElevatorNum();
        Mockito.verify(mvvm).setConnectionState(false);
        Mockito.verify(fmod).setNumberOfFloors(5);
    }

    @Test
    void testGetElevatorDoorStatus() throws Exception {
        Mockito.when(ew.getFloorNum()).thenReturn(5);
        Mockito.when(ew.getElevatorNum()).thenThrow(new RemoteException());
        Mockito.when(mvvm.getFloorsModel()).thenReturn(fmod);


        ElevatorController ec = new ElevatorController(ew, mvvm);
        Mockito.verify(ew).getElevatorNum();
        Mockito.verify(mvvm).setConnectionState(false);
        Mockito.verify(fmod).setNumberOfFloors(5);


    }
}
