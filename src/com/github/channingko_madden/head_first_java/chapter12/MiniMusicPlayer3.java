/** @brief Example for creating MidiEvents using a static method
 *  and listening for Controller events using an inner class,
 *  which will create gui stufff in time with the beat like
 *  a music video */

import javax.sound.midi.*;
import java.io.*;
import javax.swing.*; //gui stuff
import java.awt.*; //for event stuff

public class MiniMusicPlayer3
{
	static JFrame f= new JFrame("My First Music Video"); // why are these static, to avoid a constructor?
	static MyDrawPanel ml; // use as the content panel of the JFrame

	public static void main(String[] args)
	{
		MiniMusicPlayer3 mini = new MiniMusicPlayer3();
		mini.go();
	}

	public void setUpGui()
	{
		ml = new MyDrawPanel(); // inner class
		f.setContentPane(ml);
		f.setBounds(30, 30, 300, 300);
		f.setVisible(true);
	}

	public void go()
	{
		setUpGui();
		try
		{
			Sequencer sequencer = MidiSystem.getSequencer(); // requires try
			sequencer.open();

			sequencer.addControllerEventListener(ml, new int[] {127}); // only want controller event #127

			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();

			for(int i = 0; i < 60; i+=4)
			{
				int r = (int) ((Math.random() * 50) + 1); // get random number from 0 to 50
				track.add(makeEvent(144, 1, r, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i)); // insert ControllerEvent (176) #127
				track.add(makeEvent(128, 1, r, 100, i + 2));
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

	class MyDrawPanel extends JPanel implements ControllerEventListener
	{
		private boolean msg = false; // should be atomic to be thread safe

		/** @brief ControllerEventListener interface method */
		public void controlChange(ShortMessage event)
		{
			msg = true;
			repaint(); // JPanel method
		}

		/** @brief Override JPanel method, is also called when repaint() is called */
		public void paintComponent(Graphics g)
		{
			if(msg)
			{
				int r = (int) (Math.random() * 250);
				int gr = (int) (Math.random() * 250);
				int b = (int) (Math.random() * 250);

				g.setColor(new Color(r, gr, b));

				int ht = (int) ((Math.random() * 120) + 10); // random number between 10 and 129
				int width = (int) ((Math.random() * 120) + 10); // random number between 10 and 129

				int x = (int) ((Math.random() * 40) + 10); // random number between 10 and 39
				int y = (int) ((Math.random() * 40) + 10); // random number between 10 and 39

				g.fillRect(x, y, ht, width);
				msg = false;

			}
		}
	}
}
