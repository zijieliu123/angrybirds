package arrays;
// Angry Birds template provided by Mr. David

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AngryBirdsFiller extends JPanel {
	
	// the width/height of the window - feel free to change these
	private final int W_WIDTH = 900, W_HEIGHT = 600;
	
	// the number of enemies in the game - feel free to change
	private final int NUM_ENEMIES = 6;
	
	// a constant for the gravitational pull on our 'bird' - again, feel free to change
	private final double GRAVITY = .4;

	// hints...
	private int[] enemyX = {500, 600, 690, 600, 500};
	private int[] enemyY = {300, 360, 450, 500, 400};
	private boolean[] dead;
	
	private Image shrek;	// example for using images - delete/replace this!
	private Image background;
	private Image villan;

	// you'll need more instance variables - put them here.
	
	private int startX, startY;
	private double SpeedX = 0, SpeedY = 0;
	private double birdX = 100, birdY = 400;
	private int imagewh = 80;
	private boolean isgravityon = false;
	
	// this method is for setting up any arrays that need filling in and loading images. 
	// This method will run once at the start of the game.
	public void setup() {

		// example of loading an image file - edit to suit your project
		try {
			shrek = ImageIO.read(new File("shrek.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			background = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			villan = ImageIO.read(new File("villan.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// your code here
		
		
	}
	
	// move your 'bird' and apply any gravitational pull 
	public void moveBird() {
		// your code here
		birdX += SpeedX;
		birdY += SpeedY;
		
		if (isgravityon)
			SpeedY += GRAVITY;
	}
	
	// check for any collisions between your 'bird' and the enemies.
	// if there is a collision, address it
	public void checkHits() {
		// your code here
	}
	
	// what you want to happen at the moment when the 
	// mouse is first pressed down.
	public void mousePressed(int mouseX, int mouseY) {
		// your code here
		startX = mouseX;
		startY = mouseY;
	}
	
	// what you want to happen when the mouse button is released
	public void mouseReleased(int mouseX, int mouseY) {
		// your code here
		int distDraggedX = mouseX - startX;
		int distDraggedY = mouseY - startY;
		SpeedX = -distDraggedX/10.0;
		SpeedY = -distDraggedY/10.0;
		
		isgravityon = true;
	}
	
	// draws everything in our project - the enemies, your 'bird', 
	// and anything else that is present in the game
	public void paint(Graphics g) {
		// draws a white background
		g.drawImage(background, 0, 0, W_WIDTH, W_HEIGHT, null);
		g.drawRect(0, 0, W_WIDTH, W_HEIGHT);
		
		// example of how to draw an image - draws at (100, 100) with width/height of 40/40
		g.drawImage(shrek,(int)birdX, (int)birdY, imagewh, imagewh, null);
		for (int i = 0; i < enemyX.length; i++) {
			g.drawImage(villan, enemyX[i], enemyY[i], imagewh, imagewh, null);
		}
		
		// your code here
	}
	
	
	// ************** DON'T TOUCH THE BELOW CODE ********************** //
	
	public void run() {
		while (true) {
			moveBird();
			checkHits();
			repaint();
			
			try {Thread.sleep(20);} 
			catch (InterruptedException e) {}
		}
	}
	
	public AngryBirdsFiller() {
		setup();
		
		JFrame frame = new JFrame();
		frame.setSize(W_WIDTH,W_HEIGHT);
		this.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				AngryBirdsFiller.this.mousePressed(e.getX(),e.getY());	
			}
			public void mouseReleased(MouseEvent e) {
				AngryBirdsFiller.this.mouseReleased(e.getX(),e.getY());
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		frame.add(this);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		run();
	}
	public static void main(String[] args) {
		new AngryBirdsFiller();
	}
}
