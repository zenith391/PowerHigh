package org.powerhigh.audio;

public class Sound {

	private int pos = -1;
	
	protected byte[] samples;
	
	protected int audioFlags;
	
	protected float volume;
	
	public int getAudioFlags() {
		return audioFlags;
	}
	
	public Sound() {
		
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public byte getNextSample() {
		pos++;
		return samples[pos];
	}
	
	public boolean hasNextSample() {
		return samples.length > pos+2;
	}

}
