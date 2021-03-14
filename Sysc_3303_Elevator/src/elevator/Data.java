package elevator;

import java.util.ArrayList;

public class Data {
	int requestType;// 0 is the start moving request, 1 is the floor update and 3 is the button update
	int elevatorId;
    boolean stop,up;
    ArrayList<Integer> buttonsPressed;
    int floor;
    /**
     * Constructor for starting move requests
     * @param elevatorId elevator the request is sent to.
     * @param up boolean for up or down
     */
    public Data(int elevatorId, boolean up) {
    	this.requestType = 0;
    	this.elevatorId = elevatorId;
    	this.up = up;   	
    }
    /**
     * Constructor for floor update packets
     * @param elevatorId elevator the request is sent to.
     * @param stop boolean letting the elevators now if they reached their stop
     * @param floor which floor to update the counter with
     */
    public Data(int elevatorId, boolean stop, int floor) {
    	this.requestType = 1;
    	this.elevatorId = elevatorId;
    	this.stop = stop;
    	this.floor = floor;
    }
    /**
     * 
     * @param elevatorId which elevator to send to.
     * @param buttonsPressed array list containing all the buttons pressed.
     */
    public Data(int elevatorId, ArrayList <Integer> buttonsPressed) {
    	this.requestType = 3;
    	this.elevatorId = elevatorId;
    	this.buttonsPressed = buttonsPressed;
    	
    }
    /**
     * 
     * Below are getters for every data type
     */
    public int getRequestType() {
    	return requestType;
    }
    
    public int getElevatorId() {
    	return elevatorId;
    }
    public boolean getStop() {
    	return stop;
    }
    public boolean getUp() {
    	return up;
    }
    public ArrayList<Integer> getButtonsPressed() {
    	return buttonsPressed;
    }
    public int getFloor() {
    	return floor;
    }
    
	

}
