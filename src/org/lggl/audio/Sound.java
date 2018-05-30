package org.lggl.audio;

public class Sound {

	private int pos = -1;
	
	protected byte[] samples;
	
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
