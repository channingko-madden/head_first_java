package com.github.channingko_madden.head_first_java.chapter13;

/** @brief Beat Box Chapter13 version
 *  Creates 16 beat MIDI based on user input
 *  and plays */

import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class BeatBox
{
	JPanel mMainPanel;
	ArrayList<JCheckBox> mCheckboxList; // store checkboxes here
	Sequencer mSequencer;
	Sequence mSequence;
	Track mTrack;
	JFrame mTheFrame;

	String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap",
		"High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo",
		"Open Hi Conga"};

	// Instrument keys, in matching order as instrumentNames above
	int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

	public static void main(String[] args)
	{
		new BeatBox().buildGUI();
	}

	public void buildGUI()
	{
		mTheFrame = new JFrame("Cyber BeatBox");
		mTheFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		// empty border gives a margin between edges of the panel and where the components are placed (aesthetic).
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

		mCheckboxList = new ArrayList<JCheckBox>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS); // box holds the 4 buttons located on the right hand side of gui

		JButton start = new JButton("Start");
		start.addActionListener(new MyStartListener()); //inner classes for callbacks declared below
		buttonBox.add(start);

		JButton stop = new JButton("Stop");
		stop.addActionListener(new MyStopListener());
		buttonBox.add(stop);

		JButton upTempo = new JButton("Tempo Up");
		upTempo.addActionListener(new MyUpTempoListener());
		buttonBox.add(upTempo);

		JButton downTempo = new JButton("Tempo Down");
		downTempo.addActionListener(new MyDownTempoListener());
		buttonBox.add(downTempo);

		JButton saveBeat = new JButton("Save Beat");
		saveBeat.addActionListener(new MySendListener());
		buttonBox.add(saveBeat);

		JButton loadBeat = new JButton("Load Beat");
		loadBeat.addActionListener(new MyReadInListener());
		buttonBox.add(loadBeat);



		Box nameBox = new Box(BoxLayout.Y_AXIS); // box holds names of each drum instrument, located on the left hand side of gui
		for (int i = 0; i < 16; i++)
		{
			nameBox.add(new Label(instrumentNames[i]));
		}

		background.add(BorderLayout.EAST, buttonBox); // add buttons to east
		background.add(BorderLayout.WEST, nameBox); // add drum instrument names to west
		mTheFrame.getContentPane().add(background); // Add this background JPanel to the pane

		GridLayout grid = new GridLayout(16, 16); // 16x16 grid for all the checkboxes (16 beats x 16 drum instruments)
		grid.setVgap(1);
		grid.setHgap(2);
		// Create a panel with the grid and add it to the background JPanel, in the center position
		mMainPanel = new JPanel(grid); 
		background.add(BorderLayout.CENTER, mMainPanel);

		/* add checkboxes to grid and ArrayList
		 * Grid adds elements to a single row along the column until all columns are full, at
		 * which is drops down to the next row and continues. So each row corresponds to a single 
		 * instrument and each column corresponds to the beats that instrument should play on.
		 */
		for (int i = 0; i < 256; i++)
		{
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			mCheckboxList.add(c);
			mMainPanel.add(c); 
		}

		setUpMidi();

		mTheFrame.setBounds(50,50,300,300);
		mTheFrame.pack();
		mTheFrame.setVisible(true);
	}

	public void setUpMidi()
	{
		try
		{
			mSequencer = MidiSystem.getSequencer();
			mSequencer.open();
			mSequence = new Sequence(Sequence.PPQ, 4);
			mTrack = mSequence.createTrack();
			mSequencer.setTempoInBPM(120);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void buildTrackAndStart()
	{
		/* 16 element array to hold values for one instrument across all 16 beats
		 * If playing on a beat, the value at that element will be the instrument key,
		 * otherwise set to 0 */
		int[] trackList = null;

		mSequence.deleteTrack(mTrack);  // delete old track so we can make a new one.
		mTrack = mSequence.createTrack();
		for (int i = 0; i < 16; i++) // for each instrument
		{
			trackList = new int[16];
			int key = instruments[i]; // array of instrument keys
			for (int j = 0; j < 16; j++ ) // for each beat
			{
				JCheckBox jc = mCheckboxList.get(16*i + j); 
				if(jc.isSelected())
				{
					trackList[j] = key;
				}
				else
				{
					trackList[j] = 0;
				}
			} 
			makeTracks(trackList); // adds events to mTrack
			mTrack.add(makeEvent(176, 1, 127, 0, 16)); //control change command, not sure what it does
		}

		mTrack.add(makeEvent(192,9,1,0,15)); // program change command, not sure what it does 
		try
		{
			mSequencer.setSequence(mSequence);
			mSequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			mSequencer.start();
			mSequencer.setTempoInBPM(120);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void makeTracks(int[] list)
	{
		for (int i = 0; i < 16; i++)
		{
			int key = list[i];
			if (key != 0) 
			{
				mTrack.add(makeEvent(144,9,key, 100, i));
				mTrack.add(makeEvent(128,9,key, 100, i+1));
			}
		}
	}

	public static MidiEvent makeEvent(int cmd, int chan, int one, int two, int tick)
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

	public class MyStartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			buildTrackAndStart();
		}
	}

	public class MyStopListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			mSequencer.stop();
		}
	} // close inner class

	public class MyUpTempoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			float tempoFactor = mSequencer.getTempoFactor();
			mSequencer.setTempoFactor((float)(tempoFactor * 1.03)); // scales BPM tempo that was set previously
		}
	} // close inner class

	public class MyDownTempoListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			float tempoFactor = mSequencer.getTempoFactor();
			mSequencer.setTempoFactor((float)(tempoFactor * .97));
		}
	} // close inner class

	/* from Chapter 14 pg 463. Class serializes a boolean array containing
	 * checkbox data to save the beat. Happens when user presses Save button
	 *
	 * Add a File chooser to let user decide what file to save to!
	 */
	public class MySendListener implements ActionListener
	{
		public void actionPerformed(ActionEvent a)
		{
			boolean[] checkboxState = new boolean[256];
			for (int i = 0; i < 256; i++)
			{
				JCheckBox check = (JCheckBox) mCheckboxList.get(i);
				if (check.isSelected())
				{
					checkboxState[i] = true;
				}
			}

			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(mTheFrame); // dialog shows up in the frame for user

			try
			{
				FileOutputStream fileStream = new FileOutputStream(fileSave.getSelectedFile());
				ObjectOutputStream os = new ObjectOutputStream(fileStream);
				os.writeObject(checkboxState);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	// from Chapter 14 pg 464. Class deserializes a boolean array containing
	// checkbox data to restore the beats contained within mCheckboxList. Happens when user presses Restore button
	public class MyReadInListener implements ActionListener {

		public void actionPerformed(ActionEvent a)
		{
			boolean[] checkboxState = null;
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(mTheFrame);
			try
			{
				FileInputStream fileIn = new FileInputStream(fileOpen.getSelectedFile());
				ObjectInputStream is = new ObjectInputStream(fileIn);
				checkboxState = (boolean[]) is.readObject(); // cast to boolean array object
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}

			for(int i = 0; i < 256; i++)
			{
				JCheckBox check = (JCheckBox) mCheckboxList.get(i);
				if(checkboxState[i])
				{
					check.setSelected(true);
				}
				else
				{
					check.setSelected(false);
				}
			}
			mSequencer.stop(); // shouldn't this happen before changing the state of the checkboxes?
			buildTrackAndStart();
		}
	}


}
