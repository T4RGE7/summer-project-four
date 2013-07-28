/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Intersection implements Runnable {

	private Street[] streets;
	private Lane[][] lanes;
	private Light[][] lights;
	//lane threads
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
		for(Street street : this.streets) {
			new Thread(street).run();
		}
		System.out.println("Intersection started");
		long startChangeTime = -1;
		boolean changing = false;
		
		while(this.checkAllQueues() || this.checkAllThreads()) {
			System.out.println(this.lanes[0][1].size());
			//car removal
			for(int i = 0; i < this.lanes.length; i++) {
//				if(this.checkForGreen(this.lights[i])) {
//					for(int j = 0; j < this.lanes[i].length; j++) {
//						if(this.lights[i][j].isGreen()) {
//							//car goes
//							Car crossing = this.lanes[i][j].cross();
//							if(crossing != null) {
//								System.out.println("Removed");
//							}
//						}
//
//					}
					//left
					if(this.lights[i][0].isGreen() || (this.lanes[(i+2)%4][1].isEmpty() && this.lights[i][1].isGreen() && this.lanes[(i+2)%4][2].isEmpty())) {
						Car crossing = this.lanes[i][0].cross();
						if(crossing != null) {
							System.out.println("Left Turn");
						}
					}
					//straight
					if(this.lights[i][1].isGreen()) {
						Car crossing = this.lanes[i][1].cross();
						if(crossing != null) {
							System.out.println("Straight");
						}
					}
					//right
					if(this.lights[i][2].isGreen() || (this.lanes[(i+1)%4][1].isEmpty() && this.lights[(i+2)%4][0].isRed())) {
						Car crossing = this.lanes[i][2].cross();
						if(crossing != null) {
							System.out.println("Straight");
						}
					}
					
				}
	//		}
			
			//light changing
			if(!this.checkForNotRed(this.lights[0]) || !this.checkForNotRed(this.lights[2])) {
				if(this.getWeightedAvg(0) < this.getWeightedAvg(1)) {
					this.turnToRed(this.lights[0]);
					this.turnToRed(this.lights[2]);
					changing = true;
				}
				
				if(changing && (this.checkForNotRed(this.lights[0]) || this.checkForNotRed(this.lights[2]))) {
					this.turnToRed(this.lights[0]);
					this.turnToRed(this.lights[2]);
				} else if(changing) {
					changing = false;
					this.changeToGreen(1);
				}
			} else {
				if(this.getWeightedAvg(1) < this.getWeightedAvg(0)) {
					this.turnToRed(this.lights[1]);
					this.turnToRed(this.lights[3]);
					changing = true;
				}
				
				if(changing && (this.checkForNotRed(this.lights[1]) || this.checkForNotRed(this.lights[3]))) {
					this.turnToRed(this.lights[1]);
					this.turnToRed(this.lights[3]);
				} else if(changing) {
					changing = false;
					this.changeToGreen(0);
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean checkAllThreads() {
		for(Thread[] threadArray : this.threads) {
			for(Thread thread : threadArray) {
				if(thread.isAlive()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkAllQueues() {
		for(Lane[] laneArray : this.lanes) {
			for(Lane lane : laneArray) {
				if(!lane.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkForGreen(Light[] lights) {
		for(Light single : lights) {
			if(single.isGreen()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkForNotRed(Light[] lights) {
		for(Light single : lights) {
			if(!single.isRed()) {
				return false;
			}
		}
		return true;
	}
	
	private long getWeightedAvg(int i) {
		long toReturn = 0;
		
		for(Lane single : this.lanes[i]) {
			toReturn += single.getWeightedAvg();
		}
		for(Lane single : this.lanes[i + 2]) {
			toReturn += single.getWeightedAvg();
		}
		return toReturn;
	}
	
	private void turnToRed(Light[] lights) {
		for(Light single : lights) {
			if(!single.isRed()) {
				single.cycle();
			}
		}
	}
	
	private void changeToGreen(int j) {
		for(int i = 0; i < 3; i++) {
			if(!this.lanes[j][i].isEmpty()) {
				while(!this.lights[j][i].isGreen()) {
					this.lights[j][i].cycle();
				}
			}
			if(!this.lanes[j+2][i].isEmpty()) {
				while(!this.lights[j+2][i].isGreen()) {
					this.lights[j+2][i].cycle();
				}
			}
		}
	}
}
