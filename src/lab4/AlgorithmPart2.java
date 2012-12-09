/**
 * 
 */
package lab4;

import java.util.ArrayList;

import javax.swing.JTextArea;

/**
 * 
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

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
	/**
	 * Default constructor.
	 * @param mainText The area where text output should be put.
	 */
	public AlgorithmPart2() {
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
//		    		mainText.append(personsMax - personsFree + " st personer flyttar boxarna ");
//		    		MainFrame.appendScrolled(personsMax - personsFree + " st personer flyttar boxarna "); // TEMP STATIC FUNCTION
		    		String tempString = personsMax - personsFree + " st personer flyttar boxarna ";
		    		for (Box box : boxesToMove) {
		    			boxconfig.remove(box);
		    			tempString += box.getName();
		    			if (boxesToMove.indexOf(box) != boxesToMove.size() - 1) {
		    				tempString += ", ";
		    			}
		    		}
		    		boxesToMove.clear();
		    		MainFrame.appendScrolled(tempString); // Writes to output textarea
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
		    	
		    }
		}).start();		
	}

	/**
	 *
	 * @return True if it's currently running.
	 */
	public boolean isRunning() {
		return isRunning;
	}
}
