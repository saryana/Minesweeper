import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class MSBoard extends Panel {
	protected static int DEFAULT_WIDTH, DEFAULT_HEIGHT;
	protected int boxWid, boxHei, dim;
	protected int gridXposition, gridYposition;
	protected static int bottomBar = 80;
	protected static int sideBar = 120;
	protected Stroke line = new BasicStroke(2);
	protected int[][] grid;
	protected boolean[][] clicked, flags;
	protected String current = "Still Going";
	protected String bombsLeft = "";
	protected long startTime, runningTime;
	protected HighScoreList hScores;
	protected String top3;
	
	
	public MSBoard(int dim) {
		this(dim*20, 20*dim, dim);
		hScores = new HighScoreList("minesweeperHighscores.txt", false);
		top3 = hScores.toString(3);
	}
	
	public void clear() {
		g.setColor(Color.white);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public MSBoard(int width, int height, int size) {
		super(width+sideBar, height+bottomBar);
		DEFAULT_WIDTH = width;
		DEFAULT_HEIGHT = height;
		boxWid = width / size;
		boxHei = height / size;
		this.dim = size;
		grid = new int[size][size];
		clicked = new boolean[size][size];
		flags = new boolean[size][size];
		startTime = System.currentTimeMillis();
		runningTime = 0;
		hScores = new HighScoreList("minesweeperHighscores.txt", false);
		top3 = hScores.toString(3);
	}

	private void drawLines() {
		g.setColor(Color.black);
		//Draws vertical and horizontal Lines
		((Graphics2D) this.g).setStroke(line);
		for(int i = 0; i <= DEFAULT_WIDTH; i += boxWid) {
			g.drawLine(i, 0, i, DEFAULT_HEIGHT);
		}
		for(int i = 0; i <= DEFAULT_HEIGHT; i += boxHei) {
			g.drawLine(0, i, DEFAULT_WIDTH, i);
		}		
		
	}

	/**
	 * Updates every 20ms
	 */
	public void update() {
		setMouse();
		drawGrid();
	}
	
	/**
	 * X and Y position depending on the grid not the overall position
	 */
	private void setMouse() {
		if (this.getMouseX() == 0 || this.getMouseY() == 0) {
			gridXposition = -1;
			gridYposition = -1;
		} else {
			gridXposition = this.getMouseX() / 20;
			gridYposition = this.getMouseY() / 20;
		}
	}

	/**
	 * Draws the lines, highlights the box, and draw's each box's information
	 */
	private void drawGrid() {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		//Side and bottom bar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(DEFAULT_WIDTH, 0, DEFAULT_WIDTH+sideBar, DEFAULT_HEIGHT+bottomBar);
		g.fillRect(0, DEFAULT_HEIGHT, DEFAULT_WIDTH+sideBar, DEFAULT_HEIGHT+bottomBar);
		if(dim < 15) {
			g.setFont(new Font(null,10,10));
		} else {
			g.setFont(new Font(null,15,15));
		}	
		drawShit();
		g.setColor(Color.cyan);
		// Highlights current box position
		if(gridXposition < grid.length && gridYposition < grid.length 
				&& gridXposition >= 0 && gridYposition >= 0) {
			g.fillRect(gridXposition*boxWid, gridYposition*boxHei, boxWid, boxHei);
		} else if(gridXposition > grid.length  && gridYposition == grid.length-1 &&
				gridXposition <= grid.length + 3 ) {
			g.drawRect((grid.length+1)*boxWid, gridYposition*boxHei, 3*boxWid, boxHei);			
		} else if(gridXposition > grid.length  && gridYposition == grid.length-2 &&
				gridXposition <= grid.length + 3 ) {
			g.drawRect((grid.length+1)*boxWid, (gridYposition)*boxHei, 4*boxWid, boxHei);
		}
		
		drawLines();

		// Goes through every tile and see what needs to be drawn
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				drawElement(x, y);
			}
		}
	

	}
	
	/**
	 * Shit that need to be drawn like the time and the bombs left
	 */
	private void drawShit() {
		g.setColor(Color.white);
		g.drawString(
				"Left Click to open mine, 'Space' to flag mine, 'R' to restart",
				10, DEFAULT_HEIGHT + 20);
		g.drawString("Status:", DEFAULT_WIDTH + boxWid, 50);
		g.drawString(current, DEFAULT_WIDTH + boxWid, 70);
		g.drawString("Bombs Left: " , DEFAULT_WIDTH + boxWid, 90);
		g.drawString(bombsLeft, DEFAULT_WIDTH + boxWid, 110);
		g.drawString("Time: " + getTime(runningTime - startTime),
				DEFAULT_WIDTH + boxWid, 150);
		g.drawString("Top:" + top3.replaceAll("\n", " "), 10, DEFAULT_HEIGHT + 40);
		g.drawString("Change settings", DEFAULT_WIDTH + boxWid +1, DEFAULT_HEIGHT-3*(boxWid/2));
		g.drawString("Play Again", DEFAULT_WIDTH + boxWid + 1, DEFAULT_HEIGHT-boxWid/3);
	}

	/**
	 * Gets the time
	 * @param time - Running time minus start time
	 * @return Properly formats the time
	 */
	private String getTime(long time) {
		time /= 1000;
		long seconds = time %60;
		long minutes = time /60;
		if(seconds < 10 && minutes < 10) {
			return "0" + minutes + ":0" + seconds;
		} else if (minutes < 10) {
			return "0" + minutes + ":" + seconds;
		} else if (seconds < 10) {
			return  minutes + ":0" + seconds;
		} else {
			return minutes + ":" + seconds;
		}
	}
	
	/**
	 * Override
	 * @param x - Position
	 * @param y - Position
	 */
	protected void drawElement(int x, int y) {}
	protected void actoin(){}
}