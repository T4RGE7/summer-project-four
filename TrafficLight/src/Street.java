/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Street implements Runnable {

	private Lane[] lanes;
	private Light[] lights;
	private int id, totalNumber;
	public final double percentage[] = {.25,.5,.25};
	private boolean graphical;
	private Thread[] threads;
	
	public Street(int start, int totalNumber, boolean graphical) {
		this.lanes = new Lane[3];
		this.lights = new Light[3];
		this.id = start;
		this.totalNumber = totalNumber;
		this.graphical = graphical;
		this.threads = new Thread[this.lanes.length];
		this.setUP();
	}
	
	private void setUP() {
		for(int i = 0; i < 3; i++) {
			this.lanes[i] = new Lane(this.id, i, this.totalNumber, this.percentage[i], this.graphical);
			this.threads[i] = new Thread(this.lanes[i]);
		}
		for(int i = 0; i < 2; i++) {
			this.lights[i] = new Light();
		}
		this.lights[2] = this.lights[1];
	}
	
	public void run() {
		for(Thread t : this.threads) {
			t.start();
		}
	}
	
	public Thread[] getThreads() {
		return this.threads;
	}
	
	public Lane[] getLanes() {
		return this.lanes;
	}
	
	public Light[] getLights() {
		return this.lights;
	}
	
	public long getWeightedAvg() {
		long toReturn = 0;
		for(Lane singleLane : this.lanes) {
			toReturn += singleLane.getWeightedAvg();
		}
		return toReturn;
	}
}
