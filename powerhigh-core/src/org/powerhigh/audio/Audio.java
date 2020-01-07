package org.powerhigh.audio;

public class Audio {
	
	private float masterVolume;
	private static AudioImplementation impl;
	
	public Audio() {
		masterVolume = 1.0f;
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
	
	public void play(AudioSource source) {
		if (impl == null)
			throw new UnsupportedOperationException("impl == null");
		impl.playFromSource(source);
	}

}
