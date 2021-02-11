package com.github.channingko_madden.head_first_java.chapter6;

/** @brief DotComTestDrive is a test class for testing
 *  the DotCom class */

import java.util.ArrayList;

public class DotComTestDrive
{
	public static void main (String[] args)
	{
		DotCom dot = new DotCom();

		ArrayList<String> locationCells = new ArrayList<String>();
		locationCells.add("2");
		locationCells.add("3");
		locationCells.add("4");
		dot.setLocationCells(locationCells); // setter
		dot.setName("twitch.tv");

		String userGuess = "2"; // fake user guess
		String result = dot.checkYourself(userGuess); 
		String testResult = "failed";
		if(result.equals("hit") ) 
		{
			testResult = "passed";
		}

		System.out.println(testResult); // print out test result
	}
}
