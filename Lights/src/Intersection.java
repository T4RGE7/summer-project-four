
public class Intersection implements Runnable {
	
	private Thread[][] threads;
	private Lane[][] lanes;
	private Street[] streets;
	private Light[][] lights;
	private int totalNumCars;
	private double[] percentages;
	private boolean sim;
	final String[] directions = {"South", "West", "North", "East"}, turns = {"Left", "Straight", "Right"};
	
	public Intersection(int totalNumCars, double[] percentages, boolean sim) {
		this.threads = new Thread[4][3];
		this.lanes = new Lane[4][3];
		this.streets = new Street[4];
		this.lights = new Light[4][3];
		this.totalNumCars = totalNumCars;
		this.percentages = percentages;
		this.sim = sim;
		this.setUp();
	}
	
	private void setUp() {
		for(int i = 0; i < this.streets.length; i++) {
			this.streets[i] = new Street(i, this.totalNumCars, this.lanes[0].length, this.percentages[i], this.sim);
			this.lanes[i] = this.streets[i].getLanes();
			this.lights[i] = this.streets[i].getLights();
			this.threads[i] = this.streets[i].getThreads();
		}
	}
	
	@Override
	public void run() {
//		for(Thread[] group : this.threads) {
//			for(Thread t : group) {
//					t.start();
//			}
//		}
		for(Street street : this.streets) {
			new Thread(street).run();
		}
		
		
		while(this.checkAlive() || this.checkNotEmpty()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Running");
		}
	}
	
	public boolean checkAlive() {
		for(Thread[] group : this.threads) {
			for(Thread t : group) {
				if(t.isAlive()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkNotEmpty() {
		for(Lane[] street : this.lanes) {
			for(Lane lane : street) {
				if(!lane.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
	
}
