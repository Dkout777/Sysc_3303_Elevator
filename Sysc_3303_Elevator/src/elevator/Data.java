package elevator;

import java.util.ArrayList;



public class Data {
	private int requestType;// 0 is the start moving request, 1 is the floor update and 3 is the button update
	private int elevatorId;
    private boolean stop,up;
    private ArrayList<Integer> buttonsPressed;
    private int floor;
    private boolean moving;
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
    	this.requestType = 2;
    	this.elevatorId = elevatorId;
    	this.buttonsPressed = buttonsPressed;
    	
    }
    
    public Data(int elevatorId) {
    	this.requestType = 3;
    	this.elevatorId = elevatorId;
    	
    }
    
    public String toString() {
    	if(requestType == 0) {
    		String x ="Request: "+  requestType +", Elevator: " + elevatorId + ", Up?: " + up; 
        	return x;
    	}
    	else if(requestType == 1) {
    		String x ="Request: "+  requestType +", Elevator: " + elevatorId + ", Stop?: " + stop + ", floor: " + floor; 
        	return x;
    	}
    	else if(requestType == 2) {
    		String x ="Request: "+  requestType +", Elevator: " + elevatorId + ",  firstButton: " + buttonsPressed.get(0);
    		for(int button: buttonsPressed) {
    			System.out.println(button);
    		}
    		return x;
    			
    	}
    	else if(requestType == 3) {
    		String x ="Request: "+  requestType +", Elevator: " + elevatorId;
    		return x;
    			
    	}
    	else {
    		String x = "Error";
    		return x;
    	}
    	
    	
    	
    	
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
