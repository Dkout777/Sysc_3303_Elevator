package testing;

import org.junit.Test;

import elevator.Channel;
import elevator.Data;
import elevator.ElevatorData;
import elevator.Scheduler;
import elevator.SchedulerData;

public class TestScheduler {
	
	@Test
	public void testaddFloor() {
		Channel elevatorChannel = new Channel();
		Channel floorChannel = new Channel();
		Channel recieveChannel = new Channel();
		Scheduler s = new Scheduler(elevatorChannel,floorChannel,recieveChannel);
		Thread sThread = new Thread(s,"scheduler thread");
		sThread.start();
		ElevatorData d = new ElevatorData(3,false);
		elevatorChannel.putData(d);
		boolean done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==3);
			}
			
		}
	}
	@Test
	public void testaddFloor2() {
		Channel elevatorChannel = new Channel();
		Channel floorChannel = new Channel();
		Channel recieveChannel = new Channel();
		Scheduler s = new Scheduler(elevatorChannel,floorChannel,recieveChannel);
		Thread sThread = new Thread(s,"scheduler thread");
		sThread.start();
		ElevatorData d = new ElevatorData(3,false);
		elevatorChannel.putData(d);
		boolean done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==3);
			}
			
		}
		 d = new ElevatorData(4,false);
		elevatorChannel.putData(d);
		done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==3);
			}
			
		}
	}
	@Test
	public void testaddFloor3() {
		Channel elevatorChannel = new Channel();
		Channel floorChannel = new Channel();
		Channel recieveChannel = new Channel();
		Scheduler s = new Scheduler(elevatorChannel,floorChannel,recieveChannel);
		Thread sThread = new Thread(s,"scheduler thread");
		sThread.start();
		ElevatorData d = new ElevatorData(3,false);
		elevatorChannel.putData(d);
		boolean done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==3);
			}
			
		}
		 d = new ElevatorData(2,false);
		elevatorChannel.putData(d);
		done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==2);
			}
			
		}
	}
	@Test
	public void testbuttonFloor() {
		Channel elevatorChannel = new Channel();
		Channel floorChannel = new Channel();
		Channel recieveChannel = new Channel();
		Scheduler s = new Scheduler(elevatorChannel,floorChannel,recieveChannel);
		Thread sThread = new Thread(s,"scheduler thread");
		sThread.start();
		Data d = new Data(3,false);
		floorChannel.putData(d);
		boolean done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==3);
			}
			
		}
	}
	@Test
	public void testbuttonFloor2() {
		Channel elevatorChannel = new Channel();
		Channel floorChannel = new Channel();
		Channel recieveChannel = new Channel();
		Scheduler s = new Scheduler(elevatorChannel,floorChannel,recieveChannel);
		Thread sThread = new Thread(s,"scheduler thread");
		sThread.start();
		Data d = new Data(3,true);
		floorChannel.putData(d);
		boolean done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==3);
			}
			
		}
		d = new Data(2,false);
		floorChannel.putData(d);
		done = false;
		while(!done) {
			SchedulerData sData = (SchedulerData) recieveChannel.getData();
			if(sData == null) {
				done = false;
			}else {
				done = true;
				assert(sData.getFloors()==3);
			}
			
		}
	}

}
