package com.github.channingko_madden.head_first_java.chapter15;

/** @brief Very simple chat server (pg 520)
 *  Works with both simple chat client examples
 *  Commenting code is a "Sharpen your pencil" task
 *  Not a robust server, book challenges you to come 
 *  back and make it more robust after you finish the book
 *
 *  compiling with javac, says there are unchecked or unsafe operations used!*/


import java.io.*;
import java.net.*;
import java.util.*;

public class VerySimpleChatServer
{
	private ArrayList<PrintWriter> clientOutputStreams; // no type needed?

	public class ClientHandler implements Runnable
	{
		BufferedReader reader; // used to read data from client
		Socket sock; // socket connection to a client
		
		/** @brief Constructor
		 *  @param[in] clientSocket socket connection to a client */
		public ClientHandler(Socket clientSocket)
		{
			try
			{
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

		/** @brief Reads messages from client and sends them to everyone */
		public void run()
		{
			String message;
			try
			{
				while ((message = reader.readLine()) != null)
				{
					System.out.println("read " + message);
					tellEveryone(message);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void go()
	{
		clientOutputStreams = new ArrayList<PrintWriter>(); // no type needed?

		try
		{
			ServerSocket serverSock = new ServerSocket(5000); // server at port 5000 on local host
			while(true)
			{
				Socket clientSocket = serverSock.accept(); // blocks until a client tries to connect
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				Thread t = new Thread(new ClientHandler(clientSocket)); 
				t.start(); // Not necessary to maintain a reference to Thread object
				System.out.println("got a connection");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

	/** @brief Send a message string to all connected clients
	 *  @param[in] message */
	public void tellEveryone(String message)
	{
		Iterator<PrintWriter> it = clientOutputStreams.iterator();

		while(it.hasNext()) // iterate through all client connections
		{
			try
			{
				PrintWriter writer = it.next();
				writer.println(message);
				writer.flush();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		} 
	} 

	public static void main (String[] args)
	{
		new VerySimpleChatServer().go();
	}
}
