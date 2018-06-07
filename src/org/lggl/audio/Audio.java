package org.lggl.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.lggl.utils.LGGLException;
import org.lggl.utils.debug.DebugLogger;

public class Audio {

	private SourceDataLine line;
	private AudioFormat format;
	
	/** 8-bit audio. Converts higher sample size to 8-bits **/ public static final int AUDIO_BIT_8  = 8;
	/** 16-bit audio. Converts other sample size to 16-bit **/ public static final int AUDIO_BIT_16 = 16;
	
	public Audio() throws LGGLException {
		format = new AudioFormat(91000f, AUDIO_BIT_16, 1, true, false); // DVD Audio Quality
		try {
			line = AudioSystem.getSourceDataLine(format);
		} catch (LineUnavailableException e) {
			throw new LGGLException("Exception initializing audio", e);
		}
		if (!openLine())
			throw new LGGLException("Could not open audio line");
	}
	
	public void playSound(Sound sound) {
		Thread th = new Thread() {
			public void run() {
				byte[] buffer = new byte[65536];
				int offset = buffer.length;
				while (sound.hasNextSample()) {
					if (offset > buffer.length - 1) {
						for (int i = 0; i < buffer.length; i++) {
							buffer[i] = sound.getNextSample();
						}
						offset = 0;
					}
					line.write(buffer, 0, buffer.length);
					offset += buffer.length;
				}
				line.drain();
				System.out.println("AUDIO DONE");
			}
		};
		th.setDaemon(true);
		th.setName("Sound-"+ sound.toString() +"-Thread");
		th.start();
	}
	
	protected boolean openLine() {
		try {
			line.open();
			line.start();
			return true;
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return false;
		}
	}

}
