/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Light {

	boolean red, yellow, green;
	
	public Light() {
		this.red = true;
		this.yellow = false;
		this.green = false;
	}
	
	public synchronized boolean isGreen() {
		return this.green;
	}
	
	public synchronized boolean isYellow() {
		return this.yellow;
	}
	
	public synchronized boolean isRed() {
		return this.red;
	}
	
	public synchronized void cycle() {
		if(this.red) {
			this.red = false;
			this.green = true;
		} else if(this.yellow) {
			this.yellow = false;
			this.red = true;
		} else {
			this.green = false;
			this.yellow = true;
		}
	}
	
	public String toString() {
		if(this.red) {
			return "Red";
		}
		if(this.yellow) {
			return "Yellow";
		}
		if(this.green) {
			return "Green";
		}
		return "";
	}
}
