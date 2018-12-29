package org.powerhigh.swing.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.powerhigh.audio.Audio;
import org.powerhigh.audio.AudioImplementation;
import org.powerhigh.audio.AudioSource;

public class SwingAudioImpl extends AudioImplementation {

	@Override
	public void playFromSource(AudioSource source, int flags) {
		Thread thread = new Thread(() -> {
			SourceDataLine line = openLine(flags);
			line.start();
			while (source.hasNextSample()) {
				byte[] sample = source.getNextSample();
				//System.out.println(sample[0] + ", " + sample[1] + ", " + sample[2] + ", " + sample[3] + " at " + source.getPosition());
				line.write(sample, 0, sample.length);
			}
			line.drain();
			line.stop();
			line.close();
		});
		thread.setName("snd-awt");
		thread.start();
	}
	
	public AudioFormat flagsToFormat(int flags) {
		int bits = 0;
		int speed = 41700;
		if ((flags & Audio.AUDIO_BIT_8) == Audio.AUDIO_BIT_8) {
			bits = 8;
		}
		if ((flags & Audio.AUDIO_BIT_16) == Audio.AUDIO_BIT_16) {
			bits = 16;
		}
		if ((flags & Audio.AUDIO_BIT_24) == Audio.AUDIO_BIT_24) {
			bits = 24;
		}
		if ((flags & Audio.CD_SPEED) == Audio.CD_SPEED) {
			speed = 44100; // AWT doesn't support 44100, nobody knowns why
		}
		if ((flags & Audio.CUSTOM_SPEED) == Audio.CUSTOM_SPEED) {
			/// TODO
		}
		if ((flags & Audio.DVD_SPEED) == Audio.DVD_SPEED) {
			speed = 96000;
		}
		// 41700Hz for DVD speed
		AudioFormat format = new AudioFormat(speed, bits, 2, true, false);
		return format;
	}
	
	public SourceDataLine openLine(int flags) {
		try {
			SourceDataLine line = AudioSystem.getSourceDataLine(flagsToFormat(flags));
			line.open();
			return line;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}

}
