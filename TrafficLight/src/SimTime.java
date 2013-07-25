/**
 * 
 * @author James Roberts jpr242
 *
 */
public class SimTime implements Runnable {
	
	private long time;
	
	public SimTime() {
		this.time = 0;
	}

	public void run () {
		this.time++;
	}
	
	public synchronized long getTime() {
		return this.time;
	}
}
