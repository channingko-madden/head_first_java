
/** @brief SimpleDotComTestDrive is a test class for testing
 *  the SimpleDotCom class */
public class SimpleDotComTestDrive
{
	public static void main (String[] args)
	{
		SimpleDotCom dot = new SimpleDotCom();

		int[] locations = {2,3,4}; 
		dot.setLocationCells(locations); // setter

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
