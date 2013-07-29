import java.util.Random;


public class Intersection implements Runnable {
	
	private Thread[][] threads;
	private Lane[][] lanes;
	private Street[] streets;
	private Light[][] lights;
	private int totalNumCars;
	private double[] percentages;
	private boolean sim, northSouth;
	final String[] directions = {"South", "West", "North", "East"}, turns = {"Left", "Straight", "Right"};
	private long lastTimeChanged;
	private Random rand;
	
	public Intersection(int totalNumCars, double[] percentages, boolean sim) {
		this.threads = new Thread[4][3];
		this.lanes = new Lane[4][3];
		this.streets = new Street[4];
		this.lights = new Light[4][3];
		this.totalNumCars = totalNumCars;
		this.percentages = percentages;
		this.sim = sim;
		this.rand = new Random();
		this.northSouth = rand.nextBoolean();
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
		
		if(northSouth) {
			this.changeToGreen(0);
		} else {
			this.changeToGreen(1);
		}
		this.lastTimeChanged = System.currentTimeMillis();
		
		int second = 0;
		
		while(this.checkAlive() || this.checkNotEmpty()) {
			second++;
			//crossing
			for(int i = 0; i < this.lanes.length; i++) {
				for(int j = 0; j < this.lanes[i].length; j++) {
					if(this.lights[i][j].isGreen()) {
						Car toCross = this.lanes[i][j].cross();
						if(toCross != null) {
							System.out.println("Car " + this.directions[toCross.getStartId()] + " lane went " + this.turns[toCross.getEndId()]);
						}
					} else if(this.lights[i][j].isYellow()) {
						this.lights[i][j].cycle();
					}
				}
			}
			
			if (this.lastTimeChanged + 2000/(this.sim ? 10 : 1) < System.currentTimeMillis()) {
				if (this.timeToChange()) {
					this.cycleToRed((this.northSouth ? 0 : 1));
					this.sleep(1000);
					this.cycleToRed((this.northSouth ? 0 : 1));
					this.sleep(1000);
					this.northSouth = !this.northSouth;
					this.lastTimeChanged = System.currentTimeMillis() + 1000/(this.sim ? 10 : 1);
					this.changeToGreen((this.northSouth ? 0 : 1));
					System.out.println("Directions changed");
				}
			}
			
			System.out.println("Second: " + second);
			this.sleep(1000/(this.sim ? 10 : 1));
		}
	}
	
	private void cycleToRed(int j) {
		for(int i = 0; i < this.lanes[j].length; i++) {
			if(!this.lights[j][i].isRed()) {
				this.lights[j][i].cycle();
			}
		}
		j += 2;
		for(int i = 0; i < this.lanes[j].length; i++) {
			if(!this.lights[j][i].isRed()) {
				this.lights[j][i].cycle();
			}
		}
	}
	
	private void changeToGreen(int j) {
		for(int i = 0; i < this.lanes[j].length; i++) {
			if(!this.lanes[j][i].isEmpty()) {
				while(!this.lights[j][i].isGreen()) {
					this.lights[j][i].cycle();
				}
			}
		}
		j += 2;
		for(int i = 0; i < this.lanes[j].length; i++) {
			if(!this.lanes[j][i].isEmpty()) {
				while(!this.lights[j][i].isGreen()) {
					this.lights[j][i].cycle();
				}
			}
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
	
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {}
	}
	
	private long getCombinedAvg(int i) {
		return this.streets[i].getWeightedAvg() + this.streets[i+2].getWeightedAvg();
	}
	
	private boolean timeToChange() {
		if(this.northSouth) {
			if(this.getCombinedAvg(0) < this.getCombinedAvg(1)) {
				return true;
			}
		} else {
			if(this.getCombinedAvg(0) > this.getCombinedAvg(1)) {
				return true;
			}
		}
		return false;
	}
}
