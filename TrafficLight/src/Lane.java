import java.util.Random;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Lane implements Runnable {

	private LinkedQueue<Car> queue;
	private int start, dest, totalNumber, adjusted;
	private double percentage;
	private boolean graphical;
	private Random rand;
	
	public Lane(int start, int dest, int totalNumber, double percentage, boolean graphical) {
		this.queue = new LinkedQueue<Car>();
		this.start = start;
		this.dest = dest;
		this.totalNumber = totalNumber;
		this.percentage = percentage;
		this.graphical = graphical;
		this.adjusted = (int)(this.percentage * this.totalNumber);
		this.rand = new Random();
	}
	
	public void run() {
		long last = 0;
		for(int i = 0; i < this.adjusted; i++) {
			Car toAdd = null;
			if(!this.graphical) {
				last += this.rand.nextInt(5) + (1/this.percentage);
				long wait = 0;
				if(this.queue.size() < 3) {
					wait = this.queue.size();
				} else {
					wait = 0;
				}
				toAdd = new Car(this.start, this.dest, -1, -1, last, last + wait, i);
			} else {
				//graphical
			}
			this.queue.add(toAdd);
		}
	}
	
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
	
	public Car cross() {
		try {
			return this.queue.poll();
		} catch (EmptyListException e) {
			return null;
		}
	}
	
	public long getWeightedAvg() {
		long toReturn = 0;
		try {
			toReturn += this.queue.size() * Math.pow(System.currentTimeMillis() - this.queue.peekFirst().getWaitTime(), 2);
		} catch (EmptyListException e) {
			return 0;
		}
		return toReturn;
	}
	
}
