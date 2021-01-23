package arrays;
// Angry Birds template provided by Mr. David

import java.awt.Color;
import java.awt.Font;
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
	private final int NUM_ENEMIES = 5;
	
	// a constant for the gravitational pull on our 'bird' - again, feel free to change
	private final double GRAVITY = .4;

	// I have enemyX and enemyY, two arrays that contains all their values. the diams measure
	//the launcher and enemy hitspace for collision and obstacle is the same.
	private int[] enemyX = {500, 450, 760, 600, 300};
	private int[] enemyY = {330, 330, 200, 200, 450};
	private final int LAUNCHERDIAM = 55, ENEMYDIAM = 80;
	private final int OBSTACLEDIAM = 150;
	
	
	// my images 
	private Image shrek;	
	private Image background;
	private Image villan;
	private Image obstacle;
	private Image launch;

	// you'll need more instance variables - put them here.
	
	
	// all of my variables
	private int startX, startY;
	private int bounceup = 5;
	private boolean hitobY = false;
	private boolean win = false;
	private boolean hitob = false;
	private boolean birddead = false;
	private int bounce = 2;
	private int[] obstacleX = {600, 600, 470, 730};
	private int[] obstacleY = {300, 430, 430, 300};
	private int pausetime = 500;
	private int lives = 8;
	private boolean move = true;
	private boolean gameover = false;
	private Boolean[] dead = {false, false, false, false, false};
	private int score = 0;
	private int fall = 10;
	private double SpeedX = 0, SpeedY = 0;
	private double birdX = 100, birdY = 300;
	private int birdstartX = 100, birdstartY = 300;
	private int launchstartX = birdstartX - 20;
	private int imagewh = 130;
	private int launchheight = imagewh + 200;
	private boolean isgravityon = false;
	
	// x and y for my word displays
	private int popupX = 500;
	private int popupY = 150;
	private int scoreX = 100;
	private int scoreY = 200;
	private int livesY = 150;
	private int winX = 400;
	private int winY = 300;
	
	// this method is for setting up any arrays that need filling in and loading images. 
	// This method will run once at the start of the game.
	public void setup() {

		// example of loading an image file - edit to suit your project
		
		// draw image for shrek
		try {
			shrek = ImageIO.read(new File("shrek.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// draw image for launcher
		try {
			launch = ImageIO.read(new File("launch.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// draw image for backgorund
		try {
			background = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// draw image for villan
		try {
			villan = ImageIO.read(new File("villan.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// draw image for obstacle
		try {
			obstacle = ImageIO.read(new File("obstacle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		// your code here
	}
	
	// move your 'bird' and apply any gravitational pull 
	public void moveBird() {
		// shows if the bird moves and the gravity effects
		if (move == true) {
			birdX += SpeedX;
			birdY += SpeedY;
		
		
			if (isgravityon)
				SpeedY += GRAVITY;
		
		}
		// if bird Y value is greater than window height + pause time(more effects) it will go back to start
		// and score will +1
		if (birdY > W_HEIGHT + pausetime) {
			birddead = true;
			birdX = birdstartX;
			birdY = birdstartY;
			
			lives -= 1;
			move = false;
			hitob = false;
			birddead = false;
			
			
		}
		// if bird Y is greater than ceiling than bird dead
		if (birdY < -W_HEIGHT) {
			birddead = true;
			birdX = birdstartX;
			birdY = birdstartY;
			
			lives -= 1;
			move = false;
			hitob = false;
			birddead = false;
			
			
		}
		
		// if birdX greater than left and right bird is dead
		if (birdX > W_WIDTH + pausetime) {
			birddead = true;
			birdX = birdstartX;
			birdY = birdstartY;
			
			lives -= 1;
			move = false;
			hitob = false;
			birddead = false;
		}
	
		// if enemy is dead, then enemy falls
		for (int i = 0; i < enemyY.length; i++) {
			if (dead[i] == true) {
				enemyY[i]+= fall;
				
			}
				
		}
		
		
		// if hitob is true and bird is not dead, then it bounce off the side of the obstacle
		if (hitob == true && birddead == false) {
			birdX -= SpeedX * bounce;
			birdY += fall;
			
		}
		
		//if hitobY is true and bird is not dead, then it bounce off the top of obstacle
		if (hitobY == true && birddead == false) {
			
			isgravityon = false;
			SpeedY -= bounceup;
		
			hitobY = false;
			
		}
		
	
		
	}
	
	// check for any collisions between your 'bird' and the enemies.
	// if there is a collision, address it
	public void checkHits() {
		// check for collisions on the birds and the enemy and the bird and the obstacle's left and upper side.
	
	for (int i = 0; i < enemyX.length; i++) {
		if (distance(birdX, birdY, enemyX[i], enemyY[i]) <= ENEMYDIAM/2 + LAUNCHERDIAM/2) {
			
			if (dead[i] == false) {
				score ++;
			}
			dead[i] = true;		
		}
	}
	//object hit detection for bird and side of obstacles, will bounce off the side
	for (int j = 0; j < obstacleX.length; j++) {
		if (birdY > obstacleY[j] && birdY <= obstacleY[j] + imagewh && birdX > obstacleX[j] - imagewh) {
			hitob = true;
		
		}
	}
	
	//object hit detection for bird and top of obstacles, will bounce off the top if hit(sometimes does
	//not work like the previous method, this check hit method is somewhat sketchy.
	for (int j = 0; j < obstacleX.length; j++) {
		if (birdX > obstacleX[j] && birdX <= obstacleX[j] + imagewh && birdY > obstacleY[j] - imagewh) {
			hitobY = true;
			
		}
	}
}

	// distance formula basically copied from math
	private double distance(double birdX, double birdY, int enemyX, int enemyY) {
		double without = Math.pow((enemyX-birdX), 2) + Math.pow((enemyY - birdY), 2);
		without = Math.sqrt(without);
		
		return without;

	
	}
	
	// what you want to happen at the moment when the 
	// mouse is first pressed down.
	public void mousePressed(int mouseX, int mouseY) {
		// what happens when mouse is pressed, initialize two variables.
		
		startX = mouseX;
		startY = mouseY;
		
	}
	
	// what you want to happen when the mouse button is released
	public void mouseReleased(int mouseX, int mouseY) {
		// when mouse is released the bird is launched in the opposite direction of the drag.
	if (gameover == false && win == false) {
		int distDraggedX = mouseX - startX;
		int distDraggedY = mouseY - startY;
		SpeedX = -distDraggedX/10.0;
		SpeedY = -distDraggedY/10.0;
		
		isgravityon = true;
		move = true;
	}
	}
	
	// draws everything in our project - the enemies, your 'bird', 
	// and anything else that is present in the game
	public void paint(Graphics g) {
		// draws a white background
		g.drawImage(background, 0, 0, W_WIDTH, W_HEIGHT, null);
		g.drawRect(0, 0, W_WIDTH, W_HEIGHT);
		
		g.drawImage(launch, launchstartX, birdstartY, imagewh, launchheight, null);
		// all of my image drawn
		g.drawImage(shrek,(int)birdX, (int)birdY, imagewh, imagewh, null);
		//draws all the obstacles
		for (int j = 0; j < obstacleX.length; j++) {
			g.drawImage(obstacle, obstacleX[j], obstacleY[j], imagewh, imagewh, null);
		}
		// draws all the enemies
		for (int i = 0; i < enemyX.length; i++) {
			g.drawImage(villan, enemyX[i], enemyY[i], imagewh, imagewh, null);
		}
		
		
		
		// these are scores and lives for the game drawn and special
		//popups for each score phase.
		Font f = new Font("Arial", Font.BOLD, 25);
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString("score: " + score, scoreX, scoreY);
		g.drawString("lives: " + lives, scoreX, livesY);
		// if socre is one beginners luck pops up
		if (score == 1) {
			
			Font a = new Font("Serif", Font.BOLD, 25);
			g.setFont(a);
			g.setColor(Color.green);
			g.drawString("beginner's luck", popupX, popupY);
		
		}
		// if score is two then two in a role pops up
		if (score == 2) {
			
			Font a = new Font("Serif Bold", Font.BOLD, 25);
			g.setFont(a);
			g.setColor(Color.green);
			g.drawString("two IN A ROLE", popupX, popupY);
		
		}
		// if the score is four then almost pops up
		if (score == 4) {
			
			Font a = new Font("Arial", Font.BOLD, 25);
			g.setFont(a);
			g.setColor(Color.green);
			g.drawString("ALMOST", popupX, popupY);
		
		}
		// is score is five then the player won
		if (score == 5) {
			win = true;
			Font v = new Font("Serif Bold", Font.BOLD, 400);
			g.setColor(Color.green);
			g.drawString("YOU WON!!!", winX, winY);
		}
		
		// if lives is 0 then game is over
		if (lives == 0) {
			gameover = true;
			Font v = new Font("Serif Bold", Font.BOLD, 400);
			g.setColor(Color.red);
			g.drawString("GAME OVER", winX, winY);
			
		}
		
		
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
