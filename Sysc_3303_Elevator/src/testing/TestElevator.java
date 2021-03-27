package testing;

import static org.junit.Assert.*;
import elevator.Channel;
import elevator.Data;
import elevator.Elevator;
import elevator.Elevator.ElevatorState;

import org.junit.Test;

public class TestElevator {
	
	/**
	 * tests that elevator starts on correct floor
	 */
	@Test
	public void testStartingFloor()  {
		Channel elevatorChannel = new Channel();
		Channel sendChannel = new Channel();
		Elevator elevator = new Elevator(elevatorChannel,sendChannel,1,5);
		
		assertEquals(1,elevator.getCurrentFloor());
	}
	
	/**
	 * tests that elevator starts on correct State
	 */
	@Test
	public void testStartingState()  {
		Channel elevatorChannel = new Channel();
		Channel sendChannel = new Channel();
		Elevator elevator = new Elevator(elevatorChannel,sendChannel,1,5);
		
		assertEquals(ElevatorState.Waiting,elevator.getCurrentState());
	}
		


}
