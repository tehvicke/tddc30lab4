package lab4;

import javax.swing.JTextArea;

/**
 * An abstract class of the algorithms, i.e. all the shared functions and
 * variables.
 * @author Viktor, Petter
 *
 */
public abstract class Algorithm {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private static final int TIMEINMS = 100;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private JTextArea mainText;
	private boolean isRunning;
	private BoxConfiguration boxconfig;
	private String name;
		

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public Algorithm(JTextArea mainText) {
		this.name("generic");
		this.mainText = mainText;
	}
	
	public Algorithm(JTextArea mainText, String name) {
		this.name = name;
		this.mainText = mainText;
	}
	/**
	 * 
	 * @return True if running
 	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	public void start(BoxConfiguration boxconfig) {
		this.boxconfig = boxconfig;
		this.move();
	}
	
	/* To be overridden */
	private void move() {
		
	}
	
}
