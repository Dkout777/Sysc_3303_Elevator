package testing;

import static org.junit.Assert.*;
import elevator.Channel;
import elevator.Data;
import elevator.Elevator;

import org.junit.Test;

public class TestElevator {
	
	/**
	 * tests that floor transitions up correctly
	 */
	@Test
	public void testTransitionFloorUp()  {
		Channel elevatorChannel = new Channel();
		Channel sendChannel = new Channel();
		Elevator elevator = new Elevator(elevatorChannel,sendChannel);
		elevator.setDestinationFloor(5);
		assertEquals(5,elevator.getDestinationFloor());
		elevator.floorTransition();
		assertEquals(2,elevator.getCurrentFloor());
		
	}
	/**
	 * tests that floor transitions down correctly
	 */
	@Test
	public void testTransitionFloorDown()  {
		Channel elevatorChannel = new Channel();
		Channel sendChannel = new Channel();
		Elevator elevator = new Elevator(elevatorChannel,sendChannel);
		elevator.setDestinationFloor(0);
		assertEquals(0,elevator.getDestinationFloor());
		elevator.floorTransition();
		assertEquals(0,elevator.getCurrentFloor());
		
	}
	@Test
	public void readAndParseData()  {
		Channel elevatorChannel = new Channel();
		Channel sendChannel = new Channel();
		Elevator elevator = new Elevator(elevatorChannel,sendChannel);
		elevator.readInputFile("ElevatorInputs.txt");
		int[][] testInputs = new int[4][2];
		testInputs [0][0] =5;
		testInputs [0][1] =1;
		testInputs [1][0] =3;
		testInputs [1][1] =5;
		testInputs [2][0] =6;
		testInputs [2][1] =10;
		testInputs [3][0] =4;
		testInputs [3][1] =25;
		assertEquals(testInputs, elevator.getTestInputs());
		
		
	}
	

}
