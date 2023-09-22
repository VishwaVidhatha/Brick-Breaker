package BrickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	//creating bricks
	public int map [][];
	public int brickwidth;
	public int brickheight;
	public MapGenerator (int row, int col) {
		map = new int[row][col];
		//detecting if ball touched any brick
		for (int i = 0 ; i < map.length ; i++) {
			for (int j = 0 ; j < map[0].length ; j++) {
				map[i][j] = 1;
			}
		}
		brickwidth = 540/col;
		brickheight = 150/row;
	}
	
	//drawing the bricks using graphics
	public void draw (Graphics2D g) {
		for (int i = 0 ; i < map.length ; i++) {
			for (int j = 0 ; j < map[0].length ; j++) {
				//creating brick if value != 0
				if(map[i][j] > 0) {
					g.setColor(Color.white);
					g.fillRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
					
					//creating strokes for bricks
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickwidth + 80, i * brickheight + 50, brickwidth, brickheight);
					
				}
			}
		}
	}
	//creating function to break bricks
	public void setBricksValue (int value, int row, int col) {
		map[row][col] = value;
	}
	
}