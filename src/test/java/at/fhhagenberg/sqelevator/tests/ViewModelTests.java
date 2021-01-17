package at.fhhagenberg.sqelevator.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.helper.Direction;
import at.fhhagenberg.sqe.helper.DoorState;
import at.fhhagenberg.sqe.helper.ModeState;
import at.fhhagenberg.sqe.viewmodel.ElevatorViewModel;
import at.fhhagenberg.sqe.viewmodel.FloorsViewModel;
import at.fhhagenberg.sqe.viewmodel.MainViewModel;
import at.fhhagenberg.sqelevator.mock.MockElevator;

@ExtendWith(MockitoExtension.class)
public class ViewModelTests {

	private ElevatorViewModel evm;
	private FloorsViewModel fvm;
	private MainViewModel mvm;

	private ElevatorController ec;
	private ElevatorController ec_spy;

	@BeforeEach
	public void setup() {

		fvm = new FloorsViewModel();
		mvm = new MainViewModel(fvm);

		ec = new ElevatorController(new MockElevator(1, 4, 9, 100), mvm);
		ec_spy = Mockito.spy(ec);
		evm = new ElevatorViewModel(0, 4, ec_spy);
		mvm.addElevatorModel(evm);
	}

	@Test
	void testElevatorViewModelSetAndGetValues() {
		var vec = new ArrayList<Integer>();
		vec.add(1);
		vec.add(2);

		evm.setId(0);
		evm.setSpeed(12);
		evm.setModeState(ModeState.AUTOMATIC);
		evm.setFloor(4);
		evm.setDoorState(DoorState.CLOSED);
		evm.setDirection(Direction.UP);
		evm.setDisabledFloors(vec);
		evm.setPayload(135);
		evm.setPosition(7);

		assertEquals(0, evm.getId());
		assertEquals(12, evm.getSpeed());
		assertEquals(ModeState.AUTOMATIC, evm.getModeState());
		assertEquals(4, evm.getFloor());
		assertEquals(DoorState.CLOSED, evm.getDoorState());
		assertEquals(Direction.UP, evm.getDirection());
		assertEquals(vec, evm.getDisabledFloors());
		assertEquals(135, evm.getPayload());
		assertEquals(7, evm.getPosition());
	}

	@Test
	void testFloorsViewModelSetAndGetValues() {
		var vec = new ArrayList<Integer>();
		vec.add(1);
		vec.add(2);

		fvm.setFloorsDOWN(vec);
		fvm.setFloorsUP(vec);
		fvm.setNumberOfFloors(10);

		assertEquals(vec, fvm.getFloorsDOWN());
		assertEquals(vec, fvm.getFloorsUP());
		assertEquals(10, fvm.getNumberOfFloors());
	}

	@Test
	void testElevatorViewModelChangeMode() {
		evm.setModeState(ModeState.AUTOMATIC);

		evm.changeMode();

		assertEquals(ModeState.MANUAL, evm.getModeState());

		evm.changeMode();

		assertEquals(ModeState.AUTOMATIC, evm.getModeState());
	}
	@Test
	void testMainViewModelGetAndSet() {
		mvm.setConnectionState(true);

		assertEquals(evm.getId(), mvm.getElevatorModels().get(0).getId());
		assertEquals(fvm, mvm.getFloorsModel());
		assertEquals(true, mvm.getConnectionState());

		mvm.setConnectionState(false);

		assertEquals(false, mvm.getConnectionState());
	}
}