package lab4;


/**
 * An algorithm for the first part of the lab.
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
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Default constructor
	 */
	public AlgorithmPart1() {
		this.name = "Easy Algorithm";
	}
	
	/**
	 * Starts the sorting of boxes.
	 */
	public void start(BoxConfiguration boxconfig) {
		this.move(boxconfig);
	}
	
	/**
	 * Really bad and slow removing algorithm.
	 * @param boxes
	 */
	private void move(BoxConfiguration boxconfig) {
		boolean done = false;
		while(!done) {
			for (Box box : boxconfig.boxes) {
				if (box.isTopBox()) {
					boxconfig.remove(box);
					boxconfig.boxes.remove(box);
					System.out.println("Box " + box.getName() + " was removed successfully.");
					break;
				}
			}
			try { 
				Thread.sleep(TIMEINMS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (boxconfig.boxes.size() == 0) { // Exit criteria
				done = true;
			}
		}
	}

}
