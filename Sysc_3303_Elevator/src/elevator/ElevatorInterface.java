  
package elevator;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ElevatorInterface extends Remote{
	// elevators are zero indexed
		public void startElevator(boolean up, int elevator) throws RemoteException ;
		public void arriveElevator(boolean stop, int floor,int elevator) throws RemoteException;
		public void buttonPushed(ArrayList<Integer> buttonList,int elevator) throws RemoteException;
}