package lab4;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	public static String PATH = "/Users/viktordahl/Dropbox/Skola/Programmering/Java/eclipse/tddc30lab4/src/lab4/";
	public static String PROGRAMNAME = "LŒdor & co";
	public static int MARGIN = 6; //Margin for all the components
	public static String FILENAME;
	
	/* Some components that needs attention from more than the constructor */
	private static JPanel contentPane;
	private static JTextArea mainText; /* For the print */
	private static JTextField pathTextField;
	private static JList fileList;
	private static JTextField personsText;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	public int personsWorking = 5;
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                               GUI                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * The constructor of the main frame of the application.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(320, 100, 450, 300);
		setTitle(PROGRAMNAME);
		setSize(640,480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/* The tabbed panel */
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(2, 2, this.getWidth() - 4, this.getHeight() - 24);
		contentPane.add(tabbedPane);
		
		/* The main view where the result is showed among other things. */
		JPanel mainTab = new JPanel();
		tabbedPane.addTab("Box handling", null, mainTab, "Tjena.");
		
		/* MAIN Text area for showing results. */
		mainTab.setLayout(null);
		mainText = new JTextArea();
		JScrollPane mainScroll = new JScrollPane(mainText);
		mainScroll.setLocation(MARGIN, MARGIN);
		mainScroll.setSize(tabbedPane.getWidth() - MARGIN * 6, tabbedPane.getHeight() - 200);
		mainTab.add(mainScroll);
		
		/* The settings view. */
		JPanel settingsTab = new JPanel();
		tabbedPane.addTab("Global settings", null, settingsTab, "Tjenare.");
		
		/* SETTINGS The text field for path. */
		settingsTab.setLayout(null);
		JLabel pathLabel = new JLabel("Path to the text files :");
		pathLabel.setLocation(MARGIN, MARGIN);
		pathLabel.setSize(136, 16);
		pathLabel.setAlignmentX(LEFT_ALIGNMENT);
		settingsTab.add(pathLabel);
		
		pathTextField = new JTextField(PATH);
		pathTextField.setSize(tabbedPane.getWidth() - 240 , 24);
		pathTextField.setLocation(pathLabel.getWidth() + MARGIN, MARGIN);
		pathTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				PATH = pathTextField.getText();
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		settingsTab.add(pathTextField);
		
		/* JPane with title */
		JPanel choicePane = new JPanel();
		choicePane.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		choicePane.setBounds(MARGIN / 2, mainScroll.getY() + mainScroll.getHeight() + MARGIN, mainScroll.getWidth() + MARGIN, 130);
		choicePane.setLayout(null);
		mainTab.add(choicePane);	
		
		/* Button algorithm part 1 */
		JButton algorithm1 = new JButton("Algorithm part 1.");
		algorithm1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runAlgorithm1();
			}
		});
		algorithm1.setBounds(MARGIN , MARGIN * 3, 160, 30);
		choicePane.add(algorithm1);
		
		/* Button algorithm part 2 */
		JButton algorithm2 = new JButton("Algorithm part 2.");
		algorithm2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runAlgorithm2();
			}
		});
		algorithm2.setBounds(MARGIN, MARGIN * 7, 160, 30);
		choicePane.add(algorithm2);
		
		/* File choosing panel */
		File[] files = finder(PATH);
		String[] fileNames = new String[files.length];
		int count = 0;
		for (File file : files) {
			fileNames[count++] = file.getName();
		}
		fileList = new JList(fileNames);
		if (fileNames.length > 0) { /* Selects the first value for avoiding null */
			fileList.setSelectedIndex(0);
			FILENAME = (String) fileList.getSelectedValue();
		}
		fileList.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				FILENAME = (String) fileList.getSelectedValue(); 
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		JScrollPane fileListScroll = new JScrollPane(fileList);
		fileListScroll.setBounds(480, MARGIN * 3, 115, 60);
		choicePane.add(fileListScroll);
		
		
		/* Textfield to choose number of persons working */
		personsText = new JTextField(Integer.toString(personsWorking));
		personsText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				personsWorking = Integer.parseInt(personsText.getText());
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		personsText.setBounds(MARGIN * 2 + 160, MARGIN * 7, 40, 30);
		choicePane.add(personsText);
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                            Functions                              *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Launch the application. The main function.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * A function that returns all files ending with .txt in a directory.
	 * @param dirName Absolute path to directory.
	 * @return
	 */
	private File[] finder(String dirName) {
    	File dir = new File(dirName);
    	return dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename) { 
    	        	 return filename.endsWith(".txt");
    	        	 }
    	} );
    }
	
	/**
	 * Runs the algorithm from part 1. The bad one.
	 */
	private void runAlgorithm1() {
		(new Thread() {
			public void run() {
				AlgorithmPart1 alg = new AlgorithmPart1();
				appendScrolled("Runs algorithm part 1 on boxconfig: " + FILENAME);
				mainText.setCaretPosition(mainText.getDocument().getLength());
				alg.start(new BoxConfiguration(PATH + FILENAME));
				long t1 = System.nanoTime();
				while (alg.isRunning()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				long t2 = System.nanoTime();
				long timeForExecutionInMs = (t2 - t1) / 1000000;
				appendScrolled("Time for execution: " + timeForExecutionInMs+"ms");
			}
		}).start();
	}
	
	
	/**
	 * Run the second algoritm. The time efficient.
	 */
	private void runAlgorithm2() {
		(new Thread() {
			public void run() {
				BoxConfiguration boxconfig = new BoxConfiguration(PATH + FILENAME);
				if (!boxconfig.canBeSolved(personsWorking)) {
					appendScrolled("Too few workers to run algorithm 2." +
							"Try a few more.");
					return;
				}
				AlgorithmPart2 alg = new AlgorithmPart2();
				appendScrolled("Runs algorithm part 2 on boxconfig: " + FILENAME);
				alg.start(boxconfig, personsWorking);
				long t1 = System.nanoTime();
				while (alg.isRunning()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				long t2 = System.nanoTime();
				long timeForExecutionInMs = (t2 - t1) / 1000000;
				appendScrolled("Time for execution: " + timeForExecutionInMs + "ms");
			}
		}).start();
	}

	/**
	 * Appends a string as well as a new line, and scrolls to the bottom of
	 * the textarea.
	 * @param str The string to be printed.
	 */
	public static void appendScrolled(String str) {
		mainText.append(str + "\n");
		mainText.setCaretPosition(mainText.getDocument().getLength());
	}
	
	
} // Class
