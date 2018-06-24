package org.lggl.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.lggl.utils.LGGLException;

public class Audio {
	
	/** 8-bit audio. Converts higher sample size to 8-bits **/ public static final int AUDIO_BIT_8  = 8;
	/** 16-bit audio. Converts other sample size to 16-bit **/ public static final int AUDIO_BIT_16 = 16;
	
	public Audio() throws LGGLException {
		
	}
	
	public void playMusic(Music music) {
		Thread th = new Thread() {
			public void run() {
				SourceDataLine line = null;
				try {
					line = AudioSystem.getSourceDataLine(null);
					if (!openLine(line)) {
						throw new LineUnavailableException("Could not open line.");
					}
				} catch (LineUnavailableException e) {
					e.printStackTrace();
					return;
				}
				while (music.hasNextSample()) {
					line.write(new byte[] {music.getNextSample()}, 0, 1);
				}
				line.drain();
				line.close();
			}
			
		};
		th.setDaemon(true);
		th.setName("Music-"+ music.toString() +"-Thread");
		th.start();
	}
	
	public void playSound(Sound sound) {
		Thread th = new Thread() {
			public void run() {
				SourceDataLine line = null;
				try {
					line = AudioSystem.getSourceDataLine(sound.getFormat());
					if (!openLine(line)) {
						throw new LineUnavailableException("Could not open line.");
					}
				} catch (LineUnavailableException e) {
					e.printStackTrace();
					return;
				}
				while (sound.hasNextSample()) {
					line.write(new byte[] {sound.getNextSample(), sound.getNextSample(), sound.getNextSample(), sound.getNextSample()}, 0, 4);
				}
				line.drain();
				line.close();
			}
			
		};
		th.setDaemon(true);
		th.setName("Sound-"+ sound.toString() +"-Thread");
		th.start();
	}
	
	protected boolean openLine(SourceDataLine line) {
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
