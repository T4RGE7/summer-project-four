/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Car {

	private long creationTime, waitingTime;
	private int startId, endId, numId;
	
	public Car(int startId, int endId, int numId) {
		this.creationTime = System.currentTimeMillis();
		this.startId = startId;
		this.endId = endId;
		this.numId = numId;
	}

	/**
	 * @return the creationTime
	 */
	public long getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the startId
	 */
	public int getStartId() {
		return startId;
	}

	/**
	 * @param startId the startId to set
	 */
	public void setStartId(int startId) {
		this.startId = startId;
	}

	/**
	 * @return the endId
	 */
	public int getEndId() {
		return endId;
	}

	/**
	 * @param endId the endId to set
	 */
	public void setEndId(int endId) {
		this.endId = endId;
	}

	/**
	 * @return the numId
	 */
	public int getNumId() {
		return numId;
	}

	/**
	 * @param numId the numId to set
	 */
	public void setNumId(int numId) {
		this.numId = numId;
	}
	
	public long getWaitingTime() {
		this.waitingTime = System.currentTimeMillis() - this.creationTime;
		return this.waitingTime;
	}
	
	public String toString() {
		return this.numId + ") Start: " + this.startId + ", Dest: " + this.endId + ", Created: " + this.creationTime;
	}
}
