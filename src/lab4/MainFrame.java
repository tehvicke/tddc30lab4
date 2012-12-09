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

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 *                        Object variables                           *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	private JPanel contentPane;
	JTextArea mainText; /* For the print */
	JList fileList;

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
		mainText.setEditable(false);
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
		
		JTextField pathTextField = new JTextField(PATH);
		pathTextField.setSize(tabbedPane.getWidth() - 240 , 24);
		pathTextField.setLocation(pathLabel.getWidth() + MARGIN, MARGIN);
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
		
		/* Choose file */
		File[] files = finder(this.PATH);
		String[] fileNames = new String[files.length];
		int count = 0;
		for (File file : files) {
			fileNames[count++] = file.getName();
		}
		fileList = new JList(fileNames);
		fileList.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				FILENAME = (String) fileList.getSelectedValue(); 
			}
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("Focus gained!!");
			}
		});
		JScrollPane fileListScroll = new JScrollPane(fileList);
		fileListScroll.setBounds(480, MARGIN * 3, 115, 60);
		choicePane.add(fileListScroll);
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
	private File[] finder(String dirName){
    	File dir = new File(dirName);
    	return dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename) { 
    	        	 return filename.endsWith(".txt");
    	        	 }
    	} );
    }
	
	private void runAlgorithm1() {
		AlgorithmPart1 alg = new AlgorithmPart1(mainText);
		mainText.setText("Runs algorithm part 1 on boxconfig: "+FILENAME + "\n");
		BoxConfiguration boxconfig = new BoxConfiguration(PATH + FILENAME);
		alg.start(boxconfig);
	}
}
