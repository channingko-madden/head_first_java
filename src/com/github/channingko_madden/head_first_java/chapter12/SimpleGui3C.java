package com.github.channingko_madden.head_first_java.chapter12;

/** @brief Change color of circle each time button is pressed */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleGui3C implements ActionListener
{
	JFrame m_frame;

	public static void main(String[] args)
	{
		SimpleGui3C gui = new SimpleGui3C();
		gui.go();
	}

	public void go()
	{
		m_frame = new JFrame();
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton button = new JButton("Change colors");
		button.addActionListener(this);

		MyDrawPanel drawPanel = new MyDrawPanel();

		m_frame.getContentPane().add(BorderLayout.SOUTH, button);
		m_frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
		m_frame.setSize(300, 300);
		m_frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) //implement ActionListener
	{
		m_frame.repaint();
	}
}
