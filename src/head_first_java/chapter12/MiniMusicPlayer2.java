/** @brief Example for creating MidiEvents using a static method
 *  and listening for Controller events */

import javax.sound.midi.*;

public class MiniMusicPlayer2 implements ControllerEventListener
{
	public static void main(String[] args)
	{
		MiniMusicPlayer2 mini = new MiniMusicPlayer2();
		mini.go();
	}

	public void go()
	{
		try
		{
			Sequencer sequencer = MidiSystem.getSequencer(); // requires try
			sequencer.open();

			int[] eventsIWant = {127};
			sequencer.addControllerEventListener(this, eventsIWant); // only want controller event #127

			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();

			for(int i = 5; i < 61; i+=4)
			{
				track.add(makeEvent(144, 1, i, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i)); // insert ControllerEvent (176) #127
				track.add(makeEvent(128, 1, i, 100, i + 2));
			}

			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/** @brief Event handler method from ControllerEvent listener interface */
	public void controlChange(ShortMessage event)
	{
		System.out.println("la");
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
}