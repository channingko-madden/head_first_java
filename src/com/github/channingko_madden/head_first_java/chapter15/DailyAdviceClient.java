/** @brief DailyAdviceClient creates a socket connection to
 * localhost port 4242 and reads a line. (pg 481) */

import java.io.*;
import java.net.*;

public class DailyAdviceClient
{
	public void go() 
	{
		try
		{
			Socket socket = new Socket("127.0.0.1", 4242);

			InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);

			String advice = reader.readLine();
			System.out.println("Today you should: " + advice);
			reader.close(); // closes all streams
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		DailyAdviceClient client = new DailyAdviceClient();
		client.go();
	}
}
