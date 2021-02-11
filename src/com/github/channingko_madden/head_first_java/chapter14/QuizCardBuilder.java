package com.github.channingko_madden.head_first_java.chapter14;

/** @brief Quiz card builder example pg 450
 *  Good example of MenuVar, Menu, and MenuItems */

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardBuilder {

	private JTextArea question; // Text areas used by inner classes to get user input
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;

	public static void main (String[] args)
	{
		QuizCardBuilder builder = new QuizCardBuilder();
		builder.go();
	}

	public void go()
	{
		cardList = new ArrayList<QuizCard>(); // initialize private member variable
		// build gui
		frame = new JFrame("Quiz Card Builder");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24); // FONT used for text areas
		question = new JTextArea(6,20); // Where user can write their question
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		JScrollPane qScroller = new JScrollPane(question); // Long questions, scroll to see it all
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JButton nextButton = new JButton("Next Card");
		JLabel qLabel = new JLabel("Question:");
		JLabel aLabel = new JLabel("Answer:");
		mainPanel.add(qLabel); // First add label to be title for scrollerPane + text area
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller);
		mainPanel.add(nextButton); // button on the bottom
		nextButton.addActionListener(new NextCardListener());


		JMenuBar menuBar = new JMenuBar(); // Menu bar is that "bar" at the top of window that has tabs for drop down menus(like file, edit, etc.)
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");

		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());

		fileMenu.add(newMenuItem); // MenuItems get added to Menu (makes sense)
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu); // Menu gets added to Menu Bar
		frame.setJMenuBar(menuBar); // special method for menu bar, so menu bar must always go in a certain place
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
	}

	public class NextCardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			clearCard();
		}
	}

	public class SaveMenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			QuizCard card = new QuizCard(question.getText(), answer.getText());
			cardList.add(card);
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame); // dialog shows up in the frame for user to use (has to use first I assume)
			saveFile(fileSave.getSelectedFile());
		}
	}

	public class NewMenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			cardList.clear();
			clearCard();
		}
	}

	private void clearCard()
	{
		question.setText("");
		answer.setText("");
		question.requestFocus(); //JComponent method: Requests that this component get the focus
	}

	private void saveFile(File file)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file)); // BufferedWriter writes text to an output stream
			for(QuizCard card:cardList)
			{
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
			}
			writer.close(); // flushes on close
		}
		catch(IOException ex)
		{
			System.out.println("couldn’t write the cardList out");
			ex.printStackTrace();
		}
	}
}
