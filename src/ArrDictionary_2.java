/*
 * Kadie 9/21/17
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ArrDictionary_2
{
	public HashMap<String,HashSet<Character>> PossibleChars = new HashMap<String,HashSet<Character>>();
	public HashMap<String,Integer> lengthThreshold = new HashMap<String,Integer>();

	int frequencyThreshold = 45;

	public ArrDictionary_2(int mode) throws FileNotFoundException {
		int count = 0;
        Scanner input;
		System.out.println("Loading dictionary to array...");
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
			String currentWord = scan.next();
			int strValue = scan.nextInt();
			if(strValue < frequencyThreshold)
			{
				continue;
			}
			char[] allChars = currentWord.toCharArray();
			if (allChars.length > 2)
			{
				Character firstChar = allChars[0];
				Character lastChar = allChars[allChars.length - 1];
				String strkey = firstChar+"_"+lastChar;
				if (PossibleChars.containsKey(strkey))
				{
					HashSet<Character> currentchars = PossibleChars.get(strkey);
					for (int i = 1; i < allChars.length - 1; i++)
					{
						if(!currentchars.contains(allChars[i]))
						{
							currentchars.add(allChars[i]);
						}
					}
				}
				else
				{
					HashSet<Character> currentchars = new HashSet<Character>();
					for (int i = 1; i < allChars.length - 1; i++)
					{
						if(!currentchars.contains(allChars[i]))
						{
							currentchars.add(allChars[i]);
						}
					}
					PossibleChars.put(strkey,currentchars);
				}

				int wordLength = allChars.length - 2;
				if(lengthThreshold.containsKey(strkey))
				{
					int oldVal = lengthThreshold.get(strkey);
					if(wordLength > oldVal)
					{
						lengthThreshold.replace(strkey, wordLength);
					}
				}
				else
				{
					lengthThreshold.put(strkey, wordLength);
				}
			}
		}
		input.close();
		System.out.println("Array Dictionary Loaded.");
	}

	
	
	// find possible letters for words in dict given first and last char
	// returns either alphabetically sorted String of possible letters 
	// or returns empty String if no words in Dict start with first and end with last
	public HashSet<Character> findPossChars(char first, char last) {
       String strKey = first+"_"+last;
		if(PossibleChars.containsKey(strKey))
		{
			return PossibleChars.get(strKey);
		}
		else
		{
			return new HashSet<Character>();
		}
    }

	public int getWordLengthThreshold(char first, char last) {
		String strKey = first+"_"+last;
		if(PossibleChars.containsKey(strKey))
		{
			return lengthThreshold.get(strKey);
		}
		else
		{
			return -1;
		}
	}
}
