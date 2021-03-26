package elevator;
public class Channel {
	private Data d;
	/**
	 * create a channel with a null data
	 */
	public Channel() {
		
		d = null;

	}

	/**
	 * 
	 * @return null if data is null other wise it empties the channel and returns the data
	 */
	public Data peekData() {
		return d;
	}
	public synchronized Data getData(int eID, int request) {
		
		if(empty()) {
			notifyAll();
			return null;
		}
		int id = d.getElevatorId();
		int requestType = d.getRequestType();
		if((id == eID || eID == 4) && request == requestType) {
			System.out.println("took"+d.toString()+" out of the channel");
			Data dRet = d;
			d = null;
			notifyAll();
			return dRet;
		}
		notifyAll();
		return null;
	}
	
	public synchronized Data get() {
		
		if(empty()) {
			notifyAll();
			return null;
		}
	
		System.out.println("took"+d.toString()+" out of the channel");
		Data dRet = d;
		d = null;
		notifyAll();
		return dRet;
		
	}
	/**
	 * waits until the channel is empty and puts a data in the channel
	 * @param d
	 */
	public synchronized void putData(Data d) {
		while(!empty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.d = d;
		System.out.println("put "+d.toString()+" into the channel");
		notifyAll();
	}
	
	/**
	 * 
	 * @return returns true if the channel is empty
	 */
	public boolean empty() {
		if (d == null) {
			return true;
		} else {
			return false;
		}
	}
	

}
