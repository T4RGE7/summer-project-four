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
//		System.out.println("Lane " + this.startId + "-" + this.endId + " created with " + this.adjustedNumber + " Cars to be made.");
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
	
	public Car peekFirst() {
		try {
			return this.queue.peekFirst();
		} catch (EmptyListException e) {
		}
		return null;
	}
	
	public long weightedAvg() {
		try {
			return (long) (this.queue.size() * Math.pow((System.currentTimeMillis() - this.queue.peekFirst().getCreationTime()), 2));
		} catch (EmptyListException e) {
			return (long) 0;
		}

	}
	
	public int size() {
		return this.queue.size();
	}
	
	@Override
	public void run() {
//		System.out.println("Lane " + this.startId + "-" + this.endId + " Started");
		for(int i = 0; i < this.adjustedNumber; i++) {
			long toSleep1 = (long) (1000/Math.pow(this.percentage, 1));
			toSleep1 /= (this.sim ? 10 : 1);
			//long toSleep0 = (long) (1000/Math.pow(this.percentage, 1.75));
			long toSleep0 = (long) (1000);
			toSleep0 /= (this.sim ? 10 : 1);
			try {
				Thread.sleep(rand.nextInt((int) toSleep0) + toSleep1); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Car toAdd = new Car(this.startId, this.endId, i);
			this.queue.add(toAdd);
			if(!sim) {
		//		System.out.println(toAdd);
			}
		}
	}
	
}
