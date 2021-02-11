/** @brief Part of the Universal Service Browser
 *  User insert a day/month/year and is told what day of the week that day was/is/will be */

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class DayOfTheWeekService implements Service
{
	private static final long serialVersionUID = 1L;
	JLabel mOutputLabel;
	JComboBox<String> mMonthBox;
	JTextField mDayText;
	JTextField mYearText;

	public JPanel getGuiPanel()
	{
		JButton button = new JButton("AI TECH!");
		button.addActionListener(new DoItListener());
		mOutputLabel = new JLabel("Day of Week appears here");
		DateFormatSymbols dateStuff = new DateFormatSymbols();
		mMonthBox = new JComboBox<String>(dateStuff.getMonths());
		mDayText = new JTextField(8);
		mYearText = new JTextField(8);

		JPanel inputPanel = new JPanel(new GridLayout(3,2)); // 3 rows, two columns
		inputPanel.add(new JLabel("Month"));
		inputPanel.add(mMonthBox);
		inputPanel.add(new JLabel("Day"));
		inputPanel.add(mDayText);
		inputPanel.add(new JLabel("Year"));
		inputPanel.add(mYearText);

		JPanel panel = new JPanel();
		panel.add(inputPanel);
		panel.add(button);
		panel.add(mOutputLabel);
		return panel;
	}

	public class DoItListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			int monthNum = mMonthBox.getSelectedIndex();
			int dayNum = Integer.parseInt(mDayText.getText());
			int yearNum = Integer.parseInt(mYearText.getText());

			Calendar c = Calendar.getInstance();
			c.set(Calendar.MONTH, monthNum);
			c.set(Calendar.DAY_OF_MONTH, dayNum);
			c.set(Calendar.YEAR, yearNum);
			Date date = c.getTime();
			String dayOfWeek = (new SimpleDateFormat("EEEE")).format(date);
			mOutputLabel.setText(dayOfWeek);
		}
	}

}
