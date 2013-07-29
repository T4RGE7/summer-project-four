/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Car {

	private long creationTime, waitingTime;
	private int startId, endId, numId;
	private double rotation, x, y, anchorY, anchorX, endRot;
	private boolean atEnd = false;
	private int spot;
	private double totalTime = 2;
	
	public void move(float elapsedTime) {
		totalTime -= elapsedTime;
		if(endRot != rotation) {
			switch(this.startId) {
			case 0: if(this.endId == 0) {
				x = this.anchorX - (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY - (endRot - rotation)*(this.totalTime/2);
			} else {
				x = this.anchorX + (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY - (endRot - rotation)*(this.totalTime/2);
			}
			break;
			case 1: if(this.endId == 0) {
				x = this.anchorX + (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY - (endRot - rotation)*(this.totalTime/2);
			} else {
				x = this.anchorX + (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY + (endRot - rotation)*(this.totalTime/2);
			}			
				break;
			case 2: if(this.endId == 0) {
				x = this.anchorX + (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY + (endRot - rotation)*(this.totalTime/2);
			} else {
				x = this.anchorX - (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY + (endRot - rotation)*(this.totalTime/2);
			}
				break;
			case 3: if(this.endId == 0) {
				x = this.anchorX - (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY + (endRot - rotation)*(this.totalTime/2);
			} else {
				x = this.anchorX - (endRot - rotation)*(this.totalTime/2);
				y = this.anchorY - (endRot - rotation)*(this.totalTime/2);
			}
			}
		} else {
			switch(this.endId) {
			case 0: this.y = 30 + 180*(this.totalTime/2);
				break;
			case 1: this.x = 300 - 180*(this.totalTime/2);
				break;
			case 2: this.y = 300 - 180*(this.totalTime/2);
				break;
			case 3: this.x = 30 + 180*(this.totalTime/2);
			}
		}
		
		if(totalTime < 0) {
			this.atEnd = true;
		}
	}
	
	public boolean isAtEnd() {
		return this.atEnd;
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
				this.endRot = 270;
			} else if(this.endId == 2) {
				this.anchorX = 270;
				this.anchorY = 270;
				this.endRot = 90;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
				this.endRot = 0;
			}
			break;
		case 1: this.rotation = 90;
			this.x = 30;
			this.y = 195 + 30*(this.endId);
			if(this.endId == 0) {
				this.anchorX = 60;
				this.anchorY = 60;
				this.endRot = 0;
			} else if(this.endId == 2) {
				this.anchorX = 60;
				this.anchorY = 270;
				this.endRot = 180;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
				this.endRot = 90;
			}
			break;
		case 2: this.rotation = 180;
			this.x = 195 + 30*(this.endId);
			this.y = 30;
			if(this.endId == 0) {
				this.anchorX = 270;
				this.anchorY = 60;
				this.endRot = 90;
			} else if(this.endId == 2) {
				this.anchorX = 60;
				this.anchorY = 60;
				this.endRot = 270;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
				this.endRot = 180;
			}
			break;
		case 3: this.rotation = 270;
			this.x = 300;
			this.y = 135 - 30*(this.endId);
			if(this.endId == 0) {
				this.anchorX = 270;
				this.anchorY = 270;
				this.endRot = 180;
			} else if(this.endId == 2) {
				this.anchorX = 270;
				this.anchorY = 60;
				this.endRot = 0;
			} else {
				this.anchorX = -1;
				this.anchorY = -1;
				this.endRot = 270;
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
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public double getRotation() {
		return this.endRot - this.rotation;
	}
}
