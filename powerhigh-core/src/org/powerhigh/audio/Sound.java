package org.powerhigh.audio;

public class Sound extends AudioSource {
	
	public Sound(int flags, float volume) {
		super(flags, volume);
	}

	protected byte[][] samples;
	
	public byte[] getNextSample() {
		position++;
		return samples[(int) position];
	}
	
	public boolean hasNextSample() {
		return samples.length > position+2;
	}

}
