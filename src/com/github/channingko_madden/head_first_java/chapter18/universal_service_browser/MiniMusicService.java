/** @brief Part of Universal Service Browser
 *  This Service is the MiniMusic player for chapter 12
 *  Listens for Controller events using an inner class,
 *  which will create gui stufff in time with the beat like
 *  a music video */

package com.github.channingko_madden.head_first_java.chapter18.universal_service_browser; 

import javax.sound.midi.*;
import java.io.*;
import javax.swing.*; //gui stuff
import java.awt.*; //for event stuff
import java.awt.event.*;

public class MiniMusicService implements Service
{
	private MyDrawPanel mMyPanel;

	public JPanel getGuiPanel()
	{
		JPanel mainPanel = new JPanel();
		mMyPanel = new MyDrawPanel();
		JButton playItButton = new JButton("Play");
		playItButton.addActionListener(new PlayItListener());
		mainPanel.add(mMyPanel);
		mainPanel.add(playItButton);
		return mainPanel;
	}

	/** @brief Listener for Play button, runs the music/graphic */
	public class PlayItListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			try
			{
				Sequencer sequencer = MidiSystem.getSequencer(); // requires try
				sequencer.open();

				sequencer.addControllerEventListener(mMyPanel, new int[] {127}); // only want controller event #127

				Sequence seq = new Sequence(Sequence.PPQ, 4);
				Track track = seq.createTrack();

				for(int i = 0; i < 100; i+=4)
				{
					int r = (int) ((Math.random() * 50) + 1); // get random number from 0 to 50
					if(r < 38)
					{
						track.add(makeEvent(144, 1, r, 100, i));
						track.add(makeEvent(176, 1, 127, 0, i)); // insert ControllerEvent (176) #127
						track.add(makeEvent(128, 1, r, 100, i + 2));
					}
				}

				sequencer.setSequence(seq);
				sequencer.setTempoInBPM(120);
				sequencer.start();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}


	public MidiEvent makeEvent(int cmd, int chan, int one, int two, int tick)
	{
		try
		{
			ShortMessage a = new ShortMessage();
			a.setMessage(cmd, chan, one, two);
			return new MidiEvent(a, tick);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	class MyDrawPanel extends JPanel implements ControllerEventListener
	{
		private boolean msg = false; // should be atomic to be thread safe

		/** @brief ControllerEventListener interface method */
		public void controlChange(ShortMessage event)
		{
			msg = true;
			repaint(); // JPanel method
		}
		
		public Dimension getPreferredSize()
		{
			return new Dimension(200, 200); // encapsulates width and height of a component
		}

		/** @brief Override JPanel method, is also called when repaint() is called */
		public void paintComponent(Graphics g)
		{
			if(msg)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;

				int r = (int) (Math.random() * 250);
				int gr = (int) (Math.random() * 250);
				int b = (int) (Math.random() * 250);

				g2.setColor(new Color(r, gr, b));

				int ht = (int) ((Math.random() * 120) + 10); // random number between 10 and 129
				int width = (int) ((Math.random() * 120) + 10); // random number between 10 and 129

				int x = (int) ((Math.random() * 40) + 10); // random number between 10 and 39
				int y = (int) ((Math.random() * 40) + 10); // random number between 10 and 39

				g2.fillRect(x, y, ht, width);
				msg = false;
			}
		}
	}
}
