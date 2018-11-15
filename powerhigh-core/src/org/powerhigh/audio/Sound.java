package org.powerhigh.audio;

public class Sound extends AudioSource {
	
	public Sound(int flags, float volume) {
		super(flags, volume);
	}

	protected byte[][] samples;
	
	public byte[] getNextSample() {
		position += 4;
		return samples[(int) position / 4];
	}
	
	public byte getNextSampleByte() {
		position++;
		return samples[(int) position / 4][(int) position % 4];
	}
	
	public boolean hasNextSample() {
		return samples.length > position+2;
	}

}
