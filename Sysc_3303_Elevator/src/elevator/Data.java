package elevator;

import java.util.ArrayList;

public class Data {
	int requestType;
	int elevatorId;
    boolean stop,up;
    ArrayList<Integer> buttonsPressed;
    int floor;
    
    public Data(int elevatorId, boolean up) {
    	this.requestType = 0;
    	this.elevatorId = elevatorId;
    	this.up = up;   	
    }
    public Data(int elevatorId, boolean stop, int floor) {
    	this.requestType = 1;
    	this.elevatorId = elevatorId;
    	this.stop = stop;
    	this.floor = floor;
    }
    public Data(int elevatorId, ArrayList <Integer> buttonsPressed) {
    	this.requestType = 3;
    	this.elevatorId = elevatorId;
    	this.buttonsPressed = buttonsPressed;
    	
    }
    
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
