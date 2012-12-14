/**
 * 
 */
package lab4;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextArea;

/**
 * An algorithm for the third part.Is based on multiple attempts on a randomized
 * prioritization list as well as trying all possible counts of persons
 * ranging from the minimum number of persons to the maximum number of persons
 * needed for removing all top boxes each "step".
 * Returns the solution with the least number of steps with the least number of
 * persons required for that.
 * @author Viktor, Petter
 *
 */
public class Alg3 {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * The delay in MS for when the different solutions are being tried.
	 * Is used for smoother showing that it currently searches some solutions.
	 */
	private static int DELAY = 50;

	/**
	 * The base number of how many times one boxconfig and a set of persons
	 * will be randomized. By increasing this number the likelihood of the
	 * most optimized priorizationlist will appear and thus increases the
	 * chanses of finding the optimal solution.
	 */
	private static int BASELOOPNUMBER = 100;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * The box config to move.
	 */
	private BoxConfiguration boxconfig;

	/**
	 * Boolean for whether the algorithm is currently running or not.
	 */
	private boolean isRunning;

	/**
	 * The text area to print in
	 */
	private JTextArea mainText;

	/**
	 * The least number of steps to solve the problem as quick as possible.
	 */
	private int fastestSolution = 100000000;

	/**
	 * The best solution found.
	 */
	private Solution bestSolution;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


	/**
	 * Default constructor.
	 * @param mainText The area where text output should be put.
	 */
	public Alg3(JTextArea mainText) {
		this.mainText = mainText;
	}

	/**
	 * Starts the algorithm.
	 * @param boxconfig The box config to work with.
	 * @param persons The number of persons that should work. This has to be
	 * larger than the least heavy box.
	 */
	public void start(BoxConfiguration boxconfig) {
		this.boxconfig = boxconfig;
		if (boxconfig.boxes.size() > 0) {
			this.move();
		}
	}

