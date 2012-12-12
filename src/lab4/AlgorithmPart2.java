/**
 * 
 */
package lab4;

import java.util.ArrayList;

import javax.swing.JTextArea;

/**
 * An algorithm for the second part. Takes number of workers and adapts
 * the boxing to that. 
 * @author Viktor, Petter
 *
 */
public class AlgorithmPart2 {

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
		this.mainText = mainText;
	}
	
	/**
	 * Starts the algorithm.
	 * @param boxconfig The box config to work with.
	 * @param persons The number of persons that should work. This has to be
	 * larger than the least heavy box.
	 */
	public void start(BoxConfiguration boxconfig, int persons) {
		this.personsMax = persons;
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
		    	stepsToSolve = 0;
		    	ArrayList<Box> boxesToMove = new ArrayList<Box>();
		    	int personsFree;
		    	while(isRunning) { /* One of this loop is one "round" */
		    		personsFree = personsMax; /* Resents the number of free persons */
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
		    		
		    		if (!silent) {
		    			mainText.append(tempString + "\n");
		    		}
		    		try {
						Thread.sleep(AlgorithmPart1.TIMEINMS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		if (boxconfig.boxes.size() == 0) {
		    			isRunning = false;
		    			break;
		    		}
		    	}
		    	System.out.println(stepsToSolve);
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
	 * 
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
}
