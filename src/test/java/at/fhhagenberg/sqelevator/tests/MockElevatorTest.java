package at.fhhagenberg.sqelevator.tests;


import at.fhhagenberg.sqelevator.IElevator;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import at.fhhagenberg.sqelevator.mock.MockElevatorConstants;
import at.fhhagenberg.sqelevator.mock.MockElevatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class MockElevatorTest {
    private MockElevator mocked;

    private final static Integer floors = 3;
    private final static Integer floor_height = 9;
    private final static Integer elevator_max_pax = 10;
    private final static Integer elevator_count = 2;

    @BeforeEach
    public void setup() {
        mocked = new MockElevator(elevator_count, floors, floor_height, elevator_max_pax);
    }

    @Test
    void testGetTarget() throws Exception {
        Assertions.assertEquals(0, mocked.getTarget(0));
        Assertions.assertEquals(0, mocked.getTarget(1));
    }

    @Test
    public void testGetFloorHeight() throws Exception {
        Assertions.assertEquals(floor_height, mocked.getFloorHeight());
    }

    @Test
    public void testGetClockTick() throws Exception {
        Assertions.assertEquals(MockElevatorConstants.CLOCK_TICK, mocked.getClockTick());
    }

    @Test
    void testGetElevatorPosition() throws Exception {
        int floor_1 = 1;
        int floor_2 = 2;

        Assertions.assertEquals(0, mocked.getElevatorPosition(0));
        Assertions.assertEquals(0, mocked.getElevatorPosition(1));

        mocked.setTarget(0, floor_1);
        mocked.setTarget(1, floor_2);

        Assertions.assertEquals(floor_height * floor_1, mocked.getElevatorPosition(0));
        Assertions.assertEquals(floor_height * floor_2, mocked.getElevatorPosition(1));
    }

    @Test
    void testSetFloorButtonActive_Invalid() {
        int floor_0 = 0;
        int floor_3 = 3;

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getElevators().get(0).setFloorButtonActive(floor_0 - 1, true));
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getElevators().get(1).setFloorButtonActive(floor_3 + 1, true));
    }

    @Test
    void testSetDoorStatus_Invalid() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getElevators().get(0).setDoorStatus(IElevator.ELEVATOR_DOORS_OPEN - 1));
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getElevators().get(0).setDoorStatus(IElevator.ELEVATOR_DOORS_CLOSING + 1));
    }

    @Test
    void testGetCommitedDirection() throws Exception {
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED,
                mocked.getCommittedDirection(1));
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED,
                mocked.getCommittedDirection(0));
    }

    @Test
    void testCommittedDirectionSetGet() throws Exception {
        mocked.setCommittedDirection(1, IElevator.ELEVATOR_DIRECTION_UP);
        mocked.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_DOWN);

        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN,
                mocked.getCommittedDirection(0));
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UP,
                mocked.getCommittedDirection(1));

        mocked.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
        mocked.setCommittedDirection(1, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);

        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED,
                mocked.getCommittedDirection(0));
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED,
                mocked.getCommittedDirection(1));
    }

    @Test
    void testGetServicesFloors() throws Exception {
        int floor_0 = 0;
        int floor_1 = 1;
        int floor_2 = 2;

        Assertions.assertTrue(mocked.getServicesFloors(0, floor_0));
        Assertions.assertTrue(mocked.getServicesFloors(1, floor_0));

        Assertions.assertTrue(mocked.getServicesFloors(0, floor_1));
        Assertions.assertTrue(mocked.getServicesFloors(1, floor_1));

        Assertions.assertTrue(mocked.getServicesFloors(0, floor_2));
        Assertions.assertTrue(mocked.getServicesFloors(1, floor_2));
    }

    @Test
    void testGetServicesFloors_InvalidElevator() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloors(-1, 0));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloors(elevator_count, 0));
    }

    @Test
    void testGetServicesFloors_InvalidFloors() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloors(0, -1));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloors(0, floors));
    }

    @Test
    void testSetDoorStatus() throws Exception {
        mocked.getElevators().get(0).setDoorStatus(IElevator.ELEVATOR_DOORS_OPENING);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_OPENING, mocked.getElevatorDoorStatus(0));
        mocked.getElevators().get(1).setDoorStatus(IElevator.ELEVATOR_DOORS_CLOSING);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_CLOSING, mocked.getElevatorDoorStatus(1));

        mocked.getElevators().get(0).setDoorStatus(IElevator.ELEVATOR_DOORS_OPEN);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_OPEN, mocked.getElevatorDoorStatus(0));
        mocked.getElevators().get(1).setDoorStatus(IElevator.ELEVATOR_DOORS_CLOSED);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, mocked.getElevatorDoorStatus(1));
    }

    @Test
    void testGetElevatorButton() throws Exception {
        mocked.getElevators().get(0).setFloorButtonActive(0, true);
        mocked.getElevators().get(1).setFloorButtonActive(1, true);

        Assertions.assertTrue(mocked.getElevatorButton(0, 0));
        Assertions.assertFalse(mocked.getElevatorButton(1, 0));

        Assertions.assertFalse(mocked.getElevatorButton(0, 1));
        Assertions.assertTrue(mocked.getElevatorButton(1, 1));

        Assertions.assertFalse(mocked.getElevatorButton(0, 2));
        Assertions.assertFalse(mocked.getElevatorButton(1, 2));
    }

    @Test
    void testTargetSetGet() throws Exception {
        mocked.setTarget(0, 1);
        Assertions.assertEquals(1, mocked.getTarget(0));
        mocked.setTarget(1, 2);
        Assertions.assertEquals(2, mocked.getTarget(1));
    }

    @Test
    void testGetCommittedDirection_InvalidElevatorNumber() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getCommittedDirection(-1));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getCommittedDirection(elevator_count));
    }

    @Test
    void testSetCommittedDirection_Invalid() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP - 1));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED + 1));
    }

    @Test
    void testServicesFloorsSetGet() throws Exception {
        mocked.setServicesFloors(0, 0, false);
        Assertions.assertFalse(mocked.getServicesFloors(0, 0));
        Assertions.assertTrue(mocked.getServicesFloors(1, 0));

        mocked.setServicesFloors(1, 1, false);
        Assertions.assertTrue(mocked.getServicesFloors(0, 1));
        Assertions.assertFalse(mocked.getServicesFloors(1, 1));

        mocked.setServicesFloors(0, 0, false);
        Assertions.assertFalse(mocked.getServicesFloors(0, 0));
        mocked.setServicesFloors(0, 1, false);
        Assertions.assertFalse(mocked.getServicesFloors(0, 1));
        mocked.setServicesFloors(0, 2, false);
        Assertions.assertFalse(mocked.getServicesFloors(0, 2));
    }

}
