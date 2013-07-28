/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Car {

	private double x,y,rotation,xDest,yDest;
	private long enterTime, startWaitTime, leaveTime, elapsedMoveTime, totalMoveTime;
	private int start, dest, id1, id2, id3;
	private boolean stopped, drawStopped, inIntersection;
	
	
	public Car(int start, int dest, double xDest, double yDest, long enterTime, long startWaitTime, int id3) {
		this.start = start;
		this.dest = dest;
		this.enterTime = enterTime;
		this.startWaitTime = startWaitTime;
		this.id1 = this.start;
		this.id2 = this.dest;
		this.id3 = id3;
	}
	
	
	public void move(long elapsedTime) {
		
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getRotation() {
		return this.rotation;
	}
	
	public void moveUpInitial() {
		
	}
	
	public void moveUpFinal() {
		
	}
	
	public void setInIntersection(boolean isInIntersection) {
		this.inIntersection = isInIntersection;
	}
	
	public long getWaitTime() {
		return this.startWaitTime;
	}
	
}
