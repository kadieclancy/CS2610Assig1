/*
 * Sample code for CS 2610 Homework 1
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
	public ArrayList<String> wtable;
	public ArrayList<Integer> ftable;

	int frequencyThreshold = 45;

	public Dictionary(int mode) throws FileNotFoundException {
		System.out.println("Loading dictionary...");

		wtable = new ArrayList<String>();
		ftable = new ArrayList<Integer>();
        Scanner input;
        
        if (mode == 1){
            input = new Scanner(new File("./wordTurbo.txt"));
        }
        else{
            input = new Scanner(new File("./wordf.txt"));
        }
		String buffer = new String("");

		while(input.hasNext()){
			buffer = input.nextLine();
			Scanner scan = new Scanner(buffer).useDelimiter(",");
			String wordStr = scan.next();
			int strValue = scan.nextInt();
			if(strValue > frequencyThreshold)
			{
				wtable.add(wordStr);
				ftable.add(strValue);
			}

		}

		input.close();
		System.out.println("Dictionary Loaded.");
	}
}
