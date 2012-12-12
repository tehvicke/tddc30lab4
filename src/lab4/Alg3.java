/**
 * 
 */
package lab4;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextArea;

/**
 * An algorithm for the second part. Takes number of workers and adapts
 * the boxing to that. 
 * @author Viktor, Petter
 *
 */
public class Alg3 {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	private int personsMax; // The number of persons 
	private BoxConfiguration boxconfig;
	private boolean isRunning = true;
	private JTextArea mainText;
	private int fastestSolution = 100000000;
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
		this.move();
	}
	
	/**
	 * The actual "moving"-algorithm.
	 */
	private void move() {
		(new Thread() {
			public void run() {
				isRunning = true;
				int minPersons =
						boxconfig.getMinPersons(); /* Get min number of persons *
													* required.					*/
				int optimalPersons = 
						getOptimalPersons(boxconfig.boxes); /* Gets the optimal number *
															 * to solve the problem    */
				ArrayList<Solution> solutions = new ArrayList<Solution>(); // Solutions
				
//				ArrayList<Box> randomList = randomiseList(boxconfig.boxes);
				int countTemp = 0;
				/* Try each number of "possible "persons mixes. */
				String tempText = mainText.getText();
				for (int i = minPersons; i <= optimalPersons; i++) {
						
						for (int j = 0; j < 5; j++) {
							countTemp++;
							AlgorithmPart2 alg2 = new AlgorithmPart2(mainText);
							alg2.setSilent();
							ArrayList<Box> randomList = randomiseList(boxconfig.boxes); // Denna ska loopas många gånger.
							BoxConfiguration boxconfigRand = new BoxConfiguration(randomList);
							BoxConfiguration temp = new BoxConfiguration(randomList);
							alg2.start(boxconfigRand, i); /* Runs algorithm 2 with the current box-  *
														   * config that has a randomized prio-list. */
							while(alg2.isRunning()) {     /* Wait for each algorithm to finish before*/
								try {				      /* fetching data							 */
									Thread.sleep(10);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							solutions.add(new Solution (temp, alg2.getSteps(), i)); // Adds the solution.
							mainText.setText(tempText + "Persons: " + i + ", random boxconfig: " + countTemp + "...\n");
						}
				}
				int count = 0;
				for (Solution sol : solutions) {
					String str = "Solution " + Integer.toString(++count) + " ";
					for (Box box : sol.getBoxConfig().boxes) {
						str += box.getName() + ", ";
					}
					str += "Steps: " + sol.getSteps() + ", Persons: " + sol.getPersons() + ".\n";
//					mainText.append(str);
				}
				mainText.append(
						"Solutions tested: " + solutions.size() + "\n" +
						"Fastest solution found: " + fastestSolution + " steps.\n" + 
						bestSolution.getBoxes() + "\n");
				AlgorithmPart2 alg2 = new AlgorithmPart2(mainText);
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alg2.start(bestSolution.getBoxConfig(), bestSolution.getPersons());
				while(alg2.isRunning()) {     /* Wait for each algorithm to finish before*/
					try {				      /* fetching data							 */
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				isRunning = false;
		    }
			
			/**
			 * Randomises the order of the boxes in the arraylist.
			 * @param boxes The list of boxes to randomise.
			 * @return The randomized list.
			 */
			private ArrayList<Box> randomiseList(ArrayList<Box> boxes) {
				ArrayList<Box> randomBoxes = new ArrayList<Box>();
				Random gen = new Random();
				ArrayList<Box> boxesCopy = copyBoxes(boxes);
				int size = boxesCopy.size();
				while (size > 0) {
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
				BoxConfiguration boxconfTemp = new BoxConfiguration(copyBoxes(boxes));
				int optimalPersons = 0;
				ArrayList<Box> topBoxes = new ArrayList<Box>();
				while(boxconfTemp.boxes.size() > 0) { /* Loop until boxesTemp is empty. */
					int optimalPersonsTemp = 0;
					for (Box box : boxconfTemp.boxes) {
						if (box.isTopBox()) {
							topBoxes.add(box);
							optimalPersonsTemp += box.getWeight(); /* Add weight. */
						}
					}
					for (Box box : topBoxes) { /* Remove the top boxes. */
						boxconfTemp.remove(box);
						
					}
					topBoxes.clear();
					if (optimalPersonsTemp > optimalPersons) { /* Updates the optimal persons */
						optimalPersons = optimalPersonsTemp;
					}
				}
				return optimalPersons;
			}
			
			/**
			 * Copies an arraylist of boxes.
			 * @param boxes The list to copy
			 * @return The copied list
			 */
			private ArrayList<Box> copyBoxes(ArrayList<Box> boxes) {
				ArrayList<Box> temp = new ArrayList<Box>();
				for (Box box : boxes) {
					temp.add(box);
				}
				return temp;
			}
		}).start();		
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
		private BoxConfiguration boxconfig2;
		private int stepsToSolve;
		private int persons;
		
		/**
		 * Constructor for the private class
		 * @param boxconfig The box config for this solution.
		 * @param steps The steps required for this solution.
		 * @param persons The number of persons used.
		 */
		public Solution(BoxConfiguration boxconfig, int steps, int persons) {
			this.boxconfig2 = boxconfig;
			this.stepsToSolve = steps;
			this.persons = persons;
			if (this.stepsToSolve < fastestSolution) {
				fastestSolution = this.stepsToSolve;
				bestSolution = this;
			}
		}
		
		/**
		 * 
		 * @return The box config.
		 */
		public BoxConfiguration getBoxConfig() {
			return this.boxconfig2;
		}
		
		/**
		 * 
		 * @return The number of steps to solve the box config.
		 */
		public int getSteps() {
			return this.stepsToSolve;
		}
		
		/**
		 * 
		 * @return The number of persons used to solve the box config.
		 */
		public int getPersons() {
			return this.persons;
		}
		
		public String getBoxes() {
			String str = "Solution: " ;
			for (Box box : this.boxconfig2.boxes) {
				str += box.getName() + ", ";
			}
			str += "Steps: " + this.stepsToSolve + ", Persons: " + this.persons + ".";
			return str;
		}
	}
	
}
