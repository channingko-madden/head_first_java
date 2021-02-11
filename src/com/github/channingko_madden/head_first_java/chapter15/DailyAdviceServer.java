package com.github.channingko_madden.head_first_java.chapter15;

/** @brief DailyAdviceServer offers a service on the localhost
 * at port 4242, which sends a string to a client when a client connects
 * to the service  */

import java.io.*;
import java.net.*;

public class DailyAdviceServer
{
	private String[] adviceList = {"Take a long first sip, KAMPAI!", "KEKW", "You need a free vacation", 
		"You could totally go freelance", "What's my age again?", "NA > EU Kappa"};

	public void go()
	{
		try
		{
			// Client connects to port 4242. No need to add IP address b/c it's whatever is the 
			// address of the machine this is run on. Can throw an exception
			ServerSocket serverSock = new ServerSocket(4242);

			while(true) //ctrl-c will break this
			{
				Socket socket = serverSock.accept(); // blocking call

				PrintWriter writer = new PrintWriter(socket.getOutputStream()); // to write strings to client
				String advice = getAdvice();
				writer.println(advice);
				writer.close();
				System.out.println(advice);
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}

	}

	private String getAdvice()
	{
		int random = (int) (Math.random() * adviceList.length);
		return adviceList[random];
	}

	public static void main(String[] args)
	{
		DailyAdviceServer server = new DailyAdviceServer();
		server.go();
	}
}
