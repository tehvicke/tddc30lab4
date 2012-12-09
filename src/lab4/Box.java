package lab4;

import java.util.ArrayList;
import java.util.Random;

/**
 * The box class.
 * @author Viktor, Petter
 *
 */
public class Box {
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* These aren't really used */
	public static int MAXNUMBEROFBOXES = 100; // For the random int generator. Is not used.
	public static int DEFAULTBOXHEIGHTINPIXELS = 64;
	public static int DEFAULTBOXWIDTHINPIXELS = 64;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* Basic info about the box */
	private String name;
	private int weight;
	private int levelSpan = 1; // How many levels the box spans. Default is 1.
	private int widthSpan = 1; // How wide the box is. A multiplier of DEFAULTBOXWIDTHINPIXELS
	
	/* Might not be used at all. */
	private int width; // multiplier
	private int height; // This should probably be a multiplier to DEFAULTBOXHEIGHTINPIXELS * levelSpan
	
	/* The boxes above and under */
	public ArrayList<Box> boxesAbove; // Above isn't really needed.
	public ArrayList<Box> boxesUnder;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Constructor with no arguments.
	 */
	public Box() {
		Random generator = new Random();
		this.name = "Box " + generator.nextInt(MAXNUMBEROFBOXES);
		this.weight = 1;
		
		boxesAbove = new ArrayList<Box>();
		boxesUnder = new ArrayList<Box>();
	}
	
	/**
	 * Constructor with 1 string argument.
	 * @param name The name of the box
	 */
	public Box(String name) {
		this.name = name;
		this.weight = 1;
		
		boxesAbove = new ArrayList<Box>();
		boxesUnder = new ArrayList<Box>();
	}
	
	/**
	 * Constructor with 1 int argument.
	 * @param weight The weight of the box.
	 */
	public Box(int weight) {
		Random generator = new Random();
		this.name = "Box " + generator.nextInt(MAXNUMBEROFBOXES);
		this.weight = weight;
		
		boxesAbove = new ArrayList<Box>();
		boxesUnder = new ArrayList<Box>();
	}
	
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

	public Box moveBox() {
		// TODO: Write function that removes the box.
		return this;
	}
	
	/**
	 * Returns true if the box is on the top (i.e. can be moved).
	 * @return Whether the box is free to be moved or not.
	 */
	public boolean isTopBox() {
		if (this.boxesAbove.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the number of levels the box should spans. This mainly applies to the
	 * lowmost boxes I think.
	 * @param span Number of levels the box spans.
	 */
	public void setSpan(int span) {
		this.levelSpan = span;
	}

	/**
	 * Tells the box that a box is directly above it.
	 * @param box The box that is above.
	 */
	public void addBoxAbove(Box box) {
		this.boxesAbove.add(box);
	}
	
	/**
	 * Tells the box that another box is directly under it.
	 * @param box The box that is under.
	 */
	public void addBoxUnder(Box box) {
		this.boxesUnder.add(box);
	}
	
	/**
	 * Removes all connections to and from this box. Removes the connection from both objects.
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
	 * Textual presentation of the box and it's neighbors
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
	 * @return A string with the neighbouring boxes (up and down).
	 */
	public String getNeighbors() {
		String response = "I'm box: "+ this.getName() + ". ";
		String boxAb = "";
		for (Box box : this.boxesAbove) {
			boxAb += box.getName() + ", ";
		}
		response += "Boxes above me: "+ boxAb;
		String boxUn = "";
		for (Box box : this.boxesUnder) {
			boxUn += box.getName() + ", ";
		}
		response += "Boxes under me: "+ boxUn +"\n";
		return response;
	}
	
}

