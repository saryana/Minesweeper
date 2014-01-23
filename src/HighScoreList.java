/**
 * Class handles the saving, loading and creation of a high score list
 * be sure to throw an IOException in any methods that you save or create 
 * a high score list
 */

import java.util.*;
import java.io.*;


public class HighScoreList {
	private PriorityQueue<ScoreNode> hsQueue = new PriorityQueue<ScoreNode>();
	private String fileName;
	private boolean bigToSmall;

	/**
	 * @param nameOfFile is the file name that the high score list is loaded from and saved to.
	 * @throws IOException
	 */
	public HighScoreList(String nameOfFile, boolean bigBest) {
		fileName = nameOfFile;
		bigToSmall = bigBest;
		File inoutput = new File(fileName);
		if (inoutput.exists()) {
			try {
				load(new Scanner(inoutput));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param name name of the player
	 * @param score the player's high score.
	 */
	public void add(String name, int score) {
		hsQueue.add(new ScoreNode(name, score));
	}

	/**
	 * @param place the place of the score on the high score list to be removed.
	 */
	public void remove(int place) {
		getNode(place, true);
	}

	/**
	 * @param place the place on the highscore list to retrieve the player's name from.
	 * @return returns the name of the player at the given place
	 */
	public String getName(int place) {
		return getNode(place, false).name;
	}

	/**
	 * @param place the place on the highscore list to retrieve the score from.
	 * @return returns the score from the given place on the highscore list.
	 */
	public int getScore(int place) {
		return getNode(place, false).score;
	}

	/**
	 * @return returns a string of all the highscores in order from top to bottom with 
	 * the player's names and scores separated by two colons.
	 */
	public String toString() {
		return toString(-1);
	}
	
	/**
	 * @param plac the place up to which this method returns all the high scores on the list.
	 * @return returns a string of all the highscores in order from top to bottom with 
	 * the player's names and scores separated by two colons, up to a given place.
	 */
	public String toString(int plac) {
		String tempStr = "";
		ScoreNode tempN;
		int place = 1;
		Set<ScoreNode> tempS = new HashSet<ScoreNode>();
		while (!hsQueue.isEmpty() && (plac >= place || plac == -1)) {
			tempN = hsQueue.remove();
			int time = tempN.score;
			tempStr += place + ") " + tempN.name + " :: ";
			if(time < 10) {
				tempStr += "00:0" + time;
			} else if (time < 60) {
				tempStr += "00:" + time;
			} else if (time < 9*60+59) {
				tempStr += "0" + (time /60) + ":" + (time % 60);
			} else {
				tempStr += (time/60) +":" + (time%60);
			}
			tempStr += "\n";
			tempS.add(tempN);
			place++;
		}
		hsQueue.addAll(tempS);
		return tempStr;
	}

	/**
	 * @param place the place of the node that is being returned
	 * @param removeNode whether or not the node is being removed from the high score list.
	 * @return returns the node at the given place.
	 */
	private ScoreNode getNode(int place, boolean removeNode) {
		Set<ScoreNode> tempS = new HashSet<ScoreNode>();
		ScoreNode tempN;
		for (int i = 1; i < place; i++) {
			tempS.add(hsQueue.remove());
		}
		tempN = hsQueue.remove();
		if (!removeNode)
			hsQueue.add(tempN);
		hsQueue.addAll(tempS);
		return tempN;
	}

	/**
	 * @return returns the size of the high score list
	 */
	public int size() {
		return hsQueue.size();
	}
	/**
	 * saves the current high score list to the file given in the constructor.
	 * @throws IOException
	 */
	public void save() {
		PrintStream pS;
		try {
			pS = new PrintStream(fileName);
			Scanner s = new Scanner(toString());
			while (s.hasNextLine()) {
				pS.println(s.nextLine());
			}
			s.close();
			pS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param s a scanner of the file given in the constructor that is used to 
	 * rebuild the high score list.
	 */
	public void load(Scanner s) {
		String tName;
		String crnt;
		int tScore;
		while (s.hasNextLine()) {
			tName = "";
			String newLine = s.nextLine();
			Scanner tLine = new Scanner(newLine);
			tLine.next();
			crnt = tLine.next();
			while (!crnt.equals("::")) {
				tName += crnt + " ";
				crnt = tLine.next();
			}
			String number = tLine.next();
			String[] time  = number.split(":");
			int minutes = Integer.parseInt(time[0]);
			int seconds = Integer.parseInt(time[1]);
			tScore = minutes * 60 + seconds;
			tLine.close();
			add(tName, tScore);
		}
	}

	/**
	 * Inner class dont worry about it
	 */
	private class ScoreNode implements Comparable<ScoreNode> {
		public int score;
		public String name;

		public ScoreNode(String name, int score) {
			this.score = score;
			this.name = name;
		}

		public int compareTo(ScoreNode other) {
			if (score != other.score) {
				if (bigToSmall) {
					return other.score - score;
				} else {
					return score - other.score;
				}
			} else {
				return name.compareTo(other.name);
			}
		}
	}
}
