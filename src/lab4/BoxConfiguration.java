package lab4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A class for a box configuration. Could be a matrix.
 * @author Viktor, Petter
 *
 */
public class BoxConfiguration {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public ArrayList<Box> boxes; // Should probably be temporary as another representation is better/faster.
	private int minPersons = 0;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Default constructor.
	 * TODO: Define what this should do.
	 */
	public BoxConfiguration() {
		boxes = new ArrayList<Box>();
	}
	
	/**
	 * Constructor with a filename.
	 * @param filename Absolute path of the file to be read.
	 */
	public BoxConfiguration(String filename) {
		boxes = new ArrayList<Box>();
		this.createFromFile(filename);
		for (Box box : boxes) {
			if (minPersons < box.getWeight()) {
				minPersons = box.getWeight();
			}
		}
	}
	
	/**
	 * Adds the box to an arrayList and creates the box object.
	 * @param box
	 */
	public void addBox(Box box) {
		this.boxes.add(box);
	}
	
	
	/**
	 * Adds the connections between two boxes.
	 * @param nameBox1 Name of the upper box
	 * @param nameBox2 Name of the lower box
	 */
	public void addConnection(String nameBox1, String nameBox2) {
		for (Box box1 : this.boxes) {
			if (box1.getName().equals(nameBox1)) {
				for (Box box2 : this.boxes) {
					if (box2.getName().equals(nameBox2)) {
						box1.addBoxUnder(box2);
						box2.addBoxAbove(box1);
					}
				}
			}
		}
	}
	
	/**
	 * Textual presentation of all boxes in sysout.
	 */
	public void presentation() {
		for (Box box : this.boxes) {
			box.presentBox();
		}
	}
	
	/**
	 * Removes a box from the list if it's a top box.
	 * @param box The box to remove from the list.
	 */
	public void remove(Box box) {
		if (box.isTopBox()) {
			box.destroy(); /* Removes all connections to and from the box */
			boxes.remove(box); /* Removes box from the list */ 
			return;
		} 
		System.err.println("Box " + box.getName() + " is not on the top.");
	}
	
	/**
	 * Returns whether the box configuration can be solved with the number of
	 * workers.
	 * @param pers The number of workers to test with.
	 * @return True if possible.
	 */
	public boolean canBeSolved(int pers) {
		if (pers >= minPersons) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Creates the box config by reading a file.
	 * @param filename The file to be read. Absolute path.
	 */
	private void createFromFile(String filename) {
		/* Reads the file */
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String strLine;
			int noOfBoxes = Integer.parseInt(br.readLine()); /* The number of boxes */
			for (int i = 0; i < noOfBoxes; i++) {
				strLine = br.readLine();
				this.addBox(new Box(strLine.split(" ")[0], Integer.parseInt(strLine.split(" ")[1])));
			}
			
			/* The second integer which is the number of connections */
			int noOfConnections = Integer.parseInt(br.readLine());
			for (int i = 0; i < noOfConnections; i++) {
				strLine = br.readLine();
				this.addConnection(strLine.split(" ")[0], strLine.split(" ")[1]);				
			}
			in.close();
			
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
