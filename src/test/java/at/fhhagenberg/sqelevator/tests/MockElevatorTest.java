package at.fhhagenberg.sqelevator.tests;


import sqelevator.IElevator;
import at.fhhagenberg.sqelevator.mock.MockElevator;
import at.fhhagenberg.sqelevator.mock.MockElevatorConstants;
import at.fhhagenberg.sqelevator.mock.MockElevatorException;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.rmi.RemoteException;

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
        Assertions.assertEquals(0, mocked.getTargetWrapped(0));
        Assertions.assertEquals(0, mocked.getTargetWrapped(1));
    }

    @Test
    public void testGetFloorHeight() throws Exception {
        Assertions.assertEquals(floor_height, mocked.getFloorHeightWrapped());
    }

    @Test
    public void testGetClockTick() throws Exception {
        Assertions.assertEquals(MockElevatorConstants.CLOCK_TICK, mocked.getClockTickWrapped());
    }

    @Test
    void testGetElevatorPosition() throws Exception {
        int floor_1 = 1;
        int floor_2 = 2;

        Assertions.assertEquals(0, mocked.getElevatorPositionWrapped(0));
        Assertions.assertEquals(0, mocked.getElevatorPositionWrapped(1));

        mocked.setTargetWrapped(0, floor_1);
        mocked.setTargetWrapped(1, floor_2);

        Assertions.assertEquals(floor_height * floor_1, mocked.getElevatorPositionWrapped(0));
        Assertions.assertEquals(floor_height * floor_2, mocked.getElevatorPositionWrapped(1));
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
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN,
                mocked.getCommittedDirectionWrapped(1));
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN,
                mocked.getCommittedDirectionWrapped(0));
    }

    @Test
    void testCommittedDirectionSetGet() throws Exception {
        mocked.setCommittedDirectionWrapped(1, IElevator.ELEVATOR_DIRECTION_UP);
        mocked.setCommittedDirectionWrapped(0, IElevator.ELEVATOR_DIRECTION_DOWN);

        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN,
                mocked.getCommittedDirectionWrapped(0));
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UP,
                mocked.getCommittedDirectionWrapped(1));

        mocked.setCommittedDirectionWrapped(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
        mocked.setCommittedDirectionWrapped(1, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);

        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED,
                mocked.getCommittedDirectionWrapped(0));
        Assertions.assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED,
                mocked.getCommittedDirectionWrapped(1));
    }

    @Test
    void testGetServicesFloors() throws Exception {
        int floor_0 = 0;
        int floor_1 = 1;
        int floor_2 = 2;

        Assertions.assertTrue(mocked.getServicesFloorsWrapped(0, floor_0));
        Assertions.assertTrue(mocked.getServicesFloorsWrapped(1, floor_0));

        Assertions.assertTrue(mocked.getServicesFloorsWrapped(0, floor_1));
        Assertions.assertTrue(mocked.getServicesFloorsWrapped(1, floor_1));

        Assertions.assertTrue(mocked.getServicesFloorsWrapped(0, floor_2));
        Assertions.assertTrue(mocked.getServicesFloorsWrapped(1, floor_2));
    }

    @Test
    void testGetServicesFloors_InvalidElevator() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloorsWrapped(-1, 0));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloorsWrapped(elevator_count, 0));
    }

    @Test
    void testGetServicesFloors_InvalidFloors() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloorsWrapped(0, -1));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getServicesFloorsWrapped(0, floors));
    }

    @Test
    void testSetDoorStatus() throws Exception {
        mocked.getElevators().get(0).setDoorStatus(IElevator.ELEVATOR_DOORS_OPENING);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_OPENING, mocked.getElevatorDoorStatusWrapped(0));
        mocked.getElevators().get(1).setDoorStatus(IElevator.ELEVATOR_DOORS_CLOSING);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_CLOSING, mocked.getElevatorDoorStatusWrapped(1));

        mocked.getElevators().get(0).setDoorStatus(IElevator.ELEVATOR_DOORS_OPEN);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_OPEN, mocked.getElevatorDoorStatusWrapped(0));
        mocked.getElevators().get(1).setDoorStatus(IElevator.ELEVATOR_DOORS_CLOSED);
        Assertions.assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, mocked.getElevatorDoorStatusWrapped(1));
    }

    @Test
    void testGetElevatorButton() throws Exception {
        mocked.getElevators().get(0).setFloorButtonActive(0, true);
        mocked.getElevators().get(1).setFloorButtonActive(1, true);

        Assertions.assertTrue(mocked.getElevatorButtonWrapped(0, 0));
        Assertions.assertFalse(mocked.getElevatorButtonWrapped(1, 0));

        Assertions.assertFalse(mocked.getElevatorButtonWrapped(0, 1));
        Assertions.assertTrue(mocked.getElevatorButtonWrapped(1, 1));

        Assertions.assertFalse(mocked.getElevatorButtonWrapped(0, 2));
        Assertions.assertFalse(mocked.getElevatorButtonWrapped(1, 2));
    }

    @Test
    void testTargetSetGet() throws Exception {
        mocked.setTargetWrapped(0, 1);
        Assertions.assertEquals(1, mocked.getTargetWrapped(0));
        mocked.setTargetWrapped(1, 2);
        Assertions.assertEquals(2, mocked.getTargetWrapped(1));
    }

    @Test
    void testGetCommittedDirection_InvalidElevatorNumber() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getCommittedDirectionWrapped(-1));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.getCommittedDirectionWrapped(elevator_count));
    }

    @Test
    void testSetCommittedDirection_Invalid() {
        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.setCommittedDirectionWrapped(0, IElevator.ELEVATOR_DIRECTION_UP - 1));

        Assertions.assertThrows(MockElevatorException.class, () ->
                mocked.setCommittedDirectionWrapped(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED + 1));
    }

    @Test
    void testServicesFloorsSetGet() throws Exception {
        mocked.setServicesFloorsWrapped(0, 0, false);
        Assertions.assertFalse(mocked.getServicesFloorsWrapped(0, 0));
        Assertions.assertTrue(mocked.getServicesFloorsWrapped(1, 0));

        mocked.setServicesFloorsWrapped(1, 1, false);
        Assertions.assertTrue(mocked.getServicesFloorsWrapped(0, 1));
        Assertions.assertFalse(mocked.getServicesFloorsWrapped(1, 1));

        mocked.setServicesFloorsWrapped(0, 0, false);
        Assertions.assertFalse(mocked.getServicesFloorsWrapped(0, 0));
        mocked.setServicesFloorsWrapped(0, 1, false);
        Assertions.assertFalse(mocked.getServicesFloorsWrapped(0, 1));
        mocked.setServicesFloorsWrapped(0, 2, false);
        Assertions.assertFalse(mocked.getServicesFloorsWrapped(0, 2));
    }

    @Test
    void testGetFloorNum() throws RemoteException {
        Assertions.assertEquals(floors, mocked.getFloorNumWrapped());
    }

    @Test
    void testGetElevatorNum() throws RemoteException {
        Assertions.assertEquals(elevator_count, mocked.getElevatorNumWrapped());
    }

    @Test
    void testGetElevatorWeight() throws RemoteException {
        Assertions.assertEquals(700, mocked.getElevatorWeightWrapped(0));
    }

    @Test
    void testGetElevatorCapacity() throws RemoteException {
        Assertions.assertEquals(elevator_max_pax, mocked.getElevatorCapacityWrapped(0));
    }

    @Test
    void testGetFloors() throws RemoteException {
        var floors = mocked.getFloors();
        Assertions.assertEquals(3, floors.size());
        Assertions.assertFalse(floors.get(0).getDownButtonState());
        Assertions.assertFalse(floors.get(0).getUpButtonState());
        floors.get(1).setDownButtonState(true);
        Assertions.assertTrue(floors.get(1).getDownButtonState());
        Assertions.assertFalse(floors.get(1).getUpButtonState());
        floors.get(2).setUpButtonState(true);
        Assertions.assertFalse(floors.get(2).getDownButtonState());
        Assertions.assertTrue(floors.get(2).getUpButtonState());
    }
    @Test
    void testGetElevatorFloor() throws RemoteException {
        Assertions.assertEquals(0, mocked.getElevatorFloorWrapped(0));
        mocked.setTargetWrapped(0,1);
        Assertions.assertEquals(1, mocked.getElevatorFloorWrapped(0));
        mocked.setTargetWrapped(0,2);
        Assertions.assertEquals(2, mocked.getElevatorFloorWrapped(0));
    }
    @Test
    void testGetElevatorSpeed() throws RemoteException {
        Assertions.assertEquals(20, mocked.getElevatorSpeedWrapped(0));
    }
    @Test
    void testGetFloorButtonUp() throws RemoteException {
        Assertions.assertEquals(false, mocked.getFloorButtonUpWrapped(1));
    }
    @Test
    void testGetFloorButtonDown() throws RemoteException {
        Assertions.assertEquals(false, mocked.getFloorButtonDownWrapped(1));
    }
    @Test
    void testGetElevatorAccel() throws RemoteException {
        Assertions.assertEquals(10, mocked.getElevatorAccelWrapped(1));
    }
}
