/**The elevator system class
 * @author Dimitry Koutchine
 * @version 02/04/2021
 */
package elevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Elevator implements Runnable{
	
	private enum ElevatorState{//Possible state for elevator
		Waiting,DoorsClosing,DoorOpening,Moving
	}
	private int elevatorId;
	private Channel subsystemToElevator;
	private Channel elevatorToSubsystem;
	private int currentFloor = 1;
	private final int doorMoveTime =3; // time it takes to open/close doors
	private long startingTime;// Time taken when program is first run
	private volatile ElevatorState currentState;
	private boolean up = false;
	private boolean [] buttons;// array of buttons
	private volatile float  stateStartTime;
	

	/**
	 * Constructor
	 * 
	 * @param elevatorChannel, receive the channel, elevatorChannel is used for putting inputs into scheduler, while receive is used for getting inputs.
	 */
	public Elevator(Channel subsystemToElevator, Channel elevatorToSubsystem,int elevatorId, int buttonAmount ) {
		this.elevatorId = elevatorId;
		this.subsystemToElevator = subsystemToElevator;
		this.elevatorToSubsystem = elevatorToSubsystem;
		buttons = new boolean [buttonAmount];
		currentState = ElevatorState.Waiting;
		
	}
	
	
	/**
	 * getter for currentFloor
	 * @return in representing currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	public void printButtons() {
		System.out.println("The current buttons are lit up:");
		for(int i = 0; i < buttons.length; i++) {
			if(i == buttons.length-1) {
				System.out.print(i+ ".");
			}
			else {
				System.out.print(i+ ", ");
			}
			
		}
	}
	

	
	
	/**
	 * A method run as it's own thread for checking for stop requests or button/ floor changes.
	 */
	public  void checkForButtonUpdate() {
		Data receivedData = null;
		System.out.println("Task is running");
		while(true) {
			
			if(!subsystemToElevator.empty() && (subsystemToElevator.peekData().getRequestType() !=2)){
				receivedData =subsystemToElevator.getData(elevatorId);
				if(receivedData!=null) {
					for(int i: receivedData.getButtonsPressed()) {
						buttons[i] = true;
					}
					this.printButtons();
				}
		}
				
				
			
			}
			
			
		}
	
	/**
	 * helper method for moving state to know in which direction to transition the floor
	 */

	/**
	 * The run function
	 * Contains State machine for the class
	 */
	@Override
	public void run() {
		
		startingTime = System.nanoTime();
		Runnable task1 = () ->{this.checkForButtonUpdate();};
		new Thread(task1).start();
		Data received = null;
		System.out.println("elevator is waiting");
		stateStartTime = System.nanoTime();
		
		while (true) {
			
			
			
			switch(currentState) {
			//This state simulates the elevator waiting for a request
			// Will transition to closing door state
			case Waiting:
					received = subsystemToElevator.getData(elevatorId);
					if(received != null) {
						System.out.println("Elevator " + elevatorId+ " got something");
						up = received.getUp();
						
						System.out.println("Elevator " + elevatorId+": The current floor is " + currentFloor);
						System.out.println("Elevator " + elevatorId+": Doors are closing");
						
						currentState = ElevatorState.DoorsClosing;
						stateStartTime = System.nanoTime();
					}
					
				
				break;
			// This state simulates the elevator moving from floor to floor
			//From this state it will transition to opening door state
			case Moving:
				
				while(currentState == ElevatorState.Moving) {
					if(!subsystemToElevator.empty() && (subsystemToElevator.peekData().getRequestType() !=2)){
						received = subsystemToElevator.getData(elevatorId);
						if(received != null) {
							currentFloor = received.getFloor();
							if (received.getStop() == true) {
								stateStartTime = System.nanoTime();
								currentState = ElevatorState.DoorOpening;
								buttons[currentFloor] = false;
							}
							System.out.println("Elevator " + elevatorId + ": Current Floor = " + currentFloor);
							System.out.println("Elevator " + elevatorId+ ": Is now opening the doors");
							
						}
					}
					
				}
					
				break;
			// This state simulates the time it takes to open doors
			//Transitions to waiting state
			case DoorOpening:
				if((System.nanoTime()- stateStartTime)/1000000000 >= doorMoveTime) {
					System.out.println("The elevator has opened the doors");
					elevatorToSubsystem.putData(new Data(elevatorId, false));
					currentState = ElevatorState.Waiting;
					System.out.println("Elevator is now waiting");
					stateStartTime = System.nanoTime();
				}
				
				break;
			// This state simulates the time it takes to close the doors
			//transitions to moving state
			case DoorsClosing:
				if((System.nanoTime()- stateStartTime)/1000000000 >= doorMoveTime) {
					System.out.println("The elevator closed the doors.");
					elevatorToSubsystem.putData(new Data(elevatorId, true));
					currentState = ElevatorState.Moving; 
					System.out.println("Elevator"+ elevatorId+ " is starting to move.");
					stateStartTime = System.nanoTime();
				}
					
				break;
			
			
			}
			
			

			}
		}
	


	

	
	
}