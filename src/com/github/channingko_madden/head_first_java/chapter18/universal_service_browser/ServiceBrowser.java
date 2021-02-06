/** @brief Part of Universal Service Browser
 *  This is the RMI client. It builds a GUI, does a lookup in the RMI registry to get the ServiceServer stub,
 *  and displays a Service's GUI */

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser;

import java.awt.*;
import javax.swing.*;
import java.rmi.*;
import java.awt.event.*;

public class ServiceBrowser
{
	JPanel mMainPanel;
	JComboBox mServiceList;
	ServiceServer mServer;

	public void buildGUI()
	{
		JFrame frame = new JFrame("RMI Browser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mMainPanel = new JPanel();
		frame.getContentPane().add(BorderLayout.CENTER, mMainPanel);

		Object[] services = getServicesList(); // no null check?

		mServiceList = new JComboBox(services); // JComboBox knows how to make displayable Strings out of Objects

		frame.getContentPane().add(BorderLayout.NORTH, mServiceList);

		mServiceList.addActionListener(new MyListListener());

		frame.setSize(500, 500);
		frame.setVisible(true);
	}

	private void loadService(Object serviceSelection)
	{
		try
		{
			Service svc = mServer.getService(serviceSelection);

			mMainPanel.removeAll(); 
			mMainPanel.add(svc.getGuiPanel()); // inherited java.awt.Container method, adds component to container
			mMainPanel.validate(); // also java.awt.Container, validates this container and all subcomponents
			// validating the container means laying out its subcomponents, which needs to occur when
			// subcomponents are changed or the container is changed.
			mMainPanel.repaint(); //java.awt.Component repaint() causes call to component's paint() method
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/** @brief Do RMI lookup to get ServiceServer obj and
	 *  use the obj to get a list of (String) descriptions for each service */
	private Object[] getServicesList()
	{
		Object obj = null; 
		Object[] services = null;

		try
		{
			obj = Naming.lookup("rmi://127.0.0.1/ServiceServer");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		mServer = (ServiceServer) obj; // why is this outside of the try?

		try
		{
			services = mServer.getServiceList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return services;
	}

	/** @brief Class for listening to JComboBox selections */
	class MyListListener implements ActionListener
	{
		/** @brief Load the appropriate service when the user selects it */
		public void actionPerformed(ActionEvent ev)
		{
			Object selection = mServiceList.getSelectedItem();
			loadService(selection);
		}
	}

	public static void main(String[] args)
	{
		new ServiceBrowser().buildGUI();
	}



}
