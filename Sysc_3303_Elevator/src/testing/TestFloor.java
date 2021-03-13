package testing;

import org.junit.Test;
import static org.junit.Assert.*;

import elevator.Floor;
import elevator.FloorData;
import elevator.Data;
import elevator.Channel;

public class TestFloor {


	@Test
	public void testInputStringToData() {
		
		Floor floor = new Floor(null);
		String testInput = "5 6 down";
		FloorData testData  = new FloorData(5,6,false);
		
		assert(testData.systemInputTime()== floor.inputStringToData(testInput).systemInputTime());
		assert(testData.floor()== floor.inputStringToData(testInput).floor());
		assert(testData.isUp()== floor.inputStringToData(testInput).isUp());
		
	}
	
	@Test
	public void testRun()  {
		Channel floorChannel = new Channel();
		Floor floor = new Floor(floorChannel);
		Data testData = new Data(1,2,3,4,5,true,6);
		Thread FloorThread = new Thread(floor,"floor Thread");
		FloorThread.start();
		floorChannel.putData(testData);	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert(testData == floorChannel.getData());
		
	}
	
	@Test
	public void testTimeCheck()  {
		
		Channel floorChannel = new Channel();
		Floor floor = new Floor(floorChannel);
		floor.startingTime = 0;
		Long l = (System.nanoTime() / 1000000000)+5;
		int time = l.intValue();
		FloorData testData  = new FloorData(time,6,false);
		assert(floor.timeCheck(testData));
	}

}
