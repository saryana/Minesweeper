/**
 * @author Sean A
 * Gets information for the Minesweeper game like field size and amount of bombs
 */

import javax.swing.JOptionPane;

public class MS {
	public static void main(String[] args) {
		int n = JOptionPane.showConfirmDialog(null, "Want to play Minesweeper?");
		if(n == JOptionPane.OK_OPTION) {
			int size = Integer.parseInt(JOptionPane.showInputDialog("Size of board? (10-30) Note: Can go bigger if you want"));
			// Easy = 10% Medium = 50% Hard = 75%
			int sq = size * size;
			int bomb = Integer.parseInt(JOptionPane.showInputDialog("How many bombs? Easy: " + (sq / 10) + 
					" Medium: " + (sq / 2) + " Hard: " + (sq * 3 / 4)));
			if( bomb < size*size) {
				new MSPanel(size, bomb);
			} else {
				JOptionPane.showMessageDialog(null, "Too many bombs dumbass");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Fuck off then");
		}
	}

}