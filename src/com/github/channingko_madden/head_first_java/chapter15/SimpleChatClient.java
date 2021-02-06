/** @brief SimpleChatClient that can only send messages, not receive them */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*; // gui stuff
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA
{
	JTextField outgoing;
	PrintWriter writer;
	Socket socket;
	JTextArea incoming; //incoming messages text area
	BufferedReader reader;

	// make gui, register listener with send button, call setUpNetworking()
	public void go()
	{
		JFrame frame = new JFrame("Simple Chat Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		incoming = new JTextArea(15, 50); // 15 rows, 50 columns
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false); // user can't write here, only read messages
		JScrollPane qScroller = new JScrollPane(incoming); // can scroll incoming messages
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		outgoing = new JTextField(20); // 20 column text field

		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());

		JPanel mainPanel = new JPanel(); // default flow layout
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);

		setUpNetworking();

		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(400, 500);
		frame.setVisible(true);
		
	}

	// make Socket, then make PrintWriter and assign to writer variable
	private void setUpNetworking()
	{
		try
		{
			socket = new Socket("127.0.0.1", 5000);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
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

	public class IncomingReader implements Runnable
	{
		public void run()
		{
			String message;
			try
			{
				while((message = reader.readLine()) != null)
				{
					System.out.println("read " + message);
					incoming.append(message + "\n");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}

			}
		}
	}

	public static void main(String[] args)
	{
		new SimpleChatClient().go();
	}
}
