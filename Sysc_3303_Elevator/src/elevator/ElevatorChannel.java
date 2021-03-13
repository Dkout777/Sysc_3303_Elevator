package elevator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ElevatorChannel extends Remote{
		
		public void startedMoving(boolean moving,int elevator) throws RemoteException;
	
}