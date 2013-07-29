/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.PrintWriter;
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
	private long waitTime;
	private Random rand;
	private LinkedList<Long> times;
	private LinkedList<Car> toDraw;
	private PrintWriter printer;
	
	public Intersection(int totalNumCars, double[] percentages, boolean sim, long waitTime) {
		this.threads = new Thread[4][3];
		this.lanes = new Lane[4][3];
		this.streets = new Street[4];
		this.lights = new Light[4][3];
		this.totalNumCars = totalNumCars;
		this.percentages = percentages;
		this.sim = sim;
		this.toDraw = new LinkedList<Car>();
		this.times = new LinkedList<Long>();
		this.waitTime = waitTime;
		this.rand = new Random();
		try {
			this.printer = new PrintWriter("lastRun.txt");
		} catch (IOException e) {}
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
		int dir = (this.northSouth ? 0 : 1);
		while(this.checkAlive() || this.checkNotEmpty()) {
			dir = (this.northSouth ? 0 : 1);
			second++;
			//crossing
//			for(int i = 0; i < this.lanes.length; i++) {
//				for(int j = 0; j < this.lanes[i].length; j++) {
//					if(this.lights[i][j].isGreen()) {
//						Car toCross = this.lanes[i][j].cross();
//						if(toCross != null) {
//							System.out.println("Car " + this.directions[toCross.getStartId()] + " lane went " + this.turns[toCross.getEndId()]);
//						}
//					} else if(this.lights[i][j].isYellow()) {
//						this.lights[i][j].cycle();
//					}
//				}
//			}
			

				this.leftTurn();
				this.rightTurns();
				this.straight();
				
				
				if(this.leftSignal(dir)) {
				//	this.lastTimeChanged = System.currentTimeMillis() + 500/(this.sim ? 10 : 1);
				}
				if(this.centerSignal(dir)) {
					this.lastTimeChanged = System.currentTimeMillis() + 1000/(this.sim ? 10 : 1);
				}
				this.rightSignal(dir);
				

			
			if (this.lastTimeChanged + this.waitTime/(this.sim ? 10 : 1) < System.currentTimeMillis()/* || this.checkForRed(dir)*/ /*|| this.checkForRed(dir)*/) {
				if (this.timeToChange() || this.checkForRed((dir == 1 ? 0 : 1))) {
					this.cycleToRed(dir);
					this.sleep(1000/(this.sim ? 10 : 1));
					this.cycleToRed(dir);
					this.sleep(1000/(this.sim ? 10 : 1));
					this.northSouth = !this.northSouth;
					dir = (this.northSouth ? 0 : 1);
					this.lastTimeChanged = System.currentTimeMillis() + 1000/(this.sim ? 10 : 1);
					this.leftSignal(dir);
					this.centerSignal(dir);
					this.rightSignal(dir);
					if (!sim) {
					//	System.out.println("Directions changed");
					}
				}
			}
			if(!sim){
	//		System.out.println(this.printSignals());
	//		this.printLines();
		//	System.out.println("Second: " + second);
			}
			this.sleep(1000/(this.sim ? 10 : 1));
		}
		this.printer.close();
	}
	
	private void rightTurns() {
		for(int i = 0; i < 4; i++) {
			if(!this.lanes[i][2].isEmpty()) {
				if((this.lanes[(i+1)%4][1].isEmpty() && (this.lanes[(i+2)%4][0].isEmpty()) || this.lights[(i+2)%4][0].isRed()) || (this.lights[i][2].isGreen())) {
					Car toCross = this.lanes[i][2].cross();
					if(toCross != null) {
						if(!sim){
							this.toDraw.insertLast(toCross);
							this.printer.println("Car " + this.directions[toCross.getStartId()] + " lane went " + this.turns[toCross.getEndId()] + ", Seconds waiting: " + (System.currentTimeMillis() - toCross.getCreationTime())/(this.sim ? 100 : 1000));
						}
						this.times.insert(toCross.getWaitingTime());
					}
				}
			}
		}
	}
	
	private void straight() {
		for(int i = 0; i < 4; i++) {
			if(!this.lanes[i][1].isEmpty()) {
				if(this.lights[i][1].isGreen()) {
					Car toCross = this.lanes[i][1].cross();
					if(toCross != null) {
						if(!sim){
							this.toDraw.insertLast(toCross);
							this.printer.println("Car " + this.directions[toCross.getStartId()] + " lane went " + this.turns[toCross.getEndId()] + ", Seconds waiting: " + (System.currentTimeMillis() - toCross.getCreationTime())/(this.sim ? 100 : 1000));
						}
						this.times.insert(toCross.getWaitingTime());
					}
				}
			}
		}
	}
	
	private void leftTurn() {
		for(int i = 0; i < 4; i++) {
			if(!this.lanes[i][0].isEmpty()) {
				if((this.lights[i][0].isGreen()) || (this.lights[i][1].isGreen() && this.lanes[(i+2)%4][1].isEmpty() && this.lanes[(i+2)%4][2].isEmpty())) {
					Car toCross = this.lanes[i][0].cross();
					if(toCross != null) {
						if(!sim){
							this.toDraw.insertLast(toCross);
							this.printer.println("Car " + this.directions[toCross.getStartId()] + " lane went " + this.turns[toCross.getEndId()] + ", Seconds waiting: " + (System.currentTimeMillis() - toCross.getCreationTime())/(this.sim ? 100 : 1000));
						}
						this.times.insert(toCross.getWaitingTime());
					}
				}
			}
		}
	}
	
	private boolean leftSignal(int j) {
		boolean toReturn = false;
		if(!this.lanes[j][0].isEmpty() && this.lights[j][0].isRed() && this.lights[(j+2)%4][1].isRed() && this.lights[(j+2)%4][2].isRed()) {
			this.lights[j][0].cycle();
			toReturn = true;
		} else if(this.lanes[j][0].isEmpty() && this.lights[j][0].isGreen()) {
			this.lights[j][0].cycle();
			toReturn = true;
		} else if(this.lights[j][0].isYellow()) {
			this.lights[j][0].cycle();
			toReturn = true;
		}
		j+=2;
		
		
		if(!this.lanes[j][0].isEmpty() && this.lights[j][0].isRed() && this.lights[(j+2)%4][1].isRed() && this.lights[(j+2)%4][2].isRed()) {
			this.lights[j][0].cycle();
			toReturn = true;
		} else if(this.lanes[j][0].isEmpty() && this.lights[j][0].isGreen()) {
			this.lights[j][0].cycle();
			toReturn = true;
		} else if(this.lights[j][0].isYellow()) {
			this.lights[j][0].cycle();
			toReturn = true;
		}
		return toReturn;
	}
	
	private boolean rightSignal(int j) {
		boolean toReturn = false;
		if(!this.lanes[j][2].isEmpty() && ((this.lights[(j+2)%4][0].isRed()) || this.lights[j][1].isGreen())) {
			this.lights[j][2].cycle();
			toReturn = true;
		} else if(this.lanes[j][2].isEmpty() && this.lights[j][2].isGreen()) {
			this.lights[j][2].cycle();
		} else if(this.lights[j][2].isYellow()) {
			this.lights[j][2].cycle();
		}
		j+=2;
		
		if(!this.lanes[j][2].isEmpty() && ((this.lights[(j+2)%4][0].isRed()) || this.lights[j][1].isGreen())) {
			this.lights[j][2].cycle();
			toReturn = true;
		} else if(this.lanes[j][2].isEmpty() && this.lights[j][2].isGreen()) {
			this.lights[j][2].cycle();
		} else if(this.lights[j][2].isYellow()) {
			this.lights[j][2].cycle();
		}
		return toReturn;
	}
	
	private boolean centerSignal(int j) {
		boolean toReturn = false;
		if(!this.lanes[j][1].isEmpty() && this.lights[j][1].isRed() && this.lights[(j+2)%4][0].isRed()) {
			this.lights[j][1].cycle();
			toReturn = true;
		} else if(this.lanes[j][1].isEmpty() && this.lights[j][1].isGreen()) {
			this.lights[j][1].cycle();
		} else if(this.lights[j][1].isYellow()) {
			this.lights[j][1].cycle();
		}
		j+=2;
		
		if(!this.lanes[j][1].isEmpty() && this.lights[j][1].isRed() && this.lights[(j+2)%4][0].isRed()) {
			this.lights[j][1].cycle();
			toReturn = true;
		} else if(this.lanes[j][1].isEmpty() && this.lights[j][1].isGreen()) {
			this.lights[j][1].cycle();
		} else if(this.lights[j][1].isYellow()) {
			this.lights[j][1].cycle();
		}
		return toReturn;
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
	
	private boolean checkForRed(int j) {
		boolean toReturn = false;
		long testing[] = new long[4];
		try{
			testing[0] = (long)Math.sqrt(this.lanes[j][1].weightedAvg()/(this.lanes[j][1].size() == 0 ? 1 : this.lanes[j][0].size()))/(this.sim ? 10 : 1);
		} catch (ArithmeticException e) {
			testing[0] = 0;
		}
		try{
			testing[1] = (long)Math.sqrt(this.lanes[j+2][1].weightedAvg()/(this.lanes[j+2][1].size() == 0 ? 1 : this.lanes[j][1].size()))/(this.sim ? 10 : 1);
		} catch (ArithmeticException e) {
			testing[1] = 0;
		}
//		j+=2;
//		testing[2] = (long)Math.sqrt(this.lanes[j][0].weightedAvg()/(this.lanes[j][0].size() == 0 ? 1 : this.lanes[j][0].size()));
//		testing[3] = (long)Math.sqrt(this.lanes[j][1].weightedAvg()/(this.lanes[j][1].size() == 0 ? 1 : this.lanes[j][1].size()));;
//		
		for(long toTest : testing) {
			if(toTest > 8*this.waitTime/(this.sim ? 10 : 1) || toTest > Math.pow(this.waitTime / (this.sim ? 10 : 1), 4)) {
				toReturn = true;
				//double[] percentages = {.25,.25,.25,.25};
			//	System.out.println("Forced");
				break;
			}
		}
		
		
		return toReturn;
	}
	
	public boolean checkAlive() {
		for(Thread[] group : this.threads) {
			for(Thread t : group) {
				if(t.isAlive()) {
					return true;
				}
			}
		}
	//	System.out.println("All dead");
		return false;
	}
	
	public boolean checkNotEmpty() {
		boolean toReturn = false;
		for(int i = 0; i < this.lanes.length; i++) {
			for(int j = 0; j < this.lanes[i].length; j++) {
		//		System.out.print(this.lanes[i][j].size() + " ");
				if(!this.lanes[i][j].isEmpty()) {
					toReturn = true;
					break;
				}
			}
	//		System.out.println();
		}
		return toReturn;
	}
	
	public void printLines() {
		for(int i = 0; i < this.lanes.length; i++) {
			for(int j = 0; j < this.lanes[i].length; j++) {
				System.out.print(this.lanes[i][j].size() + " ");
			}
			System.out.println();
		}
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
			} else if(this.getCombinedAvg(0) <= 1) {
				return true;
			}
		} else {
			if(this.getCombinedAvg(0) > this.getCombinedAvg(1)) {
				return true;
			} else if(this.getCombinedAvg(1) <= 1) {
				return true;
			}
		}
		return false;
	}
	
	private String printSignals() {
		String toReturn = "\n";
		toReturn += "\t";
		for(int i = 0; i < 3; i++) {
			toReturn +=" " + this.lanes[2][2-i].size() + " ";
		}
		toReturn +="\n\t";
		for(int i = 0; i < 3; i++) {
			toReturn += " " + (this.lanes[2][2-i].peekFirst() == null ? 0 : (int)(System.currentTimeMillis() - this.lanes[2][2-i].peekFirst().getCreationTime())/(this.sim ? 100 : 1000)) + " ";
		}
		toReturn += "\n\t";
		for(int i = 0; i < 3; i++) {
			toReturn += this.lights[2][2-i] + " ";
		}
		toReturn += "\n";
		for(int i = 0; i < 3; i++) {
			toReturn += "\t\t\t\t" + this.lights[3][2-i] + " " + (this.lanes[3][2-i].peekFirst() == null ? 0 : (int)(System.currentTimeMillis() - this.lanes[3][2-i].peekFirst().getCreationTime())/(this.sim ? 100 : 1000)) + " "  + this.lanes[3][2-i].size() + "\n";
		}
		toReturn += "\n";
		for(int i = 0; i < 3; i++) {
			toReturn += this.lanes[1][i].size() + " " + (this.lanes[1][i].peekFirst() == null ? 0 : (int)(System.currentTimeMillis() - this.lanes[1][i].peekFirst().getCreationTime())/(this.sim ? 100 : 1000)) + " " + this.lights[1][i] + "\n";
		}
		toReturn += "\t\t\t";
		for(int i = 0; i < 3; i++) {
			toReturn += this.lights[0][i] + " ";
		}
		toReturn += "\n\t\t\t";
		for(int i = 0; i < 3; i++) {
			toReturn += " " + (this.lanes[0][i].peekFirst() == null ? 0 : (int)(System.currentTimeMillis() - this.lanes[0][i].peekFirst().getCreationTime())/(this.sim ? 100 : 1000)) + " ";
		}
		toReturn += "\n\t\t\t";
		for(int i = 0; i < 3; i++) {
			toReturn += " " + this.lanes[0][i].size() + " ";
		}
		toReturn += "\n";
		return toReturn;
	}
	
	public LinkedList<Long> getWaitingTimes() {
		return this.times;
	}
	
	public LinkedList<Car> getToDraw() {
		return this.toDraw;
	}
	
	public void draw(Graphics2D g, float elapsedTime, JimsCanvas canvas) {
		try {
			while(this.toDraw.peekFirst().isAtEnd()) {
				try {
					this.toDraw.removeFirst();
				} catch (EmptyListException e) {}
			}
		} catch (EmptyListException e) {}
		for(int i = 0; i < this.toDraw.size(); i++) {
			try {
				Car toDraw = this.toDraw.peek(i);
				toDraw.move(elapsedTime);
				canvas.drawCar(g, toDraw);
				g.setColor(Color.pink);
				g.drawOval(toDraw.getAnchorX() - 5, toDraw.getAnchorY() - 5, 10, 10);
			} catch (EmptyListException e) {}
			
		}
		for(int i = 0; i < this.lanes.length; i++) {
			for(int j = 0; j < this.lanes[i].length; j++) {
				if (!this.lanes[i][j].isEmpty()) {
					canvas.drawCar(g, this.lanes[i][j].peekFirst());
				}
			}
		}
		for(int i = 0; i < this.lights[0].length; i++) {
			g.setColor(this.lights[0][i].getColor());
			g.fillOval(182 + 30 * i, 230, 20, 20);
		}
		for(int i = 0; i < this.lights[1].length; i++) {
			g.setColor(this.lights[1][i].getColor());
			g.fillOval(70, 182 + 30 * i, 20, 20);
		}
		for(int i = 0; i < this.lights[2].length; i++) {
			g.setColor(this.lights[2][2-i].getColor());
			g.fillOval(62 + 30 * i, 70, 20, 20);
		}
		for(int i = 0; i < this.lights[3].length; i++) {
			g.setColor(this.lights[3][2-i].getColor());
			g.fillOval(230, 62 + 30 * i, 20, 20);
		}
		
		g.setColor(Color.black);
		for(int i = 0; i < this.lanes.length; i++) {
			canvas.drawString(g, this.directions[i], 350, 70*i + 30);
			for(int j = 0; j < this.lanes[i].length; j++) {
				canvas.drawString(g, this.turns[j] + ") " + this.lanes[i][j].size() + " Cars " + (this.lanes[i][j].peekFirst() == null ? 0 : (int)(System.currentTimeMillis() - this.lanes[i][j].peekFirst().getCreationTime())/(this.sim ? 100 : 1000)) + " Seconds Waiting", 360, 70*i + 42 + 15*j); 
			}
		}
		
	}

}
