package elevator;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ElevatorSubsystem implements Runnable, ElevatorInterface {
	private Channel subsystemToElevator;
	private Channel elevatorToSubsystem;
	private ElevatorChannel elevatorServer;

	public ElevatorSubsystem(Channel subsystemToElevator, Channel elevatorToSubsystem) {
		super();
		this.subsystemToElevator = subsystemToElevator;
		this.elevatorToSubsystem = elevatorToSubsystem;
		Registry schedulerRegistry;
		boolean done = false;
		while (!done) {
			try {
				System.out.println("trying to connect");
				schedulerRegistry = LocateRegistry.getRegistry(5453);
				this.elevatorServer = (ElevatorChannel) schedulerRegistry.lookup("ElevatorChannel");
				done = true;
			} catch (RemoteException e) {
				// e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Thread elev0, elev1, elev2;
		Channel subsystemToElevator = new Channel();
		Channel elevatorToSubsystem = new Channel();

		Elevator elevator0 = new Elevator(subsystemToElevator,elevatorToSubsystem,0,6);
		Elevator elevator1 = new Elevator(subsystemToElevator,elevatorToSubsystem,1,6);
		Elevator elevator2 = new Elevator(subsystemToElevator,elevatorToSubsystem,2,6);

	

		Registry registry;
		ElevatorInterface elevatorSubsystem;
		ElevatorInterface stub;

	
		

		boolean done = false;
		while (!done) {
			try {
				elevatorSubsystem = new ElevatorSubsystem(subsystemToElevator,elevatorToSubsystem);
				stub = (ElevatorInterface) UnicastRemoteObject.exportObject((ElevatorInterface)elevatorSubsystem, 21);
				registry = LocateRegistry.createRegistry(21);
				registry.bind("ElevatorInterface",stub);
				System.err.println("Server ready");
				Thread subsys = new Thread((ElevatorSubsystem)elevatorSubsystem, "elevatorSubsystem");
				subsys.start();
				done = true;
			}catch(RemoteException e) {
				e.printStackTrace();
			} catch (AlreadyBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		elev0 = new Thread(elevator0, "Elevator 0");
		elev1 = new Thread(elevator1, "Elevator 1");
		elev2 = new Thread(elevator2, "Elevator 2");
		elev0.start();
		elev1.start();
		elev2.start();

	}

	/**
	 * method for updating scheduler on anything done by the elevators.
	 */



	public void sendUpdate() {
		Data toSend = null;
		while (true) {
			toSend = elevatorToSubsystem.getData(4);
			
			if (toSend != null) {
				
				try {
					System.out.println("Elevator Subsystem sending back data " + toSend.getUp() +" " + toSend.getElevatorId());
					elevatorServer.startedMoving(toSend.getUp(), toSend.getElevatorId());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Update letting elevator know to start moving
	 * @param up determines in which direction to spin motors
	 * @param elevator id that identifies the elevator
	 * @throws RemoteException
	 */



	@Override
	public void startElevator(boolean up, int elevator) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("got something");
		Data data = new Data(elevator, up);
		subsystemToElevator.putData(data);
	}

	/**
	 * 
	 * @param stop determines if elevator should stop
	 * @param floor lets the elevator know what floor it's on
	 * @param elevator Id that identifies which elevator
	 * @throws RemoteException
	 */



	@Override
	public void arriveElevator(boolean stop, int floor, int elevator) throws RemoteException {
		// TODO Auto-generated method stub
		Data data = new Data(elevator, stop, floor);
		System.out.println("got something");
		subsystemToElevator.putData(data);

	}

	/**
	 * Update letting elevator know if it reached a new floor or requires to stop.
	 * @param buttonList arraylist containing which buttons are lit up
	 * @param elevator Id that identifies which elevator
	 * @throws RemoteException
	 */



	@Override
	public void buttonPushed(ArrayList<Integer> buttonList, int elevator) throws RemoteException {
		// TODO Auto-generated method stub
		Data data = new Data(elevator, buttonList);
		System.out.println("got something");
		subsystemToElevator.putData(data);

	}

	@Override
	public void run() {
		this.sendUpdate();

	}

}
