  
package elevator;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ElevatorInterface extends Remote{
	// elevators are zero indexed
	
		/**
		 * Update letting elevator know to start moving
		 * @param up determines in which direction to spin motors
		 * @param elevator id that identifies the elevator
		 * @throws RemoteException
		 */
		public void startElevator(boolean up, int elevator) throws RemoteException ;
		/**
		 * 
		 * @param stop determines if elevator should stop
		 * @param floor lets the elevator know what floor it's on
		 * @param elevator Id that identifies which elevator
		 * @throws RemoteException
		 */
		public void arriveElevator(boolean stop, int floor,int elevator) throws RemoteException;
		/**
		 * Update letting elevator know if it reached a new floor or requires to stop.
		 * @param buttonList arraylist containing which buttons are lit up
		 * @param elevator Id that identifies which elevator
		 * @throws RemoteException
		 */
		public void buttonPushed(ArrayList<Integer> buttonList,int elevator) throws RemoteException;
}