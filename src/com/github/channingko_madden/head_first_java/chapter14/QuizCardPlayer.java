/** @brief Quiz Card Player pg 456 */

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardPlayer {

	private JTextArea display;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer; // default false

	public static void main (String[] args)
	{
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
	}

	// build gui
	public void go()
	{
		frame = new JFrame("Quiz Card Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();

		Font bigFont = new Font("sanserif", Font.BOLD, 24);
		display = new JTextArea(10,20);
		display.setFont(bigFont);
		display.setLineWrap(true);
		display.setEditable(false);
		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		nextButton = new JButton("Show Question");
		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640,500);
		frame.setVisible(true);
	}
	
	public class NextCardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			if(isShowAnswer)
			{
				// show the answer because they’ve seen the question
				display.setText(currentCard.getAnswer());
				nextButton.setText("Next Card");
				isShowAnswer = false;
			}
			else
			{
				// show the next question if there are more cards
				if (currentCardIndex < cardList.size())
				{
					showNextCard();
				}
				else
				{
					// there are no more cards! User has to reload cards to restart?
					display.setText("That was last card");
					nextButton.setEnabled(false); // disables the button
				}
			}
		}
	}

	public class OpenMenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
		}
	}

	private void loadFile(File file)
	{
		cardList = new ArrayList<QuizCard>(); // cards are only loaded by reading from file
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				makeCard(line); // parses line to create card and adds to ArrayList
			}
			reader.close();
		}
		catch(Exception ex)
		{
			System.out.println("couldn’t read the card file");
			ex.printStackTrace();
		}
		// now time to start by showing the first card
		currentCardIndex = 0;
		showNextCard();
		nextButton.setEnabled(true); // enables the button
	}

	private void makeCard(String lineToParse)
	{
		String[] result = lineToParse.split("/"); // This slash is the delimiter between the question and answer
		QuizCard card = new QuizCard(result[0], result[1]);
		cardList.add(card);
		System.out.println("made a card");
	}

	private void showNextCard()
	{
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++; // why not pop cards off?
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show Answer");
		isShowAnswer = true;
	}
}
	
