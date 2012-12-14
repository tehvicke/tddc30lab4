package lab4;

import javax.swing.JTextArea;


/**
 * An algorithm for the first part of the lab. Loops through all boxes and checks if it's
 * a top box. If it is it removes it and it's connections, as well as it's connections
 * connection to the box removed. 
 * @author Viktor, Petter
 *
 */
public class AlgorithmPart1 extends Algorithm {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Default constructor
	 */
	public AlgorithmPart1(JTextArea mainText) {
		super(mainText,"Easy Algorithm");
	}

	/**
	 * Starts the sorting of boxes. Doesn't care about the weight of the box.
	 * @param boxconfig The box configuration to sort.
	 */


	/**
	 * Really bad and slow removing algorithm.
	 * @param boxes
	 */
	protected void move() {
		isRunning = true;
		while(boxconfig.boxes.size() > 0) {
			for (Box box : boxconfig.boxes) {
				if (box.isTopBox()) {
					boxconfig.remove(box);
					mainText.append("Box " + box.getName() + " was removed.\n");
					break;
				}
			}
			try { 
				Thread.sleep(TIMEINMS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		isRunning = false;
	}
	/**
	 * 
	 * @return True if it's currently running
	 */
	public boolean isRunning() {
		return isRunning;
	}
}
