/**
 * @author Sean A
 * Gets information for the Minesweeper game like field size and amount of bombs
 */

import java.io.IOException;

import javax.swing.JOptionPane;

public class MS {
	public static void main(String[] args) throws IOException {
		int n  = JOptionPane.showConfirmDialog(null, "Want to play Minesweeper?");
		if(n == JOptionPane.OK_OPTION) {
			int size = Integer.parseInt(JOptionPane.showInputDialog("Size of board? (10-30) Note: Can go bigger if you want"));
			// Easy = 10% Medium = 50% Hard = 75%
			int bomb = Integer.parseInt(JOptionPane.showInputDialog("How many bombs? Easy: " + (size / 10) + 
					" Medium: " + (size / 2) + " Hard: " + (size * 3 / 4)));
			if(bomb < size * size) {
				new MSPanel(size, bomb);
			} else {
				JOptionPane.showMessageDialog(null, "Too many bombs sorry");
			}
		} else {
			JOptionPane.showMessageDialog(null, "This was a waste of time");
		}
	}

}
