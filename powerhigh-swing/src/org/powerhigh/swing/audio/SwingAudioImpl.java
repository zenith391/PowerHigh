package org.powerhigh.swing.audio;

import javax.sound.sampled.*;

import org.powerhigh.audio.*;

public class SwingAudioImpl extends AudioImplementation {

	@Override
	public void playFromSource(AudioSource source) {
		Thread thread = new Thread(() -> {
			SourceDataLine line = openLine(source);
			line.start();
			while (source.hasNextSample()) {
				byte[] sample = source.getNextSample();
				line.write(sample, 0, sample.length);
				source.setPosition(source.getPosition() + 1);
			}
			line.drain();
			line.stop();
			line.close();
		});
		thread.setName("snd-awt");
		thread.start();
	}
	
	public AudioFormat getSourceAudioFormat(AudioSource source) {
		PCMType pcm = source.getPCMType();
		boolean signed = pcm.name().contains("SIGNED");
		boolean bigEndian = pcm.name().contains("BIG_ENDIAN");
		AudioFormat format = new AudioFormat(source.getSampleRate(), source.getSampleBits(), source.getChannels(), signed, bigEndian);
		return format;
	}
	
	public SourceDataLine openLine(AudioSource source) {
		try {
			SourceDataLine line = AudioSystem.getSourceDataLine(getSourceAudioFormat(source));
			line.open();
			return line;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}

}
