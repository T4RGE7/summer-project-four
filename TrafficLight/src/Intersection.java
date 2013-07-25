/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Intersection implements Runnable {

	private Street[] streets;
	private Lane[][] lanes;
	private Light[][] lights;
	private Thread[][] threads;
	private LinkedList<Car> inIntersection;
	private boolean graphical;
	private double[] trafficPercentage;
	private int numCars;
	
	public Intersection(boolean graphical, double[] percentage, int numCars) {
		this.graphical = graphical;
		this.streets = new Street[4];
		this.lights = new Light[4][3];
		this.lanes = new Lane[4][3];
		this.threads = new Thread[4][3];
		this.trafficPercentage = percentage;
		this.numCars = numCars;
		this.setUp();
	}
	
	public void setUp() {
		for(int i = 0; i < 4; i++) {
			this.streets[i] = new Street(i, (int)(this.trafficPercentage[i] * this.numCars), this.graphical);
			this.lanes[i] = this.streets[i].getLanes();
			this.lights[i] = this.streets[i].getLights();
			this.threads[i] = this.streets[i].getThreads();
		}
	}
	
	public void run() {
		
	}
	
	
}
