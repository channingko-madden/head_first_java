/** @brief Part of the Universal Service Browser
 *  Interface defines the single method for a universal service.
 *  Extends Serializable so implementations are Serializable, because a Service is shipped over wire via RMI */

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser;

import javax.swing.*;
import java.io.*;

public interface Service extends Serializable
{
	/** @brief Get the GUI panel for this Service
	 *  @return JPanel GUI panel for client to display */
	public JPanel getGuiPanel();
}
