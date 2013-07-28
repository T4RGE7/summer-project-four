/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean graphical = false;
		double[] percentages = {.25,.25,.25,.25};
		int numCars = 10;
		Intersection test = new Intersection(graphical, percentages, numCars);
		
		Thread t1 = new Thread(test);
		t1.run();
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
