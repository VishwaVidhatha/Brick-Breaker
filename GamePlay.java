package BrickBreaker;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.Timer;

public class GamePlay extends JPanel  implements KeyListener, ActionListener {

    
	private static final long serialVersionUID = 1L;
	//Starting of the game
    private boolean play = false;
    private int score = 0;

    //No. of bricks in game 
    private int totalBricks = 21;
    
    //Timer or Speed of the ball
    private Timer time;
    private int delay = 0;

    //X-axis & Y-axis for slider and ball
    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;

    //direction of ball
    Random random = new Random();
    int n = random.nextInt(2+1-2) - 2;  
    private int ballXDir = n;
    private int ballYdir = -2;
    
    //creating variable for map generator
    private MapGenerator map;
    
 
    //adding constructor
    public GamePlay() {
    	map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics g) {
        //adding background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592 );
        
        //calling draw function
        map.draw((Graphics2D)g);

        //adding borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //score panel
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(" "+score, 590, 30);

        //creating paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);

        //creating the ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        //if game is finished
        if(totalBricks <= 0) {
        	play = false;
        	ballXDir = 0;
        	ballYdir = 0;
        	g.setFont(new Font("serif", Font.BOLD, 35));
            g.drawString("CONGRATULATIONS! ", 200, 300);
            
            //To restart button
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press ENTER to Restart", 230, 350);
        }
        
        //game over
        if (ballposY > 570) {
        	play = false;
        	ballXDir = 0;
        	ballYdir = 0;
        	g.setFont(new Font("serif", Font.BOLD, 35));
            g.drawString("GAME OVER, Score = "+score, 150, 300);
            
            //To restart button
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press ENTER to Restart", 230, 350);
        }
        
        g.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        
        //function for ball
        if(play) {
        	if (new Rectangle(ballposX, ballposY, 20 ,20).intersects(new Rectangle(playerX, 550, 100, 8))) {
        		ballYdir = -ballYdir;
        	}
        	
        	//iterate through every brick
         A:	for( int i = 0 ; i < map.map.length ; i++) {
        		for ( int j = 0 ; j < map.map[0].length ; j++) {
        			if (map.map[i][j] > 0) {
        				
        				//detecting intersection wit pos of ball & brick
        				int brickX = j * map.brickwidth + 80;
        				int brickY = i * map.brickheight + 50;
        				int brickwidth = map.brickwidth;
        				int brickheight = map.brickheight;
        				
        				//creating rectangle around brick
        				Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
        				Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
        				Rectangle brickRect = rect;
        				
        				//passsing referece to another rectangle
        				if (ballRect.intersects(brickRect)) {
        					map.setBricksValue(0,  i, j);
        					totalBricks--;
        					score += 5;
        					
        					//left and right intersection
        					if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
        						ballXDir = -ballXDir;
        					}
        					else {
        						ballYdir = -ballYdir;
        					}
        					break A;
        				}
        			}
        		}
        	}
        	
        	ballposX += ballXDir;
        	ballposY += ballYdir;
        	if(ballposX < 0) {
        		ballXDir = -ballXDir;
        	}
        	if(ballposY < 0) {
        		ballYdir = -ballYdir;
        	}
        	if(ballposX > 670) {
        		ballXDir = -ballXDir;
        	}
        }
        
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //detecting which arrow key is pressed
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if((playerX >= 600)) {
                playerX = 600;
            }
            else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if((playerX <= 10)) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }
        
        //event for restarting game
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        	if (!play) {
        		play = true;
        		ballposX = 120;
        		ballposY = 350;
        		ballXDir = n;
        		ballYdir = -2;
        		playerX = 310;
        		score = 0;
        		totalBricks = 21;
        		map = new MapGenerator(3, 7);
        		
        		repaint();
        	}
        }
        
    }

    //creating right and left methods
    public void moveRight() {
        play = true;
        playerX += 20;
    }
    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    
}