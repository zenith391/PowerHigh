package org.powerhigh.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.powerhigh.utils.LGGLException;

public class Audio {
	
	/** 8-bit audio. Converts higher sample size to 8-bits **/ public static final int AUDIO_BIT_8  = 8;
	/** 16-bit audio. Converts other sample size to 16-bit **/ public static final int AUDIO_BIT_16 = 16;
	
	private float masterVolume;
	
	public Audio() throws LGGLException {
		masterVolume = 1.0f;
	}
	
	public float getVolumeModifier() {
		return masterVolume;
	}
	
	public void setVolumeModifier(float masterVolume) {
		this.masterVolume = masterVolume;
	}
	
	public void playMusic(Music music) {
		Thread th = new Thread() {
			public void run() {
				SourceDataLine line = null;
				try {
					line = AudioSystem.getSourceDataLine(music.getFormat());
					if (!openLine(line)) {
						throw new LineUnavailableException("Could not open line.");
					}
				} catch (LineUnavailableException e) {
					e.printStackTrace();
					return;
				}
//				FloatControl volume = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
				while (music.hasNextSample()) {
//					float vol = music.getVolume() + masterVolume;
//					if (vol < volume.getMinimum()) vol = volume.getMinimum();
//					if (vol > volume.getMaximum()) vol = volume.getMaximum();
//					if (volume.getValue() != vol) {
//						volume.setValue(vol);
//					}
					line.write(music.getNextSampleBuffer(), 0, music.getFrameSize());
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
				FloatControl volume = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
				while (sound.hasNextSample()) {
					float vol = sound.getVolume() + masterVolume;
					if (vol < volume.getMinimum()) vol = volume.getMinimum();
					if (vol > volume.getMaximum()) vol = volume.getMaximum();
					if (volume.getValue() != vol) {
						volume.setValue(vol);
					}
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
