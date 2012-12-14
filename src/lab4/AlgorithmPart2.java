/**
 * 
 */
package lab4;

import java.util.ArrayList;

import javax.swing.JTextArea;

/**
 * An algorithm for the second part. Takes number of workers and adapts
 * the boxing to that. Sorts the prio list so that the least heavy boxes
 * are to be chosen first for making sure that the most number of boxes
 * are being moved at the same time.
 * @author Viktor, Petter
 *
 */
public class AlgorithmPart2 extends Algorithm {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	private int personsMax; // The number of persons 
	private int stepsToSolve;
	private boolean silent = false;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


	/**
	 * Default constructor.
	 * @param mainText The area where text output should be put.
	 */
	public AlgorithmPart2(JTextArea mainText) {
		super(mainText,"algo2");
	}

	/**
	 * Starts the algorithm.
	 * @param boxconfig The box config to work with.
	 * @param persons The number of persons that should work. This has to be
	 * larger than the least heavy box.
	 */
	protected void start(BoxConfiguration boxconfig, int persons) {
		this.personsMax = persons;
		super.start(boxconfig);
	}

	/**
	 * The actual "moving"-algorithm.
	 */
	@Override
	protected void move() {
		optimiseList();
		isRunning = true;
		stepsToSolve = 0;
		ArrayList<Box> boxesToMove = new ArrayList<Box>();
		int personsFree;
		while(boxconfig.boxes.size() > 0) { /* One of this loop is one "round"    */
			personsFree = personsMax;       /* Resets the number of free persons */
			for (Box box : boxconfig.boxes) {
				if (box.isTopBox() && box.getWeight() <= personsFree) {
					boxesToMove.add(box);
					personsFree -=  box.getWeight();
				}
			}
			stepsToSolve++; /* Counts the number of steps to solve the algorithm */
			String tempString = personsMax - personsFree + " st personer flyttar boxarna ";
			for (Box box : boxesToMove) {
				boxconfig.remove(box);
				tempString += box.getName();
				if (boxesToMove.indexOf(box) != boxesToMove.size() - 1) {
					tempString += ", ";
				}
			}
			boxesToMove.clear();

			if (!silent) { /* Shall print out it one by one */
				mainText.append(tempString + "\n");

				try {
					Thread.sleep(AlgorithmPart1.TIMEINMS);
				} catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
		}
		isRunning = false;	
	}

	

	/**
	 * @return The steps to solve the current algorithm.
	 */
	public int getSteps() {
		return this.stepsToSolve;
	}

	/**
	 * Sets the alg in silent mode, i.e. doesn't print in mainText.
	 */
	public void setSilent() {
		this.silent = true;
	}

	/**
	 * Sets silent mode to off, i.e. prints in mainText.
	 */
	public void setUnsilent() {
		this.silent = false;
	}
	
	/**
	 * Optimises the list according to the weight ranging from
	 * low to high.
	 */
	private void optimiseList() {
		ArrayList<Box> sortedList = new ArrayList<Box>();
		while (boxconfig.boxes.size() > 0) {
			Box leastHeavy = boxconfig.boxes.get(0);
			for (Box box : boxconfig.boxes) {
				if (box.getWeight() < leastHeavy.getWeight()) {
					leastHeavy = box;
				}
			}
			sortedList.add(leastHeavy);
			this.boxconfig.boxes.remove(leastHeavy);
		}
		this.boxconfig.boxes = sortedList;
	}
}
