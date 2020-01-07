package org.powerhigh.audio;

public abstract class Sound extends AudioSource {
	
	public Sound(float volume) {
		super(volume);
	}
	
	public Sound() {
		this(1f);
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
