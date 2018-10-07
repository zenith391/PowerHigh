package org.powerhigh.audio;

import org.powerhigh.utils.LGGLException;

public class Audio {
	
	/** 8-bit audio. Converts higher sample size to 8-bits **/ public static final int AUDIO_BIT_8  = 0b10000000;
	/** 16-bit audio. Converts other sample size to 16-bit **/ public static final int AUDIO_BIT_16 = 0b01000000;
	/** 16-bit audio. Converts other sample size to 16-bit **/ public static final int AUDIO_BIT_24 = 0b00100000;
	
	private float masterVolume;
	private int flags;
	private AudioImplementation impl;
	
	public Audio(int flags) throws LGGLException {
		masterVolume = 1.0f;
		this.flags = flags;
		
		if ((flags & AUDIO_BIT_8) == AUDIO_BIT_8) {
			System.out.println("8-bits music!");
		}
	}
	
	public float getVolumeModifier() {
		return masterVolume;
	}
	
	public void setVolumeModifier(float masterVolume) {
		this.masterVolume = masterVolume;
	}
	
	public void playMusic(Music music) {
		impl.playMusic(music, flags);
	}
	
	public void playSound(Sound sound) {
		impl.playSound(sound, flags);
	}

}
