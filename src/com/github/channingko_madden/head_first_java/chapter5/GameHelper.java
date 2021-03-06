package com.github.channingko_madden.head_first_java.chapter5;

/** @brief Ready bake code for interacting with a user on the command line */
import java.io.*;

public class GameHelper
{
	public String getUserInput(String prompt)
	{
		String inputLine = null;
		System.out.print(prompt + "  ");
		try
		{
			BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
			inputLine = is.readLine();
			if (inputLine.length() == 0 ) return null;
		}
		catch (IOException e)
		{
			System.out.println("IOException: " + e);
		}

		return inputLine;
	}
}
