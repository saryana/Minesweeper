import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class MSBoard extends Panel {
	protected static int DEFAULT_WIDTH, DEFAULT_HEIGHT;
	protected int boxWid, boxHei, dim;
	protected int mX, mY;
	protected static int bottomBar = 80;
	protected static int sideBar = 100;
	protected Stroke line = new BasicStroke(2);
	protected int[][] grid;
	protected boolean[][] clicked, flags;
	protected String status = "Status:";
	protected String current = "Still Going";
	protected String underMessage = "Left Click to open mine, 'Space' to flag mine, 'R' to restart";
	protected String bombs  = "Bombs:";
	protected String bombsLeft = "";
	protected long startTime, runningTime;
	
	public MSBoard(int dim) {
		this(dim*20, 20*dim, dim);
	}
	
	public MSBoard(int width, int height, int dim) {
		super(width+sideBar, height+bottomBar);
		DEFAULT_WIDTH = width;
		DEFAULT_HEIGHT = height;
		boxWid = width / dim;
		boxHei = height / dim;
		this.dim = dim;
		grid = new int[dim][dim];
		clicked = new boolean[dim][dim];
		flags = new boolean[dim][dim];
		startTime = System.currentTimeMillis();
		runningTime = 0;
	}
	
	public void update() {
		setMouse();
		drawGrid();
	}

	private void drawGrid() {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		g.setColor(Color.cyan);
		g.fillRect(mX*boxWid+2, mY*boxHei+2, boxWid-4, boxHei-4);
		g.setColor(Color.BLUE);
		
		
		((Graphics2D) this.g).setStroke(line);
		for(int i = 0; i <= DEFAULT_WIDTH; i += boxWid) {
			g.drawLine(i, 0, i, DEFAULT_HEIGHT);
		}
		
		
		for(int i = 0; i <= DEFAULT_HEIGHT; i += boxHei) {
			g.drawLine(0, i, DEFAULT_WIDTH, i);
		}
		g.setColor(Color.MAGENTA);
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				drawElement(x, y);
			}
		}
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(DEFAULT_WIDTH, 0, DEFAULT_WIDTH+sideBar, DEFAULT_HEIGHT+bottomBar);
		g.fillRect(0, DEFAULT_HEIGHT, DEFAULT_WIDTH+sideBar, DEFAULT_HEIGHT+bottomBar);
		if(dim < 15) {
			g.setFont(new Font(null,10,10));
		} else {
			g.setFont(new Font(null,15,15));
		}
		g.setColor(Color.white);
		g.drawString(underMessage, 10, DEFAULT_HEIGHT+10);
		g.drawString(status, DEFAULT_WIDTH +10, 50);
		g.drawString(current, DEFAULT_WIDTH +10, 70);
		g.drawString(bombs, DEFAULT_WIDTH +10, 90);
		g.drawString(bombsLeft, DEFAULT_WIDTH +10, 110);
		g.drawString("Time: " + getTime(runningTime-startTime), DEFAULT_WIDTH +10, 150);
	}
	
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


	private void setMouse() {
		if((this.getMouseX() > 0 && this.getMouseX() < DEFAULT_WIDTH) &&
				(this.getMouseY() > 0 && this.getMouseY() < DEFAULT_HEIGHT)) {
			mX = this.getMouseX() / 20;
			mY = this.getMouseY() / 20;
		}
	}
	
	protected void drawElement(int x, int y) {}
}