import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.JOptionPane;

public class MSPanel extends MSBoard {
	private static int Hpad = -3, Vpad = 6; // Horizontal and vertical adjustment
	protected boolean done;
	protected MSPanel ms;
	private int totalBombs;
	private List<Color> col = new ArrayList<Color>(Arrays.asList(Color.blue, Color.green,
			Color.orange, Color.red, Color.pink, Color.black));

	/**
	 * @param size - Size of the board
	 * @param bomb - Number of bombs user wants to play with
	 */
	public MSPanel(int size, int bomb) {
		super(size);
		totalBombs = bomb;
		done = false;
		getBombs();
		getOthers();
	}

	/**
	 * Sets running time to the current time
	 * as well as checks to see if game is reset
	 */
	public void update() {
		runningTime = System.currentTimeMillis();
		if (this.isKeyDown(KeyEvent.VK_R)) {
			reset();
		}
		check();
		super.update();
	}

	/**
	 * Sees if the bombs match the flags
	 */
	private void check() {
		int count = 0;
		int flagCount = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (flags[i][j]) {
					flagCount++;
				}
				if (grid[i][j] == -1 && flags[i][j]) {
					count++;
				}
			}
		}
		if (totalBombs > 0 && count == totalBombs && flagCount == totalBombs) {
			String newName = JOptionPane.showInputDialog("Congrats motherfucker you won. what's you're name?");
			hScores.add(newName, (int)(long)((runningTime - startTime)/1000));
			current = "You Win";
			hScores.save();
			top3 = hScores.toString(3);
			if (JOptionPane.showConfirmDialog(null, "want to play again?") == JOptionPane.OK_OPTION) {
				reset();
			} else {
				JOptionPane.showMessageDialog(null, "Well too bad idk how to make this program quit lol.");
				exit();
			}
			done = true;
		} else if (flagCount >= totalBombs) { 
			current = "Too many bombs";
		} else {
			current = "Still going";
		}
		bombsLeft = "" +(totalBombs- flagCount);
	}

	/**
	 * This checks above below the sides and the corners to see if it is touching a bomb
	 * depending on how may it touches, the count goes up and places that number there
	 */
	private void getOthers() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int count = 0;
				if (grid[i][j] != -1) {
					count = checkSur(i, j);
					grid[i][j] = count;
				}
			}
		}
	}

	private int checkSur(int i, int j) {
		int count = 0;
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
		return count;
	}

	private int checkSurB(int i, int j) {
		int count = 0;
		if (i > 0) {
			if (grid[i - 1][j] == -1 && flags[i - 1][j]) {
				count++;
			} else if (flags[i - 1][j]) {
				count = -100;
			}
			if (j > 0) {
				if (grid[i - 1][j - 1] == -1 && flags[i - 1][j - 1]) {
					count++;
				} else if (flags[i - 1][j - 1]) {
					count = -100;
				}
			}
			if (j < grid.length - 1) {
				if (grid[i - 1][j + 1] == -1 && flags[i - 1][j + 1]) {
					count++;
				} else if (flags[i - 1][j + 1]) {
					count = -100;
				}
			}
		}
		if (i < grid.length - 1) {
			if (grid[i + 1][j] == -1 && flags[i + 1][j]) {
				count++;
			} else if (flags[i + 1][j]) {
				count = -100;
			}
			if (j > 0) {
				if (grid[i + 1][j - 1] == -1 && flags[i + 1][j - 1]) {

					count++;
				} else if (flags[i + 1][j - 1]) {
					count = -100;
				}
			}
			if (j < grid.length - 1) {
				if (grid[i + 1][j + 1] == -1 && flags[i + 1][j + 1]) {
					count++;
				} else if (flags[i + 1][j + 1]) {
					count = -100;
				}
			}
		}
		if (j > 0) {
			if (grid[i][j - 1] == -1 && flags[i][j - 1]) {
				count++;
			} else if (flags[i][j - 1]) {
				count = -100;
			}
		}
		if (j < grid.length - 1) {
			if (grid[i][j + 1] == -1 && flags[i][j + 1]) {
				count++;
			} else if (flags[i][j + 1]) {
				count = -100;
			}
		}
		return count;
	}


	/**
	 * Depending on the number of bombs the user wants,
	 * it randomly places the bomb somewhere on the grid
	 * NEED TO FIX ALOGRITHM
	 */
	private void getBombs() {
		Random rand = new Random();
		int bombsToPut = totalBombs;
		do {
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (rand.nextInt(100) < 10) {
						grid[i][j] = -1;
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
		bombsLeft = "" + totalBombs;
	}
	
	public void action() {
		if (gridXposition < grid.length && gridYposition < grid.length
				&& gridXposition >= 0 && gridYposition >= 0) {
			if (this.mouseDown) {
				mouseAction();
			} else if (this.isKeyDown(KeyEvent.VK_SPACE)
					&& !clicked[gridXposition][gridYposition]) {
				flags[gridXposition][gridYposition] = !flags[gridXposition][gridYposition];
			}
		} else if(gridXposition > grid.length  && gridYposition == grid.length-1 &&
				gridXposition <= grid.length + 3 ) {
			reset();
		} else if(gridXposition > grid.length  && gridYposition == grid.length-2 &&
				gridXposition <= grid.length + 3 ) {
			changeSettings();
		}
	}
	/**
	 * If clicked on bomb then reset game, if flag remove it, if 0 open all
	 * Surroundings, if number mark as clicked.
	 */
	private void mouseAction() {
		if (grid[gridXposition][gridYposition] == -1) {
			current = "Game Over";
			JOptionPane.showMessageDialog(null, "You suck");
			reset();
		} else if (flags[gridXposition][gridYposition]) {
			flags[gridXposition][gridYposition] = false;
		} else if (grid[gridXposition][gridYposition] == 0) {
			openRest(gridXposition, gridYposition, 0);
			clicked[gridXposition][gridYposition] = true;
		} else if (!clicked[gridXposition][gridYposition] &&
				grid[gridXposition][gridYposition] < 9) {
			clicked[gridXposition][gridYposition] = true;
		} else if(clicked[gridXposition][gridYposition]) {
			open();
		}
	}
	private void open() {
		if (doneDuty()) {
			open(gridXposition, gridYposition, 9);
		}
	}

	private boolean doneDuty() {
		int tar = grid[gridXposition][gridYposition];
		int count = checkSurB(gridXposition, gridYposition);
		if(count < 0) {
			current = "Game Over";
			JOptionPane.showMessageDialog(null, "You suck");
			reset();
		}
		return count == tar;
	}

	/**
	 * Test to see if user wants flag or if user dies
	 * then it fills all the squares
	 */
	protected void drawElement(int x, int y) {
		g.setColor(Color.black);
		if (flags[x][y]) {
			flagAction(x, y);
		} else if (clicked[x][y] && grid[x][y] == 0) {
			g.setColor(Color.gray);
			g.draw3DRect(x * boxWid + 2, y * boxHei + 2, boxWid - 4,
					boxHei - 4, true);
			g.fillRect(x * boxWid + 2, y * boxHei + 2, boxWid - 4, boxHei - 4);
		} else if (clicked[x][y] && grid[x][y] < 9) {
			int num = grid[x][y];
			draw(x, y, num);
		}
	}

	/**
	 * Draws yellow box to stand out and an 'F' for flag
	 * @param x - Current x position
	 * @param y - Current Y position
	 */
	private void flagAction(int x, int y) {
			g.setColor(Color.yellow);
			g.fillRect(x * boxWid, y * boxHei, boxWid, boxHei);
			g.setColor(Color.black);
			g.drawString("F", x * (boxWid) + boxWid / 2, y
					* (boxHei) + boxHei / 2 + Vpad);			
	}

	

	/**
	 * Makes a box along with the number inside
	 * @param x - Current x position
	 * @param y - Current y position
	 * @param num - Number it is on
	 */
	private void draw(int x, int y, int num) {
		g.setColor(Color.gray);
		g.fillRect(x * boxWid + 2, y * boxHei + 2, boxWid - 4, boxHei - 4);
		if (num < 6 && num > 0) {
			g.setColor(col.get(num-1));
		} else {
			g.setColor(col.get(col.size()-1));
		}
		g.drawString(""+num, x * boxWid + boxWid/2 + Hpad, y*boxHei+boxHei/2+Vpad);
	}

	/**
	 * Prompts the user for different board size and bomb count
	 */
	private void changeSettings() {
		super.dim = Integer.parseInt(JOptionPane.showInputDialog("How size of board? (0-30) BUG: Can't change size from 0-15 then 15+"));
		totalBombs = Integer.parseInt(JOptionPane.showInputDialog("How many bombs? (More the harder)"));
		DEFAULT_WIDTH = dim*20;
		DEFAULT_HEIGHT = dim*20;
		setWidth(DEFAULT_WIDTH + sideBar);
		setHeight(DEFAULT_HEIGHT+bottomBar);
		g = super.getGraphics();
		reset();

	}
	/**
	 * Resets the game. Something doesn't quite work right because it is 
	 * restarting and opening the tile clicked on;
	 */
	private void reset() {
		grid = new int[dim][dim];
		flags = new boolean[dim][dim];
		clicked = new boolean[dim][dim];
		//ms = new MSPanel(dim, totalBombs);
		getBombs();
		getOthers();
		// Changes start time so clock will go to zero.
		startTime = System.currentTimeMillis();
	}

	/**
	 * If 0 is pressed it will open all other 0's until it hits numbers
	 * @param x - X position on grid
	 * @param y - Y position on grid
	 */
	private void openRest(int x, int y, int num) {
		if (x < dim && x >= 0) {
			if (y < dim && y >= 0) {
				if (!clicked[x][y]) {
					clicked[x][y] = true;
					if (grid[x][y] <= num) {
						if(num == 0 || grid[x][y] == 0){
							openRest(x + 1, y, num);
							openRest(x + 1, y + 1, num);
							openRest(x + 1, y - 1, num);
							openRest(x - 1, y, num);
							openRest(x - 1, y + 1, num);
							openRest(x - 1, y - 1, num);
							openRest(x, y - 1, num);
							openRest(x, y + 1, num);
						}							
					}
				}
			}
		}
	}
	
	private void open(int x, int y, int num) {
		openRest(x + 1, y, num);
		openRest(x + 1, y + 1, num);
		openRest(x + 1, y - 1, num);
		openRest(x - 1, y, num);
		openRest(x - 1, y + 1, num);
		openRest(x - 1, y - 1, num);
		openRest(x, y - 1, num);
		openRest(x, y + 1, num);
	}
}
