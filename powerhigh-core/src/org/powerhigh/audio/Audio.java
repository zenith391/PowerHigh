package org.powerhigh.audio;

import org.powerhigh.utils.PowerHighException;
public class Audio {
	
	
	
	/** 8-bit audio. Converts higher sample size to 8-bits **/
	public static final int AUDIO_BIT_8  = 0b01000000;
	/** 16-bit audio. Converts other sample size to 16-bit **/
	public static final int AUDIO_BIT_16 = 0b1000000;
	/** 24-bit audio. Converts other sample size to 24-bit **/
	public static final int AUDIO_BIT_24 = 0b00100000; // default
	
	// Common audio rate
	/** 41700Hz or 44100Hz depending on implementations limits **/
	public static final int CD_SPEED     = 0b00010000; // default
	/** 96000Hz audio rate**/
	public static final int DVD_SPEED    = 0b00001000;
	
	// Custom audio rate
	/** Custom audio rate **/
	public static final int CUSTOM_SPEED = 0b00000100;
	
	private float masterVolume;
	private int flags;
	private static AudioImplementation impl;
	
	public Audio(int flags) throws PowerHighException {
		masterVolume = 1.0f;
		this.flags = flags;
		
//		if ((flags & AUDIO_BIT_8) == AUDIO_BIT_8) {
//			System.out.println("8-bits music!");
//		}
	}
	
	public static void setImplementation(AudioImplementation impl) {
		Audio.impl = impl;
	}
	
	public float getVolumeModifier() {
		return masterVolume;
	}
	
	public void setVolumeModifier(float masterVolume) {
		this.masterVolume = masterVolume;
	}
	
	public void playFromSource(AudioSource source) {
		if (impl == null)
			throw new UnsupportedOperationException("impl == null");
		//System.err.println(source.getAudioFlags());
		if (source.getAudioFlags() == 0) {
			impl.playFromSource(source, flags);
		} else {
			impl.playFromSource(source, source.getAudioFlags());
		}
	}
	
	public void playMusic(Music music) {
		playFromSource(music);
	}
	
	public void playSound(Sound sound) {
		playFromSource(sound);
	}

}
