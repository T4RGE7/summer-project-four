/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Car {

	private long creationTime;
	private int startId, endId, numId;
	
	public Car(int startId, int endId, int numId) {
		this.creationTime = System.currentTimeMillis();
		this.startId = startId;
		this.endId = endId;
		this.numId = numId;
		
	}
}
