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
	
	public static int TIMEINMS = 1000;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	private String name;
	private JTextArea mainText;
	private BoxConfiguration boxconfig;
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Default constructor
	 */
	public AlgorithmPart1(JTextArea mainText) {
		this.name = "Easy Algorithm";
		this.mainText = mainText;
	}
	
	/**
	 * Starts the sorting of boxes.
	 */
	public void start(BoxConfiguration boxconfigs) {
		this.boxconfig = boxconfigs;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					move(boxconfig);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Really bad and slow removing algorithm.
	 * @param boxes
	 */
	private void move(BoxConfiguration boxconfigs) {

		(new Thread()
		{
		    public void run() {
		    	boolean done = false;
		    	while(!done) {
					for (Box box : boxconfig.boxes) {
						if (box.isTopBox()) {
							boxconfig.remove(box);
							boxconfig.boxes.remove(box);
//							System.out.println("Box " + box.getName() + " was removed successfully.");
							mainText.append("Box " + box.getName() + " was removed.\n");
							break;
						}
					}
					try { 
						Thread.sleep(TIMEINMS / 10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (boxconfig.boxes.size() == 0) { // Exit criteria
						done = true;
					}
				}
		    }
		}).start();
		
		
		
	}

}
