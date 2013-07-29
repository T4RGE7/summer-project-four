/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Street implements Runnable {

	private Lane[] lanes;
	private Thread[] threads;
	private Light[] lights;
	private int startId, totalCars, adjusted;
	final double[] percentages = {.25,.5,.25};
	private double overallPercentage;
	private boolean sim;
	
	public Street(int startId, int totalCars, int numLanes, double overallPercentage, boolean sim) {
		if(numLanes > 3 || numLanes < 0) {
			throw new IllegalArgumentException();
		}
		this.lanes = new Lane[numLanes];
		this.threads = new Thread[numLanes];
		this.lights = new Light[numLanes];
		this.startId = startId;
		this.totalCars = totalCars;
		this.overallPercentage = overallPercentage;
		this.sim = sim;
		this.setUp();
	}
	
	private void setUp() {
		this.adjusted = (int) (this.totalCars * this.overallPercentage);
		for(int i = 0; i < this.lanes.length; i++) {
			this.lanes[i] = new Lane(this.startId, i, this.adjusted, this.percentages[i], this.sim);
			this.threads[i] = new Thread(this.lanes[i]);
			System.out.println(this.threads[i]);
			this.lights[i] = this.lanes[i].getLight();
		}
		//this.lights[2] = this.lights[1];
	}

	@Override
	public void run() {
		for(Thread t : this.threads) {
			t.start();
		}
	}
	
	public Lane[] getLanes() {
		return this.lanes;
	}
	
	public Thread[] getThreads() {
		return this.threads;
	}
	
	public Light[] getLights() {
		return this.lights;
	}
	
	public long getWeightedAvg() {
		long toReturn = 0;
		for(Lane lane : this.lanes) {
			toReturn += lane.weightedAvg();
		}
		return toReturn;
	}
	
}
