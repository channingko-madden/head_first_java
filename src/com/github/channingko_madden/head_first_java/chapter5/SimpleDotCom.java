package com.github.channingko_madden.head_first_java.chapter5;

/** @brief SimpleDotCom class represents a "battleship" in 
 *  the board game Battleship. An object will take up 3 cells of the
 *  board, and is sunk when all three cells have been discovered.
 */

public class SimpleDotCom
{
	private int[] locationCells;
	private int numOfHits = 0;

	public void setLocationCells(int[] locs)
	{
		locationCells = locs;
	}

	public String checkYourself(String stringGuess)
	{
		int guess = Integer.parseInt(stringGuess);
		String result = "miss";
		for (int cell : locationCells)
		{
			if (guess == cell)
			{
				result = "hit";
				numOfHits++;
				break;
			}
		}

		if (numOfHits == locationCells.length)
		{
			result = "kill";
		}
		System.out.println(result);
		return result;
	}
}
