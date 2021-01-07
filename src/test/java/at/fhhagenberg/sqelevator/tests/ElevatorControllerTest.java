package at.fhhagenberg.sqelevator.tests;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.IMainViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sqelevator.IElevator;
import sqelevator.IElevatorWrapper;

import java.rmi.RemoteException;

@ExtendWith(MockitoExtension.class)
public class ElevatorControllerTest {
    ElevatorController ec;

    @Mock
    IElevatorWrapper ew;

    @Mock
    IMainViewModel mvvm;

    @BeforeEach
    void setUp(){
        Mockito.when(mvvm.getFloorsModel()).thenReturn(new FloorsViewModel());
        ec = new ElevatorController(ew, mvvm);
    }

    @Test
    void test() throws RemoteException {

    }
}
