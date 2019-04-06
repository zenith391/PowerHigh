package org.powerhigh.audio;

public abstract class Sound extends AudioSource {
	
	public Sound(int flags, float volume) {
		super(flags, volume);
	}
	
	public Sound(int flags) {
		this(flags, 1.0f);
	}
	
	public Sound(float volume) {
		this(0, volume);
	}
	
	public Sound() {
		this(0);
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
