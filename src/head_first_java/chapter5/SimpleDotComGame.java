/** @brief SimpleDotComGame contains main() method for launching the
 *  SimpleDotCom Game (aka 1D battleship) */

public class SimpleDotComGame
{
	public static void main (String[] args)
	{
		int numOfGuesses = 0;
		GameHelper helper = new GameHelper();

		// the cast from double to int removes fractional part, so always rounds down
		int startLoc = (int) (Math.random() * 5); // random number between 0 and 4
		int[] locations = {startLoc, startLoc + 1, startLoc + 2};
		SimpleDotCom com = new SimpleDotCom();
		com.setLocationCells(locations);

		boolean isAlive = true;
		while (isAlive == true)
		{
			// get user input
			String guess = helper.getUserInput("enter a number");
			String res = com.checkYourself(guess);
			numOfGuesses++;
			if (res.equals("kill") )
			{
				isAlive = false;
				System.out.println("You took " + numOfGuesses + " guesses");
			}
		}
		
	}
}
