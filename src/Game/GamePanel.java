package Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

	//Atributos
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private double averageFPS;
	
	//Constructor
	public GamePanel() {
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		this.requestFocus();
	}
	
	//Funciones
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run() {
		running = true;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		long startTime = 0;
		long URDTimeMillis = 0;
		long waitTime = 0;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = 1000 / FPS;
		
		//loop del juego
		while(running) {
			
			startTime = System.nanoTime();
			
			this.gameUpdate();
			this.gameRender();
			this.gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			
			try { 
				Thread.sleep(waitTime);
			} catch (Exception e) {}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if (frameCount == maxFrameCount) {
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
	}
	
	private void gameUpdate() {
		
	}
	
	private void gameRender() {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString("Average FPS: " + averageFPS, 100, 100);
	}
	
	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	
	
}
