/** @brief Part of the Universal Service Browser
 *  Lets user "roll [1-5] number of 6 sided dice" 
 *
 *  "Sharpen Your Pencil" task is to improve this Dice service. For example, make the dice graphical (Use a rectangle
 *   and draw the appropriate number of circles on each one, corresponding to the roll for that particular die*/

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser;

import javax.swing.*;
import java.awt.*; //for event stuff
import java.awt.event.*;
import java.io.*;

public class DiceService implements Service
{
	private static final long serialVersionUID = 1L;
	JLabel mLabel;
	JComboBox<String> mNumOfDice;
	JPanel mDicePanel;

	public JPanel getGuiPanel()
	{
		mDicePanel = new JPanel();
		JPanel panel = new JPanel(new GridBagLayout());
		JButton button = new JButton("Roll!"); // Create a button that user presses to roll the dice
		String[] choices = {"1", "2", "3", "4", "5"}; // # of dice choices for JComboBox
		mNumOfDice = new JComboBox<String>(choices);
		mLabel = new JLabel("Dice values here"); // Label used to display dice roll values in a single formatted single
		button.addActionListener(new RollListener());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		panel.add(mNumOfDice, constraints);
		constraints.gridx = 1;
		panel.add(button, constraints);
		//panel.add(mLabel);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.0;
		constraints.gridwidth = 2;
		panel.add(mDicePanel, constraints);
		return panel;
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
		}
	}

	/** @brief Create a JPanel that contains an image of a dice of the roll parameter
	 *  @param[in] roll Rice roll (1-6 value)
	 *  @return Dice JPanel to add to another panel */
	public class DicePanel extends JPanel 
	{
		private static final long serialVersionUID = 1L;
		private int mRoll;

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

		
		private void drawDots(Graphics g)
		{
			g.setColor(Color.black);
			int dot_height = (this.getHeight() - 8) / 3;
			int dot_width = (this.getWidth() - 8) / 3;
			if(mRoll == 1)
			{
				g.fillOval((this.getWidth() - dot_width)/2, (this.getHeight() - dot_height)/2, dot_width, dot_height);
			}
			
		}

	}
}
