package elevator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ElevatorChannel extends Remote{
		// remote interface on Schedulers side to talk to elevator
		public void startedMoving(boolean moving,int elevator) throws RemoteException;
		public void doorFailure(int elevator)throws RemoteException;
		public void sensorFailure(int elevator)throws RemoteException;
		public void elevatorStoped(int elevator)throws RemoteException;
}