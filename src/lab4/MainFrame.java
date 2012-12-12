package lab4;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Class variables                            *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * Absolute or relative path to where the files are stored.
	 */
	public static String PATH = "src/lab4/";
	
	/**
	 * The filename of the file to open. Is used both for the boxconfig
	 * loading as well as the image shown.
	 */
	public static String FILENAME;
	
	/**
	 * Name of the program. Whats shown in the top bar of the program.
	 */
	public static final String PROGRAMNAME = "LŒdor & co";
	
	/**
	 * The margin of all components. Should not be changed.
	 */
	public static final int MARGIN = 6;
	

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * The number of persons to work in the algoritm. Defaults to 5 and
	 * is only used in Algorithm part 2.
	 */
	private int personsWorking = 5;
	
	/**
	 * The box configuration to be used.
	 */
	private BoxConfiguration boxConfig;
	
	/**
	 * The box config image if it exist.
	 */
	private ImageIcon boxconfigImage;
	
	/**
	 * True if an image is shown for the boxconfig representation.
	 * False if textual representation.
	 */
	private boolean imageIsShown = false;
	
	/**
	 * The main area.
	 */
	private JPanel contentPane;
	
	/**
	 * The main tab where the box things are shown.
	 */
	private JPanel mainTab;
	
	/**
	 * An area for changing the PATH variable.
	 */
	private JPanel settingsTab;
	
	/**
	 * The main area where the text is being printed.
	 */
	private JTextArea mainText;
	
	/**
	 * The text field for the Path. Belongs to settingsTab.
	 */
	private JTextField pathTextField;
	
	/**
	 * the list of .txt-files found in the PATH directory.
	 */
	private JList fileList;
	
	/**
	 * A text field for determing the number of persons to use.
	 */
	private JTextField personsText;
	
	/**
	 * The fram where the image is stored.
	 */
	private JLabel imageFrame;
	
	/**
	 * The holder for the mainText for making it scrollable.
	 */
	private JScrollPane mainScroll;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                               GUI                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/**
	 * The constructor of the main frame of the application. It initializes all
	 * panes of the app and is rather long.
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
		mainTab = new JPanel();
		mainTab.setLayout(null);
		tabbedPane.addTab("Box handling", null, mainTab, "");
		
		/* MAIN Text area for showing results. */
		mainText = new JTextArea();
		mainText.setFocusable(false);
		DefaultCaret caret = (DefaultCaret)mainText.getCaret(); /* Makes it scroll down */
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		/* when appending.      */
		
		mainScroll = new JScrollPane(mainText);
		mainScroll.setLocation(MARGIN, MARGIN);
		mainScroll.setSize(
				tabbedPane.getWidth() - MARGIN * 6, 
				tabbedPane.getHeight() - 200);
		mainScroll.setFocusable(false);
		mainTab.add(mainScroll);
		
		/* The settings view. */
		settingsTab = new JPanel();
		settingsTab.setLayout(null);
		tabbedPane.addTab("Global settings", null, settingsTab, "");
		
		/* SETTINGS The text field for path. */
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
				if (!PATH.equals(pathTextField.getText())) {
//					PATH = pathTextField.getText();
//					mainText.append("Path updated to : " + PATH + "\n");
					try {
						fileList = setFiles();
					} catch (NullPointerException e) {
					}
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		settingsTab.add(pathTextField);
		
		/* JPane with title */
		JPanel choicePane = new JPanel();
		choicePane.setBorder(
				new TitledBorder(
						null, 
						"Settings", 
						TitledBorder.LEADING, 
						TitledBorder.TOP, 
						null, 
						null));
		choicePane.setBounds(
				MARGIN / 2,
				mainScroll.getY() + mainScroll.getHeight() + MARGIN, 
				mainScroll.getWidth() + MARGIN, 
				130);
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
		
		/* Button algorithm part 2 */
		JButton algorithm3 = new JButton("Algorithm part 3.");
		algorithm3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runAlgorithm3();
			}
		});
		algorithm3.setBounds(MARGIN, MARGIN * 13, 160, 30);
		choicePane.add(algorithm3);
		
		
		/* File choosing panel */
		fileList = setFiles();
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
		fileListScroll.setBounds(465, MARGIN * 3, 130, 80);
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
		personsText.setBounds(MARGIN  + 160 , MARGIN * 7, 40, 30);
		choicePane.add(personsText);
		
		/* Knapp fšr att initiera en box config.*/
		JButton addBoxConfigButton = new JButton("Create BoxConfig");
		addBoxConfigButton.setBounds(460, MARGIN * 16, 140, 30);
		addBoxConfigButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainText.append("boxconfig " + FILENAME + " loaded.\n");
				boxConfig = new BoxConfiguration(PATH + FILENAME);
				presentBoxConfig();
			}
		});
		choicePane.add(addBoxConfigButton);
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
	 * Creates a JList with the names of all box config files.
	 * @return A JList with the boxconfig names.
	 */
	private JList setFiles() {
		File[] files = finder(PATH);
		String[] fileNames = new String[files.length];
		int count = 0;
		for (File file : files) {
			fileNames[count++] = file.getName();
		}
		JList fileListLocal = new JList(fileNames);
		if (fileNames.length > 0) { /* Selects the first value for avoiding null */
			fileListLocal.setSelectedIndex(0);
			FILENAME = (String) fileListLocal.getSelectedValue();
		}
		return fileListLocal;
	}
	
	/**
	 * Runs the algorithm from part 1. The bad one.
	 */
	private void runAlgorithm1() {
		(new Thread() {
			public void run() {
				if (imageIsShown) {
					imageFrame.setVisible(false);
					mainScroll.setVisible(true);
				}
				AlgorithmPart1 alg = new AlgorithmPart1(mainText);
				mainText.append("Runs algorithm part 1 on boxconfig: " + FILENAME + "\n");
				alg.start(boxConfig);
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
				mainText.append("Time for execution: " + timeForExecutionInMs+"ms\nBoxconfig " + FILENAME + " is empty.\n");
			}
		}).start();
	}
	
	
	/**
	 * Run the second algoritm. The time efficient.
	 */
	private void runAlgorithm2() {
		(new Thread() {
			public void run() {
				if (imageIsShown) {
					imageFrame.setVisible(false);
					mainScroll.setVisible(true);
				}
				if (!boxConfig.canBeSolved(personsWorking)) {
					mainText.append("Too few workers to run algorithm 2." +
							"Try a few more.\n");
					return;
				}
				AlgorithmPart2 alg = new AlgorithmPart2(mainText);
				mainText.append("Runs algorithm part 2 on boxconfig: " + FILENAME + "\n");
				alg.start(boxConfig, personsWorking);
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
				mainText.append("Time for execution: " + timeForExecutionInMs + "ms\nBoxconfig " + FILENAME + " is empty.\n");
			}
		}).start();
	}
	
	/**
	 * Run the third algoritm. The smart one.
	 */
	private void runAlgorithm3() {
		(new Thread() {
			public void run() {
				if (imageIsShown) {
					imageFrame.setVisible(false);
					mainScroll.setVisible(true);
				}
				Alg3 alg = new Alg3(mainText);
				mainText.append("Runs algorithm part 3 on boxconfig: " + FILENAME + "\n");
				alg.start(boxConfig);
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
				mainText.append("Time for execution: " + timeForExecutionInMs + "ms\nBoxconfig " + FILENAME + " is empty.\n");
			}
		}).start();
	}
	
	private void presentBoxConfig() {
		try { /* Removes the imageButton from the GUI if it exists */
			mainTab.remove(imageFrame);
			imageFrame.setVisible(false);
		} catch (Exception e) {
		}
		mainText.setText(""); /* Resets the text area */
		try { /* Tries to load the image corresponding to the boxconfig. */
			Image img = (new ImageIcon(PATH + FILENAME.split(".txt")[0] + ".gif")).getImage();
			
			BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			g.drawImage(img, 0, 0, mainScroll.getWidth() - MARGIN, mainScroll.getHeight() - MARGIN, null);
			boxconfigImage = new ImageIcon(bi);
			
			imageFrame = new JLabel(boxconfigImage);
			imageFrame.setBounds(-MARGIN * 2, 0, boxconfigImage.getIconWidth() + MARGIN, boxconfigImage.getIconHeight() + MARGIN);
			imageFrame.setVisible(true);
			mainText.add(imageFrame);
			imageIsShown = true;
		} catch (Exception e) { /* Textual presentation if not found */
			boxConfig.presentation(mainText);
			imageIsShown = false;
		}
		
	}
} // Class
