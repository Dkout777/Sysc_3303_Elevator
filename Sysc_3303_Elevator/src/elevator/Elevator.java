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

/**
 * 
 * @author Dimitry Koutchine
 *
 */
public class Elevator implements Runnable{
	
	public enum ElevatorState{//Possible state for elevator
		Waiting,DoorsClosing,DoorOpening,Moving,OutOfOrder
	}
	private volatile boolean doorJam = false;
	private final int arrivalSensorTimeout = 10;
	private int elevatorId;
	private volatile Channel subsystemToElevator;
	private Channel elevatorToSubsystem;
	private int currentFloor = 1;
	private final int doorOpenTime = 8; // time it takes to open+ offloading 
	private final int doorCloseTime = 3;
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
		startingTime = System.nanoTime();
		this.elevatorId = elevatorId;
		this.subsystemToElevator = subsystemToElevator;
		this.elevatorToSubsystem = elevatorToSubsystem;
		buttons = new boolean [buttonAmount];
		currentState = ElevatorState.Waiting;
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = false;
		}
		
	}
	
	
	/**
	 * getter for currentFloor
	 * @return in representing currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	/**
	 * getter for currentState
	 * @return in representing currentFloor
	 */
	public ElevatorState getCurrentState() {
		return currentState;
	}
	/**
	 * method for printing out all active buttons.
	 */
	public void printButtons() {
		System.out.println(getCurrentTime()+">"+" Elevator "+elevatorId +" has the current buttons are lit up:");
		
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i] == true) {
				System.out.print(i+ " ");
			}
		
			
			
		}
		System.out.println();
	}
	

	
	
	/**
	 * A method run as it's own thread for checking for stop requests or button/ floor changes.
	 */
	public  void checkForUpdate() {
		Data receivedData = null;
		System.out.println("Task is running");
		while(true) {			
			if(!subsystemToElevator.empty()){
				//System.out.println("Entered button if statement");
				receivedData =subsystemToElevator.getData(elevatorId,2);
				if(receivedData!=null) {
					for(int i: receivedData.getButtonsPressed()) {
						buttons[i] = true;
					}
					this.printButtons();
				}
			}
			else {
				receivedData =subsystemToElevator.getData(elevatorId,4);
				if(receivedData != null) {
					doorJam = true;
				}
			}
		}
				
				
			
		}
	public float getCurrentTime() {
		return (System.nanoTime() - startingTime) / 1000000000;
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
		
		Runnable task1 = () ->{this.checkForUpdate();};
		new Thread(task1).start();
		Data received = null;
		System.out.println(getCurrentTime()+">"+" elevator " + elevatorId +" is waiting");
		stateStartTime = System.nanoTime();
		
		while (true) {
			
			
			
		switch(currentState) {
			//This state simulates the elevator waiting for a request
			// Will transition to closing door state
			case Waiting:
				if(!subsystemToElevator.empty()) {
					received = subsystemToElevator.getData(elevatorId,0);
					if(received != null) {
						System.out.println(getCurrentTime()+">"+" Elevator " + elevatorId+ " got something");
						up = received.getUp();
							
						System.out.println(getCurrentTime()+">"+" Elevator " + elevatorId+": The current floor is " + currentFloor);
						System.out.println(getCurrentTime()+">"+" Elevator " + elevatorId+": Doors are closing");
							
						currentState = ElevatorState.DoorsClosing;
						stateStartTime = System.nanoTime();
					}
				}
				
					
				
				break;
			// This state simulates the elevator moving from floor to floor
			//From this state it will transition to opening door state
			case Moving:
				float errorTimer = System.nanoTime();
				while(currentState == ElevatorState.Moving) {
					
					received = subsystemToElevator.getData(elevatorId,1);
					if(received != null) {
						currentFloor = received.getFloor();
						if (received.getStop() == true) {
							stateStartTime = System.nanoTime();
							currentState = ElevatorState.DoorOpening;
							buttons[currentFloor] = false;
							System.out.println(getCurrentTime()+">"+" Elevator " + elevatorId+ ": Is now opening the doors");
							errorTimer = System.nanoTime();
						}
						System.out.println(getCurrentTime()+">"+" Elevator " + elevatorId + ": Current Floor = " + currentFloor);
						
							
					}
					if((System.nanoTime()- errorTimer)/1000000000 >= arrivalSensorTimeout){
						Data errorMessage = new Data(elevatorId,5);
						currentState = ElevatorState.OutOfOrder;
						elevatorToSubsystem.putData(errorMessage);
					}
					
					
				}
				
					
				break;
			// This state simulates the time it takes to open doors
			//Transitions to waiting state
			case DoorOpening:
				if((System.nanoTime()- stateStartTime)/1000000000 >= doorOpenTime) {
					System.out.println(getCurrentTime()+">"+" Elevator " + elevatorId+ " has opened the doors");
					elevatorToSubsystem.putData(new Data(elevatorId));
					currentState = ElevatorState.Waiting;
					System.out.println(getCurrentTime()+">"+" Elevator " + elevatorId +" Elevator is now waiting");
					stateStartTime = System.nanoTime();
				}
				else if(doorJam == true) {
					System.out.println(getCurrentTime()+">"+" Elevator " +elevatorId+ " door is jammed");
					elevatorToSubsystem.putData(new Data(elevatorId, 4));  
					stateStartTime = System.nanoTime();
					doorJam = false;
					
				}
				
				break;
			// This state simulates the time it takes to close the doors
			//transitions to moving state
			case DoorsClosing:
				if((System.nanoTime()- stateStartTime)/1000000000 >= doorCloseTime) {
					System.out.println(getCurrentTime()+">"+" Elevator " +elevatorId+" closed the doors.");
					elevatorToSubsystem.putData(new Data(elevatorId, up));
					currentState = ElevatorState.Moving; 
					if(up) {
						System.out.println(getCurrentTime()+">"+" Elevator "+ elevatorId+ "'s motor started moving it up.");  
					}
					else {
						System.out.println(getCurrentTime()+">"+" Elevator "+ elevatorId+ "'s motor started moving it down.");  
						
					}
					stateStartTime = System.nanoTime();
				}
				else if(doorJam == true) {
					System.out.println(getCurrentTime()+">"+" Elevator " +elevatorId+ " door is jammed");
					elevatorToSubsystem.putData(new Data(elevatorId, 4));                      
					stateStartTime = System.nanoTime();
					doorJam = false;
				}
					
				break;
				// This is the state entered after any error occurs
			case OutOfOrder:
					System.out.println(getCurrentTime()+">"+" Elevator " +elevatorId + ": Is out of order");
					while(true) {             
						
					}
				  
		default:
			break;
			
			                                                                   
			}
			
			

			}
		}
	


	

	
	
}