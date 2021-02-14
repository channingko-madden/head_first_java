/** @brief Part of the Universal Service Browser
 *  Lets user "roll [1-5] number of 6 sided dice" 
 *
 *  "Sharpen Your Pencil" task is to improve this Dice service. For example, make the dice graphical (Use a rectangle
 *   and draw the appropriate number of circles on each one, corresponding to the roll for that particular die*/

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser;

import javax.swing.*;
import java.awt.*; //for event stuff
import java.awt.event.*;

public class DiceService implements Service
{
	private static final long serialVersionUID = 1L;
	JLabel mLabel;
	JComboBox<String> mNumOfDice;
	JPanel mDicePanel;
	JPanel mServicePanel;

	public JPanel getGuiPanel()
	{
		mDicePanel = new JPanel();
		mDicePanel.setPreferredSize(new Dimension(300, 60));
		mServicePanel = new JPanel(new GridBagLayout());
		JButton button = new JButton("Roll!"); // Create a button that user presses to roll the dice
		button.setPreferredSize(new Dimension(100, 25));
		String[] choices = {"1", "2", "3", "4", "5"}; // # of dice choices for JComboBox
		mNumOfDice = new JComboBox<String>(choices);
		mNumOfDice.setPreferredSize(new Dimension(100, 25));
		mLabel = new JLabel("Dice values here"); // Label used to display dice roll values in a single formatted single
		button.addActionListener(new RollListener());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		mServicePanel.add(mNumOfDice, constraints);
		constraints.gridx = 1;
		mServicePanel.add(button, constraints);
		//panel.add(mLabel);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.0;
		constraints.gridwidth = 2;
		mServicePanel.add(mDicePanel, constraints);
		return mServicePanel;
	}

	/** @brief Listens to "Roll" button presses */
	public class RollListener implements ActionListener
	{
		/** @brief Roll the device when button is pressed */
		public void actionPerformed(ActionEvent ev) 
		{
			String diceOutput = "";
			String selection = (String) mNumOfDice.getSelectedItem();
			int numOfDiceToRoll = Integer.parseInt(selection);
			mDicePanel.removeAll(); 
			for(int i = 0; i < numOfDiceToRoll; i++)
			{
				int r = (int) (Math.random() * 6) + 1;
				diceOutput += (" " + r);
				mDicePanel.add(new DicePanel(r));
			}
			mDicePanel.validate();
			mDicePanel.repaint();
			mLabel.setText(diceOutput);
			mServicePanel.repaint();
		}
	}

	/** @brief Create a JPanel that contains an image of a dice of the roll parameter
	 *  @param[in] roll Rice roll (1-6 value)
	 *  @return Dice JPanel to add to another panel */
	public class DicePanel extends JPanel 
	{
		private static final long serialVersionUID = 1L;
		private int mRoll;
		private final int mSpace = 2;

		public DicePanel(int roll)
		{
			mRoll = roll;
		}

		public Dimension getPreferredSize()
		{
			return new Dimension(50, 50); // encapsulates width and height of a component
		}

		/** @brief Override JPanel method, is also called when repaint() is called */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			drawDots(g);
		}

		
		/** @brief A Dice is essentially a 3x3 grid of dots
		 *  Use private methods to construct a dice face by painting each dot */
		private void drawDots(Graphics g)
		{
			g.setColor(Color.black);
			int dot_height = (this.getHeight() - (4 * mSpace)) / 3;
			int dot_width = (this.getWidth() - (4 * mSpace)) / 3;
			if(mRoll == 1)
			{
				drawGridDot(g, dot_width, dot_height, mSpace, 1, 1);
			}
			else if(mRoll == 2)
			{
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 0);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 2);
			}
			else if(mRoll == 3)
			{
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 0);
				drawGridDot(g, dot_width, dot_height, mSpace, 1, 1);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 2);
			}
			else if(mRoll == 4)
			{
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 0);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 0);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 2);
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 2);
			}
			else if(mRoll == 5)
			{
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 0);
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 2);
				drawGridDot(g, dot_width, dot_height, mSpace, 1, 1);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 2);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 0);
			}
			else if(mRoll == 6)
			{
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 0);
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 1);
				drawGridDot(g, dot_width, dot_height, mSpace, 0, 2);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 0);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 1);
				drawGridDot(g, dot_width, dot_height, mSpace, 2, 2);
			}
		}
		

		/** @brief A Dice is a 3x3 grid of dots
		 *  Draw a dot at a particular row/column location
		 *  @param[in] g Graphics to draw on
		 *  @param[in] width Width of Dot
		 *  @param[in] height Height of Dot
		 *  @param[in] space Space between dots
		 *  @param[in] row [0-2] Row index
		 *  @param[in] column [0-2] Column index */
		private void drawGridDot(Graphics g, int width, int height, int space, int row, int column)
		{

			int start_x = (this.getWidth() - width)/2 - space - width;
			int start_y = (this.getHeight() - height)/2 - space - height;
			g.fillOval(start_x + row * (width + space), start_y + column * (height + space), width, height);
		}
		
	}
}
