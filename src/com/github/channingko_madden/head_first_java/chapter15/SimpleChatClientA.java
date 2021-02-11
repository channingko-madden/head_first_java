package com.github.channingko_madden.head_first_java.chapter15;

/** @brief SimpleChatClient that can only send messages, not receive them */

import java.io.*;
import java.net.*;
import javax.swing.*; // gui stuff
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA
{
	JTextField outgoing;
	PrintWriter writer;
	Socket socket;

	// make gui, register listener with send button, call setUpNetworking()
	public void go()
	{
		JFrame frame = new JFrame("Simple Chat Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		outgoing = new JTextField(20); // 20 column text field

		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());

		JPanel mainPanel = new JPanel();
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		setUpNetworking();
		frame.setSize(400, 500);
		frame.setVisible(true);
		
	}

	// make Socket, then make PrintWriter and assign to writer variable
	private void setUpNetworking()
	{
		try
		{
			socket = new Socket("127.0.0.1", 5000);
			writer = new PrintWriter(socket.getOutputStream());
			System.out.println("networking established");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	// Listen to send button event
	public class SendButtonListener implements ActionListener
	{
		// Get text from the text field and send it to server
		public void actionPerformed(ActionEvent ev)
		{
			try
			{
				writer.println(outgoing.getText());
				writer.flush();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus(); // moves cursor back
		}
	}

	public static void main(String[] args)
	{
		new SimpleChatClientA().go();
	}
}
