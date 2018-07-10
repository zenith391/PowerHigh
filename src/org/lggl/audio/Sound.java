package org.lggl.audio;

import javax.sound.sampled.AudioFormat;

public class Sound {

	private int pos = -1;
	
	protected byte[] samples;
	
	protected AudioFormat format;
	
	protected float volume;
	
	public AudioFormat getFormat() {
		return format;
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
