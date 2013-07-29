
public class Driver {
	
	public static void main(String[] args) {
		System.out.println("Welcome to the TrafficLight Simulator");
		int numCars = 100;
		double[] percentages = {1,.0,.0,.0};
		boolean sim = true;
		long waitTime = 2500;
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
		for(int i = 0; i < 1; i++) {
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
			System.out.println(total  + " total cars passed through with an average wait of " + sum/(total*(sim ? 100 : 1000)) + " seconds. (WaitTime = " + waitTime + ")");
			if(current == -1) {
				current = sum;
				lowWaitTime = current/(total*(sim ? 100 : 1000));
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
				if(sum > current + (lowWaitTime * 1000)) {
					up = !up;
					if (!strikeOne) {
						strikeOne = true;
					} else {
						break;
					}
				} else if(sum > current){} else {
					
					current = sum;
					lowWaitTime = current/(total*(sim ? 100 : 1000));
					lowWait = waitTime;
					
				}
			}
			
			if(up) {
				waitTime += 100;
			} else {
				waitTime -= 100;
			}
		}
			
			
			
		
		System.out.println("Best wait time estimated to be: " + lowWait/1000.0 + " seconds, for an average wait time of " + lowWaitTime + " seconds per car.");
	
		Thread last;
		if(sim) {
			test = new Intersection(numCars, percentages, false, lowWait);
			JimsCanvas canvas = new JimsCanvas(330, 330, test);
			canvas.setupAndDisplay();
			last = new Thread(test);
			last.run();
			try {
				last.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
