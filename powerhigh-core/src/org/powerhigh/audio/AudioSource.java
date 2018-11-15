package org.powerhigh.audio;

public abstract class AudioSource {

	protected int audioFlags;
	protected long position = -1;
	protected long length = -1;
	protected float volume = 1.0f;
	
	public AudioSource(int flags, float volume) {
		this.volume = volume;
		audioFlags = flags;
	}
	
	public abstract boolean hasNextSample();
	public abstract byte getNextSampleByte();
	public abstract byte[] getNextSample();
	
	public float getVolume() {
		return volume;
	}
	
	public int getAudioFlags() {
		return audioFlags;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public long getPosition() {
		return position;
	}
	
	public long getLenght() {
		return length;
	}
	
	public int getSampleBits() {
		int bits = 0;
		int flags = getAudioFlags();
		if ((flags & Audio.AUDIO_BIT_8) == Audio.AUDIO_BIT_8) {
			bits = 8;
		}
		if ((flags & Audio.AUDIO_BIT_16) == Audio.AUDIO_BIT_16) {
			bits = 16;
		}
		if ((flags & Audio.AUDIO_BIT_24) == Audio.AUDIO_BIT_24) {
			bits = 24;
		}
		return bits;
	}
	
}
