package lab4;

import java.util.ArrayList;

/**
 * The box class.
 * @author Viktor, Petter
 *
 */
public class Box {
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * The name of the box.
	 */
	private String name;
	
	/**
	 * The weight of the box.
	 */
	private int weight;
	
	/**
	 * The boxes above the list.
	 */
	private ArrayList<Box> boxesAbove;
	
	/**
	 * The boxes under the box.
	 */
	private ArrayList<Box> boxesUnder;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Constructor with 2 arguments.
	 * @param name The name of the box.
	 * @param weight The weight of the box.
	 */
	public Box(String name, int weight) {
		this.name = name;
		this.weight = weight;
		boxesAbove = new ArrayList<Box>();
		boxesUnder = new ArrayList<Box>();
	}
	
	/**
	 * Checks if the box is on the top (i.e. can be moved).
	 * @return True if the box is a top-box, false otherwise.
	 */
	public boolean isTopBox() {
		if (this.boxesAbove.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Tells the box that a box is directly above it.
	 * @param box The box to add that is above.
	 */
	public void addBoxAbove(Box box) {
		this.boxesAbove.add(box);
	}
	
	/**
	 * Tells the box that another box is directly under it.
	 * @param box The box to add that is underneath.
	 */
	public void addBoxUnder(Box box) {
		this.boxesUnder.add(box);
	}
	
	/**
	 * Removes all connections to and from this box. Removes the connection
	 * from both objects.
	 */
	public void destroy() {
		for (Box box : this.boxesAbove) {
			box.boxesUnder.remove(this);
		}
		for (Box box : this.boxesUnder) {
			box.boxesAbove.remove(this);
		}
		this.boxesUnder.clear();
		this.boxesAbove.clear();
	}
	
	/**
	 * @return The name of the box.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return The weight of the box.
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Textual presentation of the box and it's neighbors. Prints in sysout.
	 */
	public void presentBox() {
		System.out.print("I'm box: "+ this.getName() + ". ");
		String boxAb = "";
		for (Box box : this.boxesAbove) {
			boxAb += box.getName() + ", ";
		}
		System.out.print("Boxes above me: "+ boxAb);
		String boxUn = "";
		for (Box box : this.boxesUnder) {
			boxUn += box.getName() + ", ";
		}
		System.out.print("Boxes under me: "+ boxUn +"\n");
	}
	
	
	/**
	 * Creates a string with the "Neighbors" of the box, i.e. the boxes
	 * directly above and under. Is used when a graphical representation
	 * of the Boxconfig isn't available.
	 * @return A string with the neighboring boxes (up and down).
	 */
	public String getNeighbors() {
		String response = this.getName() + " " + getWeight() + ": ";
		if (boxesAbove.size() > 0) { /* Adds the boxes on top */
			String boxAb = "";
			for (Box box : this.boxesAbove) {
				boxAb += box.getName() + ", ";
			}
			response += "Boxes above me: "+ boxAb;
		} else {
			response += "Is a top box! ";
		}
		
		if (boxesUnder.size() > 0) { /* Adds the boxes under */
			String boxUn = "";
			for (Box box : this.boxesUnder) {
				boxUn += box.getName() + ", ";
			}
			response += "Boxes under me: "+ boxUn;
		} else {
			response += "Is at the bottom. ";
		}
		return response + "\n";
	}
	
	/**
	 * Calculates the depth recursively. Is not used in any current algorithm.
	 * @return The depth of the box.
	 */
	public int getDepth() {
		if (boxesUnder.isEmpty()) {
			return 0; // Might be 1
		}
		return getDepth() + 1;
	}
	
	/**
	 * @return The boxes above.
	 */
	public ArrayList<Box> getBoxesAbove() {
		return this.boxesAbove;
	}
	
	/**
	 * @return The boxes above.
	 */
	public ArrayList<Box> getBoxesUnder() {
		return this.boxesUnder;
	}
}

