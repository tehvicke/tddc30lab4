package lab4;

import java.awt.EventQueue;

import javax.swing.JTextArea;


/**
 * An algorithm for the first part of the lab. Loops through all boxes and checks if it's
 * a top box. If it is it removes it and it's connections, as well as it's connections
 * connection to the box removed. 
 * @author Viktor, Petter
 *
 */
public class AlgorithmPart1 {
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public static int TIMEINMS = 100;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	private String name;
	private JTextArea mainText;
	private BoxConfiguration boxconfig;
	private boolean isRunning = true;
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Default constructor
	 */
	public AlgorithmPart1() {
		this.name = "Easy Algorithm";
		this.mainText = mainText;
	}
	
	/**
	 * Starts the sorting of boxes. Doesn't care about the weight of the box.
	 * @param boxconfig The box configuration to sort.
	 */
	public void start(BoxConfiguration boxconfig) {
		this.boxconfig = boxconfig;
		this.move();
	}
	
	/**
	 * Really bad and slow removing algorithm.
	 * @param boxes
	 */
	private void move() {
		(new Thread() {
		    public void run() {
		    	isRunning = true;
		    	while(isRunning) {
					for (Box box : boxconfig.boxes) {
						if (box.isTopBox()) {
							boxconfig.remove(box);
							MainFrame.appendScrolled("Box " + box.getName() + " was removed.");
							break;
						}
					}
					try { 
						Thread.sleep(TIMEINMS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (boxconfig.boxes.size() == 0) { // Exit criteria
						isRunning = false;
					}
				}
		    }
		}).start();		
	}

	public boolean isRunning() {
		return isRunning;
	}
}
