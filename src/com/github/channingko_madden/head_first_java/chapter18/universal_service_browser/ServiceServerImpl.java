/** @brief Part of Universal Service Browser
 *  Implementation of ServiceServer for the RMI */

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser;

import java.rmi.*;
import java.util.*;
import java.rmi.server.*;

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer
{
	HashMap mServiceList; // Service objects will be stored here

	/** @brief Constructor 
	 *  Initializes internal HashMap of Service objects */
	public ServiceServerImpl() throws RemoteException
	{
		setUpServices();
	}
	
	/** @brief Makes the service objects and puts them in the internal HashMap with
	 *  descriptions of the service as the keys */
	private void setUpServices()
	{
		mServiceList = new HashMap();
		mServiceList.put("Dice Rolling Service", new DiceService());
		mServiceList.put("Day of the Week Service", new DayOfTheWeekService());
		mServiceList.put("Visual Music Service", new MiniMusicService());
	}

	public Object[] getServiceList() // interface method
	{
		System.out.println("in remote");
		return mServiceList.keySet().toArray(); //toArray method nice
	}

	public Service getService(Object serviceKey) throws RemoteException
	{
		Service theService = (Service) mServiceList.get(serviceKey); // why does a cast need to occur?
		return theService;
	}

	public static void main(String[] args)
	{
		try
		{
			Naming.rebind("ServiceServer", new ServiceServerImpl());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		System.out.println("Remote service is running");
	}

}