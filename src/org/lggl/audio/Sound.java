package org.lggl.audio;

import javax.sound.sampled.AudioFormat;

public class Sound {

	private int pos = -1;
	
	protected byte[] samples;
	
	protected AudioFormat format;
	
	public AudioFormat getFormat() {
		return format;
	}
	
	public Sound() {
		
	}
	
	
	
	public byte getNextSample() {
		pos++;
		return samples[pos];
	}
	
	public boolean hasNextSample() {
		return samples.length > pos+2;
	}

}
