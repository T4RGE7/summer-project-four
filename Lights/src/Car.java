/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Car {

	private long creationTime, waitingTime;
	private int startId, endId, numId;
	private double rotation, x, y, anchorY, anchorX;
	private boolean front = false;
	private int spot;
	
	public void move(float elapsedTime) {
		
	}
	
	public Car(int startId, int endId, int numId) {
		this.creationTime = System.currentTimeMillis();
		this.startId = startId;
		this.endId = endId;
		this.numId = numId;
		this.setUp();
	}
	
	private void setUp() {
		switch(this.startId) {
		case 0: this.rotation = 0;
			this.x = 195 + 30*(this.endId);
			this.y = 300;
			if(this.endId == 0) {
				this.anchorX = 60;
				this.anchorY = 270;
			} else if(this.endId == 2) {
				this.anchorX = 270;
				this.anchorY = 270;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
			}
			break;
		case 1: this.rotation = 90;
			this.x = 30;
			this.y = 195 + 30*(this.endId);
			if(this.endId == 0) {
				this.anchorX = 60;
				this.anchorY = 60;
			} else if(this.endId == 2) {
				this.anchorX = 60;
				this.anchorY = 270;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
			}
			break;
		case 2: this.rotation = 180;
			this.x = 195 + 30*(this.endId);
			this.y = 30;
			if(this.endId == 0) {
				this.anchorX = 270;
				this.anchorY = 60;
			} else if(this.endId == 2) {
				this.anchorX = 60;
				this.anchorY = 60;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
			}
			break;
		case 3: this.rotation = 270;
			this.x = 300;
			this.y = 135 - 30*(this.endId);
			if(this.endId == 0) {
				this.anchorX = 270;
				this.anchorY = 270;
			} else if(this.endId == 2) {
				this.anchorX = 270;
				this.anchorY = 60;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
			}
			break;
		}
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