	/**
	 * The actual "moving"-algorithm. 
	 */
	private void move() {
		isRunning = true;
		int minPersons =
				boxconfig.getMinPersons();
		int optimalPersons = 
				getOptimalPersons(boxconfig.boxes);
		ArrayList<Solution> solutions = new ArrayList<Solution>();

		String tempText = mainText.getText();
		AlgorithmPart2 alg2 = new AlgorithmPart2(mainText);
		alg2.setSilent(); /* Sets it to silent mode for running faster and  *
		 				   * stops it from printing in the mainText 		*/
		
		int countNumberOfTries = 0; /* For printing the total number of tries */
		for (int i = minPersons; i <= optimalPersons; i++) {
			try { /* Sleeps for making the search go slower for nicer viewing */
				Thread.sleep(DELAY);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			/* The main loop that tries a set of solutions based on a randomized
			 * prioritation list
			 */
			for (int j = 0; j <= BASELOOPNUMBER / boxconfig.boxes.size(); j++) {
				countNumberOfTries++;
				ArrayList<Box> randomList = randomiseList(copyBoxes(boxconfig.boxes));
				BoxConfiguration boxconfigRand = new BoxConfiguration(randomList);
				boxconfigRand.loadConnections();
				/* Creates a copy of the randomised boxconfig for saving the boxes list */
				BoxConfiguration temp = new BoxConfiguration(copyBoxes(randomList));
				temp.loadConnections();

				alg2.start(boxconfigRand, i); /* Runs algorithm 2 with the current box-  *
				 * config that has a randomized prio-list. */

				solutions.add(new Solution (temp, alg2.getSteps(), i)); /* Adds the solution */
				mainText.setText(tempText + 
						"Persons: " + i + ", random boxconfig: " + countNumberOfTries + "...\n");
			}
		}
		mainText.append(
				"Solutions tested: " + solutions.size() + "\n" +
						"Fastest solution found: " + fastestSolution + " steps.\n" + 
						bestSolution.presentSolution() + "\n");
		try {
			Thread.sleep(500); /* Wait half a second before printing solution */
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		alg2.setUnsilent();
		long t1 = System.nanoTime();
		alg2.start(bestSolution.getBoxConfig(), bestSolution.getPersons());
		long t2 = System.nanoTime();
		long timeForExecutionInMs = (t2 - t1) / 1000000;
		mainText.append("Best solution took :" + timeForExecutionInMs + "ms to execute.\n");
		isRunning = false;
	}

	/**
	 * Randomises the order of the boxes in the arraylist. Creates a new
	 * list with the same boxes in another order.
	 * @param boxes The list of boxes to randomise.
	 * @return The randomized list.
	 */
	private ArrayList<Box> randomiseList(ArrayList<Box> boxes) {
		ArrayList<Box> randomBoxes = new ArrayList<Box>();
		Random gen = new Random();
		ArrayList<Box> boxesCopy = copyBoxes(boxes);
		int size = boxesCopy.size();
		while (size > 0) { /* Randomly generates a number for moving to a new list */
			int ran = gen.nextInt(size--);
			randomBoxes.add(boxesCopy.remove(ran));
		}
		return randomBoxes;
	}

	/**
	 * Calculates the optimal persons for solving the boxconfig. I.e. the number
	 * for solving each set of top-boxes in one turn.
	 * @param boxes Set of boxes to calculate on.
	 * @return The optimal number of persons.
	 */
	private int getOptimalPersons(ArrayList<Box> boxes) {
		/* Makes a copy for saving the boxes, and loads its connections */
		BoxConfiguration boxconfTemp = new BoxConfiguration(copyBoxes(boxes));
		boxconfTemp.loadConnections(); 

		int optimalPersons = 0;
		int optimalPersonsTemp; /* Is reset for each lap */
		ArrayList<Box> topBoxes = new ArrayList<Box>();

		while(boxconfTemp.boxes.size() > 0) { /* Loop until boxesTemp is empty. */
			optimalPersonsTemp = 0;
			for (Box box : boxconfTemp.boxes) {
				if (box.isTopBox()) {
					topBoxes.add(box);
					optimalPersonsTemp += box.getWeight(); /* Add weight. */
				}
			}
			for (Box box : topBoxes) { /* Remove the top boxes. */
				boxconfTemp.remove(box);
			}
			topBoxes.clear(); /* Removes all current top boxes */
			if (optimalPersonsTemp > optimalPersons) { /* Updates the optimal persons */
				optimalPersons = optimalPersonsTemp;
			}
		}
		return optimalPersons;
	}

	/**
	 * Makes a copy of an arraylist of boxes.
	 * @param boxes The list to copy
	 * @return The copied list
	 */
	private ArrayList<Box> copyBoxes(ArrayList<Box> boxes) {
		ArrayList<Box> temp = new ArrayList<Box>(); /* The new list */
		for (Box box : boxes) {
			temp.add(new Box(box.getName(), box.getWeight())); /* Copies all boxes */
		}
		return temp;	
	}

	/**
	 * @return True if it's currently running.
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * A private class for the different solutions in this algorithm.
	 * Is used for later choosing the most cost efficient according to
	 * some user inputted criterias.
	 * @author Viktor, Petter
	 *
	 */
	private class Solution {
		private BoxConfiguration boxconfigSol;
		private int stepsToSolve;
		private int persons;

		/**
		 * Constructor for the private class
		 * @param boxconfig The box config for this solution.
		 * @param steps The steps required for this solution.
		 * @param persons The number of persons used.
		 */
		public Solution(BoxConfiguration boxconfig, int steps, int persons) {
			this.boxconfigSol = boxconfig;
			this.stepsToSolve = steps;
			this.persons = persons;
			if (this.stepsToSolve < fastestSolution) {
				fastestSolution = this.stepsToSolve;
				bestSolution = this;
			}
		}

		/**
		 * @return The box config.
		 */
		public BoxConfiguration getBoxConfig() {
			return this.boxconfigSol;
		}

		/**
		 * @return The number of steps to solve the box config.
		 */
		public int getSteps() {
			return this.stepsToSolve;
		}

		/**
		 * @return The number of persons used to solve the box config.
		 */
		public int getPersons() {
			return this.persons;
		}


		/**
		 * Creates a string with all the information of the solution.
		 * @return A string with all the boxes in the solution, as well as
		 * the steps and persons required.
		 */
		public String presentSolution() {
			String str = "";
			if (this.equals(bestSolution)) {
				str += "Best solution: ";
			} else {
				str += "Solution: " ;
			}
			for (Box box : this.boxconfigSol.boxes) {
				str += box.getName() + ", ";
			}
			str += "Steps: " + this.stepsToSolve + ", Persons: " + this.persons + ".";
			return str;
		}
	}

}
