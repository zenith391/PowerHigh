package org.powerhigh.swing.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.powerhigh.audio.Sound;
import org.powerhigh.utils.debug.DebugLogger;

public class WavSound extends Sound {

	private AudioInputStream ais;
	private AudioFormat format;
	
	public WavSound(int flags, float volume, File file) {
		
		super(flags, volume);
		
		try {
			ais = AudioSystem.getAudioInputStream(file);
			format = ais.getFormat();
			samples = new byte[(int) ais.getFrameLength()][format.getFrameSize()];
			for (int i = 0; i < samples.length; i++) {
				byte[] sb = new byte[format.getFrameSize()];
				ais.read(sb);
				samples[i] = sb;
			}
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		
		DebugLogger.logWarn("WavSound used. Still not suited for production, but usable.");
		
	}

}
