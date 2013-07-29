
public class Driver {
	
	public static void main(String[] args) {
		int numCars = 40;
		double[] percentages = {.25,.25,.25,.25};
		boolean sim = true;
		Intersection test = new Intersection(numCars, percentages, sim);
		
		Thread t = new Thread(test);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
