/** @brief Remote interface example pg 619 */

import java.rmi.*;

public interface MyRemote extends Remote
{
	// Only primivates/serialiable and must throw RemoteException
	public String sayHello() throws RemoteException;
}
