package com.github.channingko_madden.head_first_java.chapter6;

/** @brief DotCom class represents a "battleship" in 
 *  the board game Battleship. An object will take up 3 cells of the
 *  board, and is sunk when all three cells have been discovered.
 */

import java.util.ArrayList;

public class DotCom
{
	private ArrayList<String> locationCells;
	private String name;

	public void setLocationCells(ArrayList<String> locs)
	{
		locationCells = locs;
	}

	public void setName(String n)
	{
		name = n;
	}

	public String checkYourself(String userInput)
	{
		String result = "miss";
		int index = locationCells.indexOf(userInput);
		if (index >= 0)
		{
			locationCells.remove(index);
			if (locationCells.isEmpty())
			{
				result = "kill";
				System.out.println("Ouch! You sunk " + name + "\t: ( ");
			}
			else
			{
				result = "hit";
			}
		}
		return result;
	}
}
