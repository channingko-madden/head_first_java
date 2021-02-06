/** @brief Example remote service implementation pg 619 */

import java.rmi.*;
import java.rmi.server.*;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote
{
	// Required to have no-arg constructor that throws RemoteException (For UnicastRemoteObject)
	public MyRemoteImpl() throws RemoteException()
	{
	}

	public String sayHello() // dont have to declare RemoteException here (yay!)
	{
		return "Server says, 'KEKW'";
	}

	public static void main(String[] args)
	{
		try
		{
			MyRemote service = new MyRemoteImpl(); 
			Naming.rebind("Remote Hello", service); // bind object to rmiregistry, client can look up "Remote Hello"
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
