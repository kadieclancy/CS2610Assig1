import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by Ahmed on 9/20/2017.
 */
public class Swiping
{
    Dictionary dict;
    TrieClass trie;

    int discardThreshold = 8;
    public HashMap<Character, Integer> swipedKeysTime = new HashMap<Character, Integer>();
    public ArrayList<Character> swipedKeys = new ArrayList<Character>();

    public Swiping(Dictionary _dict, TrieClass _trie)
    {
        dict = _dict;
        trie = _trie;
    }

    public void filterChars(ArrayList<Character> allCharsSwiped)
    {

    }

    public String decideWordAfterSwipe(PriorityQueue<Word> expectedWords)
    {
        ArrayList<Character> currenChildren = trie.getCharChildren("");
        int index = 0;
        String finalWord = "";

        int n = swipedKeys.size();
        if (n > 2)
        {
            ArrayList<Character> intermediateList = new ArrayList<Character>();
            int IndexToStartFrom = -1;
            ArrayList<Character> firstCharChildren = trie.getCharChildren(swipedKeys.get(0).toString());
            for (int i = 1; i < n - 1; i++)
            {
                if (firstCharChildren.contains(swipedKeys.get(i)))
                {
                    IndexToStartFrom = i;
                    break;
                }

            }
            if (IndexToStartFrom != -1)
            {
                for (int i = IndexToStartFrom; i < n - 1; i++)
                {
                    if(swipedKeysTime.get(swipedKeys.get(i)) > discardThreshold)
                    {
                        intermediateList.add(swipedKeys.get(i));
                    }
                }
            }
            ArrayList<ArrayList<Character>> allCombs = GetAllCombination(intermediateList, intermediateList.size());//, r);

            //PriorityQueue<Word> expectedWords = new PriorityQueue<Word>();
            Character FirstChar = swipedKeys.get(0);
            Character LastChar = swipedKeys.get(n - 1);
            String FirstWord = FirstChar.toString() + LastChar.toString();
            if (FirstWord.equals(trie.getMatchingPrefix(FirstWord)))
            {
                Integer wordVal = swipedKeysTime.get(FirstChar) + swipedKeysTime.get(LastChar);
                expectedWords.add(new Word(FirstWord, wordVal));
            }

            HashSet<String> currentAddedWords = new HashSet<String>();
            for (ArrayList<Character> currentComb : allCombs)
            {
                StringBuilder currentWord = new StringBuilder();
                currentWord.append(FirstChar);
                for (Character currentChr : currentComb)
                {
                    currentWord.append(currentChr);
                }
                currentWord.append(LastChar);
                if (!currentAddedWords.contains(currentWord.toString()) && currentWord.toString().equals(trie.getMatchingPrefix(currentWord.toString())))
                {
                    Integer wordVal = 0;
                    wordVal = swipedKeysTime.get(FirstChar) + swipedKeysTime.get(LastChar);
                    for (Character currentChr : currentComb)
                    {
                        wordVal += swipedKeysTime.get(currentChr);
                    }
                    expectedWords.add(new Word(currentWord.toString(), wordVal));
                    currentAddedWords.add(currentWord.toString());
                }
            }
            finalWord = expectedWords.poll().word;

        }
        else
        {
            Character FirstChar = swipedKeys.get(0);
            Character LastChar = swipedKeys.get(n - 1);
            finalWord = FirstChar.toString() + LastChar.toString();

        }

        return finalWord;
        //String finalWord = trie.getMatchingPrefix(expectedWord.toString());
        /*String newString;
        String oldString = outputdisplay.getText();
        newString = oldString.substring(0, oldString.length() - 1) + finalWord + " _";
        outputdisplay.setText(newString);*/
    }

    ArrayList<ArrayList<Character>> GetAllCombination(ArrayList<Character> arr, int n)//, int r)
    {
        ArrayList<ArrayList<Character>> allCombs = new ArrayList<ArrayList<Character>>();
        int Threshold = arr.size();
        if (arr.size() > 13)
        {
            Threshold = 13;
        }
        for (int r = 1; r <= Threshold; r++)
        {
            // A temporary array to store all combination one by one
            ArrayList<Character> data = new ArrayList<Character>();
            for (int i = 0; i < r; i++)
            {
                data.add(' ');
            }

            // Print all combination using temprary array 'data[]'
            combinationUtil(arr, data, 0, n - 1, 0, r, allCombs);
        }
        return allCombs;
    }

    void mouseMoved(Key curKey)
    {
        if(swipedKeys.size() > 0)
        {
            Character LastChar = swipedKeys.get(swipedKeys.size() - 1);
            Character CurrentChar = curKey.getText().charAt(0);
            if(LastChar.equals(CurrentChar))
            {

            }
            else
            {
                swipedKeys.add(curKey.getText().charAt(0));
                swipedKeysTime.put(curKey.getText().charAt(0),1);
            }

        }
        if (swipedKeysTime.containsKey(curKey.getText().charAt(0)) && swipedKeys.get(swipedKeys.size() - 1).equals(curKey.getText().charAt(0)))
        {
            Integer intCount = swipedKeysTime.get(curKey.getText().charAt(0));
            intCount++;
            swipedKeysTime.replace(curKey.getText().charAt(0),intCount);
        }
        else if(!swipedKeysTime.containsKey(curKey.getText().charAt(0)))
        {
            swipedKeysTime.put(curKey.getText().charAt(0),1);
            swipedKeys.add(curKey.getText().charAt(0));
        }
    }

    void combinationUtil(ArrayList<Character> arr, ArrayList<Character> data, int start,
                         int end, int index, int r,ArrayList<ArrayList<Character>> ALLComb)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            ArrayList<Character> currentArr = new ArrayList<Character>();
            for (int j=0; j<r; j++)
            {
                currentArr.add(data.get(j));
                //System.out.print(data.get(j) + " ");

            }
            ALLComb.add(currentArr);
            //System.out.println("");
            return;
        }

        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data.set(index,arr.get(i));
            combinationUtil(arr, data, i+1, end, index+1, r,ALLComb);
        }
    }
}
