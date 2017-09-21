import java.util.*;
import java.lang.*;
import java.io.*;

public class PossibleLetters {
	public String[] arrDict = new String[]{"a", "and", "b", "anrd", "cand", "cat", "amberd"};
	
    public String findPossChars(char first, char last) {
        String possletters = "";
        
        for (int kk = 0; kk < arrDict.length; kk++){
			for (int ii = 0; ii < arrDict.length; ii++){
				if ((arrDict[kk].charAt(0) == first) && (arrDict[ii].charAt(0) == first) 
					&& (arrDict[kk].charAt(arrDict[kk].length() - 1) == last) && (arrDict[ii].charAt(arrDict[ii].length() - 1) == last))
					{
						String temp1 = arrDict[kk].substring(1, arrDict[kk].length() - 1);
						String temp2 = arrDict[ii].substring(1, arrDict[ii].length() - 1);
						
						possletters = possletters + temp1 + temp2;	
				}	
			}
		}
        return removeDups(possletters);
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
	
	public static void main(String[] args) {
		PossibleLetters posslett = new PossibleLetters();
		String letts = posslett.findPossChars('a','d');
		System.out.println(letts);
	}
}