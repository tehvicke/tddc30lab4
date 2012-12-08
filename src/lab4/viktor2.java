package lab4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class viktor2 {
	public static String PATH = "/Users/viktordahl/Dropbox/Skola/Programmering/Java/eclipse/tddc30lab4/src/lab4/";
	
	
	public static void main(String[] args) {

		/* The box configuration */
		BoxConfiguration boxconfig = new BoxConfiguration(PATH + "boxes1.txt");
		
		AlgorithmPart1 alg = new AlgorithmPart1();
		alg.start(boxconfig);

	}

}
