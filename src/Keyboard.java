/*
 * Sample code for CS 2610 Homework 1
 *
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Collections.*;
import java.util.PriorityQueue;
public class Keyboard{

	JFrame window;
	Key[] keys;
	JPanel board; //for loading buttons
	JTextField input;
	JTextField outputdisplay;
	Container panel;
	JPanel suggestedWords;
	MouseListener mouselistener;
	Highlighter highlighterOutput;
	Highlighter highlighterPrompt;
	HighlightPainter sub, add, delete;

	Color oldValue = Color.white;

	private static boolean USE_CROSS_PLATFORM_UI = true;
    static Dictionary dict;
    static TrieClass trie;
	static Swiping swiper;
    static ArrDictionary_2 arrDict;

	public Keyboard() throws FileNotFoundException{
		if(USE_CROSS_PLATFORM_UI) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		window = new JFrame("keyboard");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600,400);

		//set the keyboard pad layout
		board = new JPanel();
		Border border = BorderFactory.createEmptyBorder(0,10,20,10);
		board.setBorder(border);
		//infomation display

		input = new JTextField("A QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
		input.setFont(new Font("Serif", Font.PLAIN, 16));
		input.setBackground(new Color(237,237,237));
		input.setEditable(false);
		Border title = BorderFactory.createTitledBorder("Input: ");
		Border bevel = BorderFactory.createLoweredBevelBorder();
		Border border1 = BorderFactory.createEmptyBorder(0,10,5,10);
		Border border2 = BorderFactory.createEmptyBorder(10,10,5,10);
		Border border3 = BorderFactory.createCompoundBorder(border2, bevel);
		Border border4 = BorderFactory.createCompoundBorder(border3, title);
		input.setBorder(BorderFactory.createCompoundBorder(border4, border1));

		//modify input sample border
		suggestedWords = new JPanel();
		outputdisplay = new JTextField("_");
		highlighterOutput = outputdisplay.getHighlighter();
		highlighterPrompt = input.getHighlighter();
		mouselistener = new MouseListener();

		Border title3 = BorderFactory.createTitledBorder("Suggested Words: ");
		Border title2 = BorderFactory.createTitledBorder("Output: ");
		Border border5 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border6 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border7 = BorderFactory.createCompoundBorder(title2,border6);
		Border border8 = BorderFactory.createCompoundBorder(title3,border6);

		outputdisplay.setBorder(BorderFactory.createCompoundBorder(border5,border7));
		outputdisplay.setForeground(Color.blue);
		outputdisplay.setFont(new Font("Serif", Font.PLAIN, 16));

		suggestedWords.setBorder(BorderFactory.createCompoundBorder(border5,border8));
		suggestedWords.setForeground(Color.blue);
		suggestedWords.setFont(new Font("Serif", Font.PLAIN, 16));



		board.setLayout(new GridBagLayout());
		//set the buttons:
		int[] keyNum =  {10,9,7};
		keys = new Key[29];
		String[] keyLabels ={"QWERTYUIOP","ASDFGHJKL","ZXCVBNM"}; //change to keyboard setting

        int index = 0;
		//first line
		for (int i = 0; i < keyNum[0]; i++){ //first line of keys
			String label = keyLabels[0].substring(i, i+1);
			keys[index] = new Key(label);
			keys[index].setName(label);
			keys[index].setFocusPainted(false);
			keys[index].addMouseListener(mouselistener);
			keys[index].addMouseMotionListener(mouselistener);
			addKey(board,keys[index],i+1,0,1,1);
            index++;
		}

		//second line
		for (int i = 0; i < keyNum[1]; i++){ //second line of keys
			String label = keyLabels[1].substring(i, i+1);
			keys[index] = new Key(label);
			keys[index].setName(label);
			keys[index].setFocusPainted(false);
			keys[index].addMouseListener(mouselistener);
			keys[index].addMouseMotionListener(mouselistener);
			addKey(board,keys[index],i+1,1,1,1);
            index++;
		}

		for (int i = 0;i< keyNum[2]; i++){ //third line of keys
			String label = keyLabels[2].substring(i, i+1);
			keys[index] = new Key(label);
			keys[index].setName(label);
			keys[index].setFocusPainted(false);
			keys[index].addMouseListener(mouselistener);
			keys[index].addMouseMotionListener(mouselistener);
			addKey(board,keys[index],i+2,2,1,1);
            index++;
		}

		//set the space button
		keys[26] = new Key(" ");
		keys[26].setName(" ");
		keys[26].setFocusPainted(false);
		keys[26].addMouseListener(mouselistener);
		keys[26].addMouseMotionListener(mouselistener);
		addKey(board,keys[26],2,3,7,1);

		//set the backspace button
		keys[27] = new Key("<--");
		keys[27].setName("backspace");
		keys[27].setFocusPainted(false);
		keys[27].addMouseListener(mouselistener);
		keys[27].addMouseMotionListener(mouselistener);
		addKey(board,keys[27],10,3,2,1);

        //set the word undo button
        keys[28] = new Key("<<");
        keys[28].setName("wordundo");
        keys[28].setFocusPainted(false);
        keys[28].addMouseListener(mouselistener);
        keys[28].addMouseMotionListener(mouselistener);
        addKey(board,keys[28],10,2,2,1);

		panel = window.getContentPane();
		//use gridBag layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.anchor= GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(input,c);

		c.gridx = 0;
		c.gridy = 1;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(suggestedWords,c);

		c.gridx = 0;
		c.gridy = 2;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(outputdisplay,c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 4;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,10,10,10);
		panel.add(board,c);

		window.pack();// adjust the window size
		window.setVisible(true);
	}

	public void addKey(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.ipady = 30;
		c.ipadx = 30;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
        component.setBackground(Color.white);
		container.add(component, c);
	}

	class MouseListener extends MouseAdapter implements MouseMotionListener{
		boolean tracing;			// whether the input method is button clicking or tracing
		ArrayList<Key> tracelist;	// a list to store all buttons on the trace
		Key curKey;

		MouseListener() throws FileNotFoundException{
			super();
			tracing = false;
			tracelist = new ArrayList<Key>();
			curKey = new Key("");
			sub = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
			add = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
			delete = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
		}

		private void updateOutput (Key theEventer){
			suggestedWords.removeAll();
			window.setVisible(true);
            for(Key pressedKey: keys)
            {
                if (pressedKey == null)
                {
                    continue;
                }
                pressedKey.setBackground(Color.white);
            }

			String theChar = theEventer.getText();
            String newString = "";
            if(theChar.equals("<--"))
            {
                String oldString = outputdisplay.getText();
                if(oldString.length() == 1)
                {
                    newString = oldString;
                }
                else
                {
                    newString = oldString.substring(0, oldString.length() - 2) + "_";
                }
                outputdisplay.setText(newString);
            }
            else if (theChar.equals("<<"))
            {
            	String oldString = outputdisplay.getText();
                if(outputdisplay.getText().lastIndexOf(' ') == -1)
                {
                    newString = "_";
                }
                else
                {
                    newString = oldString.substring(0, oldString.lastIndexOf(' ')) + "_";
                }
                outputdisplay.setText(newString);
            }
            else
            {
                String oldString = outputdisplay.getText();
                newString = oldString.substring(0, oldString.length() - 1) + theChar + "_";
                outputdisplay.setText(newString);
            }
            String[] allWords = newString.split(" ");
            String currentPrefix = allWords[allWords.length-1].replace("_","");
						checkWords(allWords);
            if(currentPrefix.equals(" ") || currentPrefix.equals(""))
						{
							return;
						}
            ArrayList<Character> nextChars = trie.getCharChildren(currentPrefix);

            for(Key pressedKey: keys)
            {
                if (pressedKey == null)
                {
                    continue;
                }
                if (pressedKey.getText().length() == 1 && !pressedKey.getText().equals(" "))
                {
                    Character keyName = pressedKey.getText().charAt(0);
                    if (nextChars.contains(keyName))
                    {
                        pressedKey.setBackground(new Color(102, 178, 225));
                    }
                }
            }
		}

		public void checkWords(String[] allWords){
			if (allWords == null || allWords.length ==0)
			{
				return;
			}
			highlighterPrompt.removeAllHighlights();
			String[] splitPrompt = input.getText().split(" ");
			int wordOffsetOutput = 0;
			int wordOffsetPrompt = 0;
			for(int i = 0; i < allWords.length-1 && i < splitPrompt.length; i++){
				int[][] highlights = levDist(allWords[i], splitPrompt[i]);
				int[] outputHighlights = highlights[0];
				int[] promptsHighlights = highlights[1];
				for(int j = 0; j < outputHighlights.length; j++){
					if(outputHighlights[j] > 0){
						try{
							int color = outputHighlights[j];
							if(color == 1){
								highlighterOutput.addHighlight(j + wordOffsetOutput, j + wordOffsetOutput + 1, sub);
							}
							else if(color == 2){
								highlighterOutput.addHighlight(j + wordOffsetOutput, j + wordOffsetOutput + 1, delete);
							}
							else{
								highlighterOutput.addHighlight(j + wordOffsetOutput, j + wordOffsetOutput + 1, add);
							}
						} catch (BadLocationException e) {
							System.out.println("Bad location: " + e);
						}
					}
				}
				for(int j = 0; j < promptsHighlights.length; j++){
					if(promptsHighlights[j] > 0){
						try{
							int color = promptsHighlights[j];
							if(color == 1){
								highlighterPrompt.addHighlight(j + wordOffsetPrompt, j + wordOffsetPrompt + 1, sub);
							}
							else if(color == 2){
								highlighterPrompt.addHighlight(j + wordOffsetPrompt, j + wordOffsetPrompt + 1, delete);
							}
							else{
								highlighterPrompt.addHighlight(j + wordOffsetPrompt, j + wordOffsetPrompt + 1, add);
							}
						} catch(BadLocationException e){
							System.out.println("Bad location: " + e);
						}
					}
				}
				wordOffsetOutput += allWords[i].length() + 1;
				wordOffsetPrompt += splitPrompt[i].length() + 1;
			}
		}

		public int[][] levDist(String input, String prompt){
			Cell[][] A = new Cell[input.length()+1][prompt.length()+1];
			char[] inputs = input.toCharArray();
			char[] prompts = prompt.toCharArray();
			for(int i = 0; i <= inputs.length; i++){
				A[i][0] = new Cell(i, -1, -1);
			}

			for(int j = 0; j <= prompts.length; j++){
				A[0][j] = new Cell(j, -1, -1);
			}

			for(int i = 1; i <= inputs.length; i++){
				for(int j = 1; j <= prompts.length; j++){
					int cost = 0;
					if(inputs[i-1] != prompts[j-1]){
						cost = 1;
					}
					int delete = A[i-1][j].value + 1;
					int substitute = A[i-1][j-1].value + cost;
					int add = A[i][j-1].value + 1;
					if(delete < substitute && delete < add){
						A[i][j] = new Cell(delete, i-1, j);
					}
					else if(add < delete && add < substitute){
						A[i][j] = new Cell(add, i, j-1);
					}
					else{
						A[i][j] = new Cell(substitute, i-1, j-1);
					}
				}
			}

			int[] inputHighlights = new int[inputs.length];
			int[] promptsHighlights = new int[prompts.length];
			int row = inputs.length;
			int column = prompts.length;
			while(row != 0 && column != 0){
				Cell current = A[row][column];
				int prevRow = current.prevX;
				int prevColumn = current.prevY;
				if(current.value != A[prevRow][prevColumn].value){
					if(row != prevRow && column != prevColumn){
						//Case where we did a substitution
						inputHighlights[prevRow] = 1;
						promptsHighlights[prevColumn] = 1;
					}
					else if(row != prevRow){
						inputHighlights[prevRow] = 2;
					}
					else{
						promptsHighlights[prevColumn] = 3;
					}
				}
				row = prevRow;
				column = prevColumn;
			}

			while(row != 0){
				inputHighlights[row-1] = 2;
				row--;
			}

			while(column != 0){
				promptsHighlights[column-1] = 3;
			}

			int[][] highlights = new int[2][];
			highlights[0] = inputHighlights;
			highlights[1] = promptsHighlights;
			return highlights;
		}

		private void recoverState(){
			//when mouse is released, tracing is ended. reset the letter state in the tmptlist
			//change status
			suggestedWords.removeAll();
			window.setVisible(true);

			PriorityQueue<Word> expectedWords = new PriorityQueue<Word>();
			String finalWord = swiper.decideWordAfterSwipe(expectedWords);

			//float SentenceScore = swiper.scoreSentence(outputdisplay.getText().replace("_","").trim() + " " + finalWord);

			while (expectedWords.size() > 0)
			{
				JButton current = new JButton(expectedWords.poll().word);
				current.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String buttonText = current.getText();
						String oldString = outputdisplay.getText();
						int index = oldString.lastIndexOf(" ");
						if (index > 0)
						{
							oldString = oldString.substring(0, index);
							index = oldString.lastIndexOf(" ");
							if (index > 0)
							{
								oldString = oldString.substring(0, index) + " ";
							}
							else
							{
								oldString = "";
							}
						}
						String newString = oldString + buttonText + " _";
						outputdisplay.setText(newString);
						suggestedWords.removeAll();
						checkWords(newString.split(" "));
						window.setVisible(true);
					}
				});
				suggestedWords.add(current);
				window.setVisible(true);
			}

			String newString;
			String oldString = outputdisplay.getText();
			newString = oldString.substring(0, oldString.length() - 1) + finalWord + " _";
			outputdisplay.setText(newString);
			String[] allWords = newString.split(" ");
			checkWords(allWords);

			swiper.swipedKeys.clear();
			swiper.swipedKeysTime.clear();
			while (!tracelist.isEmpty()){
				Key e = tracelist.get(0);

				e.LineList.clear();
				e.PointList.clear();
				e.setBackground(Color.white);
				e.repaint();
				tracelist.remove(0);

			}
		}

		// The main function that prints all combinations of size r
		// in arr[] of size n. This function mainly uses combinationUtil()

		@Override
		public void mouseDragged(MouseEvent e)
        {
            Key theEventer = (Key) e.getSource();

            if (tracing == false)
            {//start tracing
                curKey = theEventer;
                tracelist.add(theEventer);
                tracing = true;
                theEventer.PointList.add(e.getPoint());
            }
            else
            {
                Point2D p = e.getPoint();
                int x = (int) p.getX() - (curKey.getX() - theEventer.getX());
                int y = (int) p.getY() - (curKey.getY() - theEventer.getY());
                Point newPoint = new Point(x, y);
                //System.out.println("Mouse position:(" + (curKey.getX() + x) + "," + (curKey.getY() + y) + "), In key " + curKey.getText() + ".");
                if (curKey.getText().charAt(0) != ' ')
                {
					swiper.mouseMoved(curKey);
                }

                curKey.PointList.add(newPoint);
                curKey.repaint();
            }
        }

		@Override
		public void mouseClicked(MouseEvent e) {
			// Feedback for Key Press
			Key theEventer = (Key) e.getSource();
			oldValue = theEventer.getBackground();
			theEventer.setBackground(new Color(165,165,165));
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (tracing) {
				Key theEventer = (Key) e.getSource();
				curKey = theEventer;
				tracelist.add(theEventer);
				theEventer.setFocusPainted(true);

	            //start the mouse trace in this button
	            theEventer.PointList.add(e.getPoint());
			}
			else
			{
				Key theEventer = (Key) e.getSource();
				oldValue = theEventer.getBackground();
				theEventer.setBackground(new Color(210,210,210));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(tracing){
				Key theEventer = (Key) e.getSource();
				theEventer.setFocusPainted(false);
                theEventer.LineList.add(theEventer.PointList);
                theEventer.PointList = new ArrayList<Point>();
			}
			else
			{
				Key theEventer = (Key) e.getSource();
				theEventer.setBackground(oldValue);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();
			// 	remove box around letter for key press
			//theEventer.setFocusPainted(true);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();//e is the same source as pressed
			if (tracing == false) {
				updateOutput(theEventer);
				System.out.println("Input key " + theEventer.getText());
			} else {
				tracing = false;
				System.out.println("Tracing Completes. Clear all traces.");
                for(Character keyChar : swiper.swipedKeys)
                {
                    System.out.println(keyChar + " : " + swiper.swipedKeysTime.get(keyChar));
                }
				recoverState();
			}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
        dict = new Dictionary();
        trie = new TrieClass();
        arrDict = new ArrDictionary_2();
        // arrDict.getLetters(char f, char l) & returns a String of the possible letters

        for(String wordStr : dict.wtable)
        {
            trie.insert(wordStr);
        }
		swiper = new Swiping(dict, trie,arrDict, ".\\model_bin.bin");
        Keyboard gui = new Keyboard();
	}
}
