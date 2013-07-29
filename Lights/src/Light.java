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
	
	public boolean isGreen() {
		return this.green;
	}
	
	public boolean isYellow() {
		return this.yellow;
	}
	
	public boolean isRed() {
		return this.red;
	}
	
	public void cycle() {
		if(this.red) {
			this.red = false;
			this.yellow = true;
		} else if(this.yellow) {
			this.yellow = false;
			this.green = true;
		} else {
			this.green = false;
			this.red = true;
		}
	}
	
}
