/**
 * 
 * @author James Roberts jpr242
 *
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JimsCanvas extends JPanel /*implements Runnable*/ {

	private static final long serialVersionUID = 1L;


	private int width;
	private int height;

	private long lastTime;
	
	private Intersection intersection;

	private LinkedList<Car> toDraw;
	
	private BufferedImage road, car;
	
public JimsCanvas(int w, int h, Intersection field) {
		width = w;
		height = h;
		intersection = field;
		this.toDraw = field.getToDraw();
		lastTime = -1L;
		try {
			road = ImageIO.read(new File("road1.png"));
			car = ImageIO.read(new File("car.png"));
		} catch (Exception e) {
			System.err.println("Images not found in project directory!");
		}
	}
	
	



	public void setupAndDisplay() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JScrollPane(this));
		frame.setSize(width, height);
		frame.setLocation(0, 0);
		frame.setVisible(true);
	}

	protected void paintComponent(Graphics graphics) {
		boolean firstCall = (lastTime == -1L);
		long elapsedTime = System.nanoTime() - lastTime;
		lastTime = System.nanoTime();
		graphics.setColor(Color.lightGray);
		graphics.fillRect(0, 0, 650, 330);
		graphics.drawImage(road, 0, 0, null);

		
		
		intersection.draw((Graphics2D) graphics, (firstCall ? 0.0f
				: (float) elapsedTime / 1e9f), this);
		graphics.fillRect(0, 0, 330, 30);
		graphics.fillRect(0, 0, 30, 330);
		graphics.fillRect(0, 290, 330, 40);
		graphics.fillRect(290, 0, 40, 330);
		repaint();
	}

	
	public void drawCar(Graphics2D graphics, Car toDraw) {
		
		AffineTransform old = new AffineTransform();
		Image temp = car;
		
		int x = toDraw.getX();
		int y = toDraw.getY();
		double rotation = toDraw.getRotation();
//		System.out.println(rotation);
//		System.out.println("RAD: " + Math.PI * rotation/180);
		old.translate(x, y);
		old.rotate(Math.PI * rotation/180);
//		old.rotate(rotation/180.0, toDraw.getAnchorX(), toDraw.getAnchorY());
		old.translate(-temp.getWidth(null)/2, -temp.getHeight(null)/2);

		graphics.drawImage(temp, old, null);
		//graphics.dispose();

	}


	public void drawString(Graphics2D graphics, String toDraw, int x, int y){
		graphics.drawString(toDraw, x, y);
	}

//
//
//
//	@Override
//	public void run() {
//		while(this.intersection.checkAlive() || this.intersection.checkNotEmpty()) {
//			
//		}
//		
//	}

}
