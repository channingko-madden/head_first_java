package com.github.channingko_madden.head_first_java.chapter6;

/** @brief Ready bake code for interacting with a user on the command line */

import java.io.*;
import java.util.*;

public class GameHelper
{
	private static final String alphabet = "abcdefg"; // "global constant string"
	private int gridLength = 7;
	private int gridSize = 49;
	private int[] grid = new int[gridSize];
	private int comCount = 0;

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

	/** 
	 * Grid is an nxn square, where (0,0) is at the upper left corner.
	 * Positive row axis points down, positive column axis points to the right
	 */
	public ArrayList<String> placeDotCom(int comSize)
	{
		ArrayList<String> alphaCells = new ArrayList<String>();

		String temp = null;
		int[] coords = new int[comSize]; //current candidate coords
		int attempts = 0; // current attempts counter
		boolean success = false; // flag = found a good location?
		int location = 0; // current starting location

		comCount++; 
		int incr = 1; // set horizontal increment
		if ((comCount % 2) == 1) // if odd dot com (place vertically)
		{
			incr = gridLength; // set vertical increment
		}

		while (!success & attempts++ < 200 ) // attempts gets incremented even when success == true
		{
			location = (int) (Math.random() * gridSize); // get a random starting point
			int x = 0; // nth position in dotcom to place
			success = true;
			while (success & x < comSize) // look for adjacent unused spots
			{
				if (grid[location] == 0) // if grid location not already used
				{
					coords[x++] = location; // save location
					location += incr; // try 'next' adjacent grid point
					if (location >= gridSize) // out of bounds ('bottom')
					{
						success = false;
					}
					if (x > 0 && (location % gridLength == 0)) // out of bounds, right edge
					{
						success = false;
					}
				}
				else // location is already used
				{
					success = false;
				}
			}

		}

		int x = 0; // turn location into alpha coords (ex. A2, where A is the row and 2 is the column)
		int row = 0;
		int column = 0;
		while (x < comSize)
		{
			grid[coords[x]] = 1;
			row = (int) (coords[x] / gridLength);
			column = coords[x] % gridLength;
			temp = String.valueOf(alphabet.charAt(column));

			alphaCells.add(temp.concat(Integer.toString(row)));
			x++;
			System.out.print(" coord " + x + " = " + alphaCells.get(x-1)); // tell user locations, since this is a learning program 
		}
		System.out.println("\n");
		
		return alphaCells;
	}
}
