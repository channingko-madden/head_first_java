package com.github.channingko_madden.head_first_java.beatbox;

/**
 * @brief The Server for the BeatBox, which facilitates sharing of beat patterns
 * Server runs on port 4242
 * @author channing.ko-madden
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class MusicServer {
	
	ArrayList<ObjectOutputStream> mClientOutputStreams;

	public static void main(String[] args)
	{
		new MusicServer().go();
	}
	
	
	/*
	 * @brief Thread class that handles a new client connection.
	 * Reads the serialized String and boolean[] objects sent by client,
	 * and then sends them to all connected clients (including the sender).
	 */
	public class ClientHandler implements Runnable
	{
		ObjectInputStream in;
		Socket clientSocket;
		
		public ClientHandler(Socket socket)
		{
			try
			{
				clientSocket = socket;
				in = new ObjectInputStream(clientSocket.getInputStream());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		public void run()
		{
			Object o1 = null; // To hold String
			Object o2 = null; // To hold boolean[]
			try
			{
				while( (o1 = in.readObject()) != null) // String
				{
					o2 = in.readObject(); // beat pattern
					
					System.out.println("Read two objects");
					tellEveryone(o1, o2);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

		/**
		 * Iterate through all clients and send the String and boolean[] objects to 
		 * each one using an ObjectOutputStream
		 * @param one String object
		 * @param two boolean[] object
		 */
		private void tellEveryone(Object one, Object two)
		{
			Iterator<ObjectOutputStream> it = mClientOutputStreams.iterator();
			while(it.hasNext())
			{
				try
				{
					ObjectOutputStream out = it.next();
					out.writeObject(one);
					out.writeObject(two);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void go()
	{
		mClientOutputStreams = new ArrayList<ObjectOutputStream>();
		System.out.println("MusicServer started");
		try
		{
			ServerSocket serverSock = new ServerSocket(4242);
			while(true)
			{
				Socket clientSocket = serverSock.accept();
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				mClientOutputStreams.add(out);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				
				System.out.println("Got a client connection");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
