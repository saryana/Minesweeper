/**
 * @author Sean A.
 */
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Random;

public class MSPanel extends MSBoard {
	private static int Hpad = -3, Vpad = 6;
	private static List<Color> col = new ArrayList<Color>(Arrays.asList(Color.blue, Color.green,
			Color.orange, Color.red, Color.pink, Color.black));
	private int bombs;
	private int totalBombs;


	public MSPanel(int size, int bomb) {
		super(size);
		bombs = 0;
		totalBombs = bomb;
		getBombs();
		getOthers();
	}

	/**
	 * Goes through the board counting the amount of bombs a space touches
	 */
	private void getOthers() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int count = 0;
				if (grid[i][j] != -1) {
					if (i > 0) {
						if (grid[i - 1][j] == -1) {
							count++;
						}
						if (j > 0 && grid[i - 1][j - 1] == -1) {
							count++;
						}
						if (j < grid.length - 1 && grid[i - 1][j + 1] == -1) {
							count++;
						}
					}
					if (i < grid.length - 1) {
						if (grid[i + 1][j] == -1) {
							count++;
						}
						if (j > 0 && grid[i + 1][j - 1] == -1) {
							count++;
						}
						if (j < grid.length - 1 && grid[i + 1][j + 1] == -1) {
							count++;
						}
					}
					if (j > 0 && grid[i][j - 1] == -1) {
						count++;
					}
					if (j < grid.length - 1 && grid[i][j + 1] == -1) {
						count++;
					}
					grid[i][j] = count;
				}
			}
		}
	}
	
	/**
	 * Randomly assigns bombs to 
	 */
	private void getBombs() {
		Random rand = new Random();
		bombs = 0;
		int bombsToPut = totalBombs;
		do {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (rand.nextInt(15) < 3) {
						grid[i][j] = -1;
						bombs++;
						bombsToPut--;
						if (bombsToPut == 0) {
							break;
						}
					}
				}
				if (bombsToPut == 0) {
					break;
				}
			}
		} while (bombsToPut != 0);
		bombsLeft = "" + bombs;
	}


	public void update() {
		super.update();
		runningTime = System.currentTimeMillis();
		if (this.isKeyDown(KeyEvent.VK_R)) {
			reset();
		}
		check();
	}

	private void check() {
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == -1 && flags[i][j]) {
					count++;
				}
			}
		}
		if (count == bombs) {
			current = "You Win";
		}
		bombsLeft = "" +(bombs- count);
	}



	protected void drawElement(int x, int y) {
		g.setColor(Color.yellow);
		if (this.mouseDown) {
			if (grid[mX][mY] == -1) {
				current = "Game Over";
			//	grid[mX][mY] = 0;
				reset();
			} else if (flags[mX][mY]) {
					flags[mX][mY] = false;
			} else if (grid[mX][mY] == 0) {
					openRest(mX, mY);
					clicked[mX][mY] = true;
			} else if (grid[mX][mY] <9) {
				clicked[mX][mY] = true;
			}
		} else if (this.isKeyDown(KeyEvent.VK_SPACE) && !clicked[mX][mY]) {
			flags[mX][mY] = true;
		}
		g.setColor(Color.black);
		if (flags[x][y]) {
			g.setColor(Color.yellow);
			g.fillRect(x * boxWid, y * boxHei, boxWid, boxHei);
			g.setColor(Color.black);
			g.drawString("F", x * (boxWid) + boxWid / 2, y
					* (boxHei) + boxHei / 2 + Vpad);			
		} else if (clicked[x][y] && grid[x][y] == 0) {
			g.setColor(Color.gray);
			g.draw3DRect(x * boxWid+2, y * boxHei+2, boxWid-4, boxHei-4, true);
			g.fillRect(x * boxWid + 2, y * boxHei + 2, boxWid - 4, boxHei - 4);
		} else if (clicked[x][y] && grid[x][y] < 9) {
			int num = grid[x][y];
			draw("" + num, x, y, num);
		}
	}
	
	private void draw(String draw, int x, int y, int num) {
		g.setColor(Color.gray);
		g.fillRect(x * boxWid + 2, y * boxHei + 2, boxWid - 4, boxHei - 4);
		if (num < 6) {
			g.setColor(col.get(num-1));
			
		} else {
			g.setColor(col.get(col.size()-1));
		}
		g.drawString(draw, x * boxWid + boxWid/2 + Hpad, y*boxHei+boxHei/2+Vpad);
	}

	private void reset() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = 0;
				flags[i][j] = false;
				clicked[i][j] = false;
			}
		}
		getBombs();
		getOthers();
		startTime = System.currentTimeMillis();
	}

	/**
	 * If 0 is pressed it will open all other 0's until it hits numbers
	 * 
	 * @param x
	 *            - X position on grid
	 * @param y
	 *            - Y position on grid
	 */
	private void openRest(int x, int y) {
		if (x < dim && x >= 0) {
			if (y < dim && y >= 0) {
				if (!clicked[x][y]) {
					clicked[x][y] = true;
					if (grid[x][y] == 0) {
						openRest(x + 1, y);
						openRest(x + 1, y + 1);
						openRest(x + 1, y - 1);
						openRest(x - 1, y);
						openRest(x - 1, y + 1);
						openRest(x - 1, y - 1);
						openRest(x, y - 1);
						openRest(x, y + 1);
					}
				}
			}
		}
	}
}
