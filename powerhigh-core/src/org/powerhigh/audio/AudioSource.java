package org.powerhigh.audio;

public abstract class AudioSource {
	
	protected long position = -1;
	protected long length = -1;
	protected float volume = 1.0f;
	
	public AudioSource(float volume) {
		this.volume = volume;
	}
	
	public AudioSource() {
		this(1.0f);
	}
	
	public abstract boolean hasNextSample();
	public abstract byte getNextSampleByte();
	public abstract byte[] getNextSample();
	public abstract PCMType getPCMType();
	public abstract int getChannels();
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public long getLength() {
		return length;
	}
	
	public abstract int getSampleBits();
	public abstract int getSampleRate();
	
}
