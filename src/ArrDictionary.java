/*
 * Kadie 9/21/17
 *
 */
import java.lang.*;
import java.io.*;
import java.util.*;

public class ArrDictionary {
	public String[] arrDict = new String[17805];
	public String[][] table = new String[28][28];

	public ArrDictionary() throws FileNotFoundException {
		int count = 0;
		System.out.println("Loading dictionary to array...");

		Scanner input = new Scanner(new File("./wordf.txt"));
		String buffer = new String("");

		while(input.hasNext()){
			buffer = input.nextLine();
			Scanner scan = new Scanner(buffer).useDelimiter(",");
			arrDict[count] = scan.next();
			count++;
		}
		
		char[] first = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char[] last = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		
		for (int i = 0; i < first.length; i++)
		{
			for (int k = 0; k < last.length; k++)
			{
				table[i][k] = findPossChars(first[i],last[k]);
			}
		}
		input.close();
		System.out.println("Array Dictionary Loaded.");
	}
	
	// table lookup of char
	public String getLetters(char f, char l) {
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int find = alpha.indexOf(f);
		int lind = alpha.indexOf(l);
		return (table[find][lind]);
	}
	
	
	// find possible letters for words in dict given first and last char
	// returns either alphabetically sorted String of possible letters 
	// or returns empty String if no words in Dict start with first and end with last
	public String findPossChars(char first, char last) {
        String possletters = "";
        
        for (int kk = 0; kk < arrDict.length; kk++){
				if ((arrDict[kk].length() > 1) && (arrDict[kk].charAt(0) == first) && (arrDict[kk].charAt(arrDict[kk].length() - 1) == last))
					{
						String temp1 = arrDict[kk].substring(1, arrDict[kk].length() - 1);
						//System.out.println(temp1 + " " );
						possletters = possletters + temp1;	
						//System.out.println(possletters);
				}
		}
		if (possletters.length() > 0){
        	return removeDups(possletters);
        }
        else{
        	return possletters;
        }
    }
    
    /* Method to remove duplicates in a sorted array */
    static String removeDupsSorted(String str)
    {
        int res_ind = 1, ip_ind = 1;
         
        // Character array for removal of duplicate characters
        char arr[] = str.toCharArray();
         
        /* In place removal of duplicate characters*/
        while (ip_ind != arr.length)
        {
            if(arr[ip_ind] != arr[ip_ind-1])
            {
                arr[res_ind] = arr[ip_ind];
                res_ind++;
            }
            ip_ind++;
           
        }
     
        str = new String(arr);
        return str.substring(0,res_ind);
    }
      
    /* Method removes duplicate characters from the string
       This function work in-place and fills null characters
       in the extra space left */
    static String removeDups(String str)
    {
       // Sort the character array
       char temp[] = str.toCharArray();
       Arrays.sort(temp);
       str = new String(temp);
        
       // Remove duplicates from sorted
       return removeDupsSorted(str);
    }
}
