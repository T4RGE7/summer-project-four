import java.awt.Color;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Light {

	private boolean red, yellow, green;
	private Color color;
	
	public Light() {
		this.red = true;
		this.yellow = false;
		this.green = false;
		this.color = Color.red;
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
			this.color = Color.green;
		} else if(this.yellow) {
			this.yellow = false;
			this.red = true;
			this.color = Color.red;
		} else {
			this.green = false;
			this.yellow = true;
			this.color = Color.yellow;
		}
	}
	
	public Color getColor() {
		return this.color;
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
