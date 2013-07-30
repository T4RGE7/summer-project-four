import java.util.Scanner;

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Driver {
	
	public static void main(String[] args) {
		System.out.println("Welcome to the TrafficLight Simulator");
		System.out.println("GUI or No Gui");
		boolean sim = true;
		if(new Scanner(System.in).nextLine().toUpperCase().charAt(0) == 'G') {
			sim = true;
		} else {
			sim = false;
		}
		
		System.out.println("What percentages would you like? (Split by \",\") ");
		String[] temp = new Scanner(System.in).nextLine().split(",");

		double[] percentages = {.25,.25,.25,.25};
		if(temp.length == 4) {
			try{
				for(int i = 0; i < 4; i++) {
					percentages[i] = Double.parseDouble(temp[i]);
				}
			} catch (Exception e){}
		}
		
		int numCars = 44;
	//	double[] percentages = {.25,.25,.25,.25};
		long waitTime = 2900;
		Intersection test;// = new Intersection(numCars, percentages, sim, waitTime);
//		LinkedList<Long> times = test.getWaitingTimes();
		long high = -1, low = -1, current = -1;
		
//		Thread t = new Thread(test);
//		t.start();
//		try {
//			t.join();
//		} catch (InterruptedException e) {}
//		
//		current = 0;
//		int total = 0;
//		while(!times.isEmpty()) {
//			try {
//				current += times.remove();
//				total++;
//			} catch (EmptyListException e) {break;}
//		}
//		System.out.println(total  + " total cars passed through with an average wait of " + current/(total*(sim ? 100 : 1000)) + " seconds.");
//		
//		waitTime -= 100;
//		test = new Intersection(numCars, percentages, sim, waitTime);
//		t = new Thread(test);
//		t.start();
//		try {
//			t.join();
//		} catch (InterruptedException e) {}
//		times = null;
//		times = test.getWaitingTimes();
//		long sum = 0;
//		total = 0;
//		while(!times.isEmpty()) {
//			try {
//				sum += times.remove();
//				total++;
//			} catch (EmptyListException e) {break;}
//		}
//		System.out.println(total  + " total cars passed through with an average wait of " + sum/(total*(sim ? 100 : 1000)) + " seconds.");
//		
//		boolean up = false;
//		
//		if(sum > current) {
//			low = current;
//			current = sum;
//			waitTime += 200;
//			up = true;
//		} else {
//			high = current;
//			current = sum;
//			waitTime -= 100;
//		}
//		
//		test = new Intersection(numCars, percentages, sim, waitTime);
//		t = new Thread(test);
//		t.start();
//		try {
//			t.join();
//		} catch (InterruptedException e) {}
//		times = null;
//		times = test.getWaitingTimes();
//		sum = 0;
//		total = 0;
//		while(!times.isEmpty()) {
//			try {
//				sum += times.remove();
//				total++;
//			} catch (EmptyListException e) {break;}
//		}
//		System.out.println(total  + " total cars passed through with an average wait of " + sum/(total*(sim ? 100 : 1000)) + " seconds.");
		long lowWait = waitTime;
		long lowWaitTime = 0;
		boolean strikeOne = false;
		boolean up = false;
		for(int i = 0; i < 20; i++) {
			test = new Intersection(numCars, percentages, true, waitTime);
			LinkedList<Long> times = test.getWaitingTimes();
			Thread t = new Thread(test);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {}
			
			long sum = 0;
			int total = 0;
			while(!times.isEmpty()) {
				try {
					sum += times.remove();
					total++;
				} catch (EmptyListException e) {break;}
			}
			System.out.println(total  + " total cars passed through with an average wait of " + sum/(total*(true ? 100 : 1000)) + " seconds. (WaitTime = " + waitTime + ")");
			if(current == -1) {
				current = sum;
				lowWaitTime = current/(total*(true ? 100 : 1000));
				lowWait = waitTime;
			} /*else if(high == -1 && low == -1) {
				if(sum > current) {
					high = current;
					current = sum;
					up = true;
					waitTime += 100;
				} else {
					low = sum;
					up = false;
				}
			} else if(high == -1 || low == -1) {
				if(high == -1) {
					if(sum < low) {
						high = current;
					}
				}
			}*/
			else {
				if(sum > current + (lowWaitTime * numCars / 10)) {
					up = !up;
					if (!strikeOne) {
						strikeOne = true;
					} else {
						break;
					}
				} else if(sum > current){} else {
					
					current = sum;
					lowWaitTime = current/(total*(true ? 100 : 1000));
					lowWait = waitTime;
					
				}
			}
			
			if(up) {
				waitTime += 200;
			} else {
				waitTime -= 200;
			}
		}
			
			
			
		
		System.out.print("Best minimum light wait time estimated to be: " + lowWait/1000.0 + " seconds, for an average wait time of " /*+ lowWaitTime/(!sim ? 1 : 10) + " seconds per car."*/);
	
		System.out.print(lowWaitTime);
		System.out.println(" seconds per car.");
		Thread last;
		if(sim) {
			test = new Intersection(numCars, percentages, false, lowWait);
			JimsCanvas canvas = new JimsCanvas(650, 330, test);
			canvas.setupAndDisplay();
			last = new Thread(test);
			last.run();
			LinkedList<Long> times = test.getWaitingTimes();
			try {
				last.join();
				canvas.setVisible(false);
				long sum = 0;
				int total = 0;
				while(!times.isEmpty()) {
					try {
						sum += times.remove();
						total++;
					} catch (EmptyListException e) {break;}
				}
				System.out.println(total  + " total cars passed through with an average wait of " + sum/(total*1000) + " seconds. (WaitTime = " + lowWait + ")");
				System.exit(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
