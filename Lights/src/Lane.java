/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.util.Random;


public class Lane implements Runnable{

	private LinkedQueue<Car> queue;
	private Light light;
	private int startId, endId, initialNumber, adjustedNumber;
	private double percentage;
	private boolean sim;
	private Random rand;
	
	public Lane(int startId, int endId, int initialNumber, double percentage, boolean sim) {
		this.queue = new LinkedQueue<Car>();
		this.light = new Light();
		this.startId = startId;
		this.endId = endId;
		this.initialNumber = initialNumber;
		this.percentage = percentage;
		this.sim = sim;
		this.rand = new Random();
		this.setUp();
	}
	
	private void setUp() {
		this.adjustedNumber = (int)(this.initialNumber * this.percentage);
		System.out.println("Lane " + this.startId + "-" + this.endId + " created with " + this.adjustedNumber + " Cars to be made.");
	}
	
	public Car cross() {
		try {
			return this.queue.poll();
		} catch (EmptyListException e) {
			return null;
		}
	}
	
	public Light getLight() {
		return this.light;
	}
	
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
	
	@Override
	public void run() {
		System.out.println("Lane " + this.startId + "-" + this.endId + " Started");
		for(int i = 0; i < this.adjustedNumber; i++) {
			long toSleep1 = (long) (1000/this.percentage);
			toSleep1 /= (this.sim ? 10 : 1);
			long toSleep0 = (1000);
			toSleep0 /= (this.sim ? 10 : 1);
			try {
				Thread.sleep(rand.nextInt((int) toSleep0) + toSleep1); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Car toAdd = new Car(this.startId, this.endId, i);
			this.queue.add(toAdd);
			System.out.println(toAdd);
		}
	}
	
}
