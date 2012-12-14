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
	protected static final int TIMEINMS = 100;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	protected JTextArea mainText;
	protected boolean isRunning;
	protected BoxConfiguration boxconfig;
	private String name;
		

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public Algorithm(JTextArea mainText) {
		this.name= "ABSTRAKT";
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
	
	protected void start(BoxConfiguration boxconfig) {
		this.boxconfig = boxconfig;
		this.move();
	}
	
	/* To be overridden */
	protected abstract void move();
	
}
