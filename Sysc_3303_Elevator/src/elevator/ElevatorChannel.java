package elevator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ElevatorChannel extends Remote{
		// remote interface on Schedulers side to talk to elevator
		/**
		 * Lets scheduler know that elevator started moving
		 * @param moving true if elevator is moving
		 * @param elevator id of which elevator
		 * @throws RemoteException
		 */
		public void startedMoving(boolean moving,int elevator) throws RemoteException;
		/**
		 * Lets the scheduler know that a door failure occurred
		 * @param elevator id of which elevator
		 * @throws RemoteException
		 */
		public void doorFailure(int elevator)throws RemoteException;
		/**
		 * Lets the scheduler know that an arrival sensor error occurred
		 * @param elevator id of which elevator
		 * @throws RemoteException
		 */
		public void sensorFailure(int elevator)throws RemoteException;
		/**
		 * Lets the scheduler know that the elevator stopped
		 * @param elevator id of which elevator
		 * @throws RemoteException
		 */
		public void elevatorStoped(int elevator)throws RemoteException;
}