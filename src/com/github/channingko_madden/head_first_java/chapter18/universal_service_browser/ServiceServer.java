/** @brief Part of Universal Service Browser
 *  RMI interface for the remote service */

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser; 

import java.rmi.*;

public interface ServiceServer extends Remote
{

	/** @brief Client calls this method to get a list of services to display in the browser 
	 *  @return List of available services as Strings */
	Object[] getServiceList() throws RemoteException;

	/** @brief Client calls this method to get the corresponding service
	 *  @param[in] serviceKey Key to corresponding service 
	 *  @return Service object */
	Service getService(String serviceKey) throws RemoteException;
}
