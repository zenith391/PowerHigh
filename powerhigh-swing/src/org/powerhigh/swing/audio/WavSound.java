package org.powerhigh.swing.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat.Encoding;

import org.powerhigh.audio.PCMType;
import org.powerhigh.audio.Sound;
import org.powerhigh.utils.debug.DebugLogger;

public class WavSound extends Sound {

	private AudioInputStream ais;
	private AudioFormat format;
	
	public WavSound(float volume, File file) {
		super(volume);
		
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

	@Override
	public int getSampleBits() {
		return format.getSampleSizeInBits();
	}

	@Override
	public int getSampleRate() {
		return (int) format.getFrameRate();
	}
	
	@Override
	public PCMType getPCMType() {
		if (format.isBigEndian()) {
			if (format.getEncoding() == Encoding.PCM_SIGNED) {
				return PCMType.SIGNED_BIG_ENDIAN;
			} else {
				return PCMType.UNSIGNED_BIG_ENDIAN;
			}
		} else {
			if (format.getEncoding() == Encoding.PCM_SIGNED) {
				return PCMType.SIGNED_LITTLE_ENDIAN;
			} else {
				return PCMType.UNSIGNED_LITTLE_ENDIAN;
			}
		}
	}

	@Override
	public int getChannels() {
		return format.getChannels();
	}

}
