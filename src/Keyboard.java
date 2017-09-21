/*
 * Sample code for CS 2610 Homework 1
 *
 */

import javax.swing.*;
import javax.swing.border.Border;

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
	JLabel outputdisplay;
	Container panel;
	JPanel suggestedWords;
	MouseListener mouselistener;

	private static boolean USE_CROSS_PLATFORM_UI = true;
    static Dictionary dict;
    static TrieClass trie;
	static Swiping swiper;
	public Keyboard() throws FileNotFoundException{
		if(USE_CROSS_PLATFORM_UI) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		mouselistener = new MouseListener();
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
		outputdisplay = new JLabel("_");
		Border title2 = BorderFactory.createTitledBorder("Output: ");
		Border border5 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border6 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border7 = BorderFactory.createCompoundBorder(title2,border6);
		outputdisplay.setBorder(BorderFactory.createCompoundBorder(border5,border7));
		outputdisplay.setForeground(Color.blue);
		outputdisplay.setFont(new Font("Serif", Font.PLAIN, 16));

		Border title3 = BorderFactory.createTitledBorder("Suggeted Words: ");
		Border border8 = BorderFactory.createCompoundBorder(title3,border6);
		suggestedWords.setBorder(BorderFactory.createCompoundBorder(border5,border8));
		suggestedWords.setForeground(Color.blue);
		suggestedWords.setFont(new Font("Serif", Font.PLAIN, 16));

		board.setLayout(new GridBagLayout());
		//set the buttons:
		int[] keyNum =  {10,9,7};
		keys = new Key[28];
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

		//set the space button
		keys[27] = new Key("<--");
		keys[27].setName("backspace");
		keys[27].setFocusPainted(false);
		keys[27].addMouseListener(mouselistener);
		keys[27].addMouseMotionListener(mouselistener);
		addKey(board,keys[27],10,3,2,1);

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
		}

		private void updateOutput (Key theEventer){
			suggestedWords.removeAll();
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
            else
            {
                String oldString = outputdisplay.getText();
                newString = oldString.substring(0, oldString.length() - 1) + theChar + "_";
                outputdisplay.setText(newString);
            }
            String[] allWords = newString.split(" ");
            String currentPrefix = allWords[allWords.length-1].replace("_","");
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
                        pressedKey.setBackground(Color.green);
                    }
                }
            }
		}

		private void recoverState(){
			//when mouse is released, tracing is ended. reset the letter state in the tmptlist
			//change status
			suggestedWords.removeAll();

			PriorityQueue<Word> expectedWords = new PriorityQueue<Word>();
			String finalWord = swiper.decideWordAfterSwipe(expectedWords);

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
						outputdisplay.setText(oldString + buttonText + " _");
						suggestedWords.removeAll();
					}
				});
				suggestedWords.add(current);
			}
			String newString;
			String oldString = outputdisplay.getText();
			newString = oldString.substring(0, oldString.length() - 1) + finalWord + " _";
			outputdisplay.setText(newString);

			swiper.swipedKeys.clear();
			swiper.swipedKeysTime.clear();
			while (!tracelist.isEmpty()){
				Key e = tracelist.get(0);

				e.LineList.clear();
				e.PointList.clear();
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
                System.out.println("Entering Mouse tracing mode");
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
			// TODO Auto-generated method stub
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
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();
			theEventer.setFocusPainted(true);
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

        for(String wordStr : dict.wtable)
        {
            trie.insert(wordStr);
        }
		swiper = new Swiping(dict, trie);
        Keyboard gui = new Keyboard();
	}
}
