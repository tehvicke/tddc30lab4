package lab4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JTextArea;

/**
 * A class for a box configuration.
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

	/** 
	 * An array for all the boxes in the box configuration.
	 * Is protected for allowing the algorithms to reach it.
	 */
	protected ArrayList<Box> boxes;

	/** 
	 * The minimum number of persons required to finish the box config.
	 */
	private int minPersons = 0;

	/**
	 * A boolean for telling the createFromFile-function that it should
	 * only load the connections between the boxes but not the actual boxes
	 * themselves.
	 */
	private boolean onlyConnections;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Constructor with a filename.
	 * @param filename Absolute or relative path of the file to be read.
	 */
	public BoxConfiguration(String filename) {
		boxes = new ArrayList<Box>();
		onlyConnections = false;
		this.createFromFile(filename);
		for (Box box : boxes) { /* Calculate min persons to solve */
			if (minPersons < box.getWeight()) {
				minPersons = box.getWeight();
			}
		}
	}

	/**
	 * Constructor for creating a box config from a set of already chosen boxes.
	 * @param boxes The list of boxes to use when creating the box config.
	 */
	public BoxConfiguration(ArrayList<Box> boxes2) {
		this.boxes = new ArrayList<Box>();
		for (Box box : boxes2) {
			this.boxes.add(box);
			if (minPersons < box.getWeight()) { /* Calculate min persons to solve */
				minPersons = box.getWeight();
			}
		}
		onlyConnections = true;
	}

	/**
	 * Adds the box to an arrayList and creates the box object.
	 * @param box The box to add.
	 */
	public void addBox(Box box) {
		this.boxes.add(box);
	}

	/**
	 * Adds the connections between two boxes.
	 * @param nameBox1 Name of the upper box.
	 * @param nameBox2 Name of the lower box.
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
	 * Presentation of the box config in the text area.
	 * @param mainText The text area where the text is to be printed.
	 */
	public void presentation(JTextArea mainText) {
		for (Box box : this.boxes) {
			mainText.append(box.getNeighbors());
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
				if (!onlyConnections) {
					this.addBox(new Box(strLine.split(" ")[0], Integer.parseInt(strLine.split(" ")[1])));
				}
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

	/**
	 * @return int Minimum number of required persons to solve the boxConfig.
	 */
	public int getMinPersons() {
		return minPersons;
	}

	public void loadConnections() {
		onlyConnections = true;
		createFromFile(MainFrame.PATH + MainFrame.FILENAME);
	}
}
