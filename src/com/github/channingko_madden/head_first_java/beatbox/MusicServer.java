package com.github.channingko_madden.head_first_java.beatbox;

/**
 * @brief The Server for the BeatBox, which faciliates sharing of beat patterns
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
			Object o2 = null;
			Object o1 = null;
			try
			{
				while( (o1 = in.readObject()) != null) // String
				{
					o2 = in.readObject(); // beat pattern
					
					System.out.println("Read two objedts");
					tellEveryone(o1, o2);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

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
