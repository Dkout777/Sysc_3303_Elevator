package elevator;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
public class ElevatorSubsystem implements Runnable, ElevatorInterface{ 
	Channel subsystemToElevator;
	Channel elevatorToSubsystem;
	ElevatorChannel elevatorServer;
	public ElevatorSubsystem(Channel subsystemToElevator, Channel elevatorToSubsystem ) {
		super();
		this.subsystemToElevator = subsystemToElevator;
		this.elevatorToSubsystem = elevatorToSubsystem;
		Registry schedulerRegistry;
		try {
			schedulerRegistry = LocateRegistry.getRegistry(5453);
			this.elevatorServer = (ElevatorChannel) schedulerRegistry.lookup("ElevatorChannel");
			
		}catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
	public static void main(String[] args) {
		Thread elev0,elev1,elev2;
		Channel subsystemToElevator = new Channel();
		Channel elevatorToSubsystem = new Channel();
		Elevator elevator0 = new Elevator(subsystemToElevator,elevatorToSubsystem,0);
		Elevator elevator1 = new Elevator(subsystemToElevator,elevatorToSubsystem,1);
		Elevator elevator2 = new Elevator(subsystemToElevator,elevatorToSubsystem,2);
		Registry registry;
		ElevatorSubsystem elevatorSubsystem;
		ElevatorInterface stub;
		try {
			elevatorSubsystem = new ElevatorSubsystem(subsystemToElevator,elevatorToSubsystem);
			stub = (ElevatorInterface) UnicastRemoteObject.exportObject((ElevatorInterface)elevatorSubsystem, 21);
			registry = LocateRegistry.createRegistry(21);
			registry.bind("ElevatorInterface",stub);
			System.err.println("Server ready");
			Thread subsys = new Thread(elevatorSubsystem, "elevatorSubsystem");
			subsys.start();
		}catch(RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elev0 = new Thread(elevator0,"Elevator 0");
		elev1 = new Thread(elevator1,"Elevator 1");
		elev2 = new Thread(elevator2,"Elevator 2");
		elev0.start();
		elev1.start();
		elev2.start();
		
		

	}
	public void sendUpdate() {
		Data toSend = null;
		while(true) {
			toSend = elevatorToSubsystem.getData();
			if(toSend != null) {
				try {
					elevatorServer.startedMoving(toSend.getUp(), toSend.getElevatorId());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	@Override
	public void startElevator(boolean up, int elevator) throws RemoteException {
		// TODO Auto-generated method stub
		Data data = new Data(elevator,up);
		subsystemToElevator.putData(data);
	}
	@Override
	public void arriveElevator(boolean stop, int floor, int elevator) throws RemoteException {
		// TODO Auto-generated method stub
		Data data = new Data(elevator,stop, floor);
		subsystemToElevator.putData(data);
		
	}
	@Override
	public void buttonPushed(ArrayList<Integer> buttonList, int elevator) throws RemoteException {
		// TODO Auto-generated method stub
		Data data = new Data(elevator,buttonList);
		subsystemToElevator.putData(data);
		
	}
	@Override
	public void run() {
		this.sendUpdate();
		
	}

}
