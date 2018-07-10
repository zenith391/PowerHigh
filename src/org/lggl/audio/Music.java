package org.lggl.audio;

import javax.sound.sampled.AudioFormat;

public abstract class Music {

	protected int position = -1;
	protected int length = Integer.MAX_VALUE;
	protected byte[] sampleBuffer;
	protected int sampleBufferSize = 4096;
	protected AudioFormat format;
	protected float volume;
	
	public byte getNextSample() {
		position++;
		if ((position % (sampleBufferSize)) >= sampleBufferSize-1 || sampleBuffer == null) {
			if (sampleBuffer == null) {
				sampleBuffer = new byte[sampleBufferSize];
			}
			int i = 0;
			while (i < sampleBufferSize-1) {
				byte[] s = readNextSamples();
				for (int j = 0; j < s.length; j++) {
					sampleBuffer[i + j] = s[j];
				}
				i += s.length;
			}
		}

		//System.out.println(position % sampleBufferSize);
		return sampleBuffer[position % sampleBufferSize];
	}
	
	public byte[] getNextSampleBuffer() {
		byte[] sb = new byte[getFrameSize()];
		for (int i = 0; i < sb.length; i++) {
			sb[i] = getNextSample();
		}
		return sb;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
	}
	
	public int getLength() {
		return length;
	}
	
	public boolean hasNextSample() {
		return getPosition() < getLength();
	}
	
	public int getPosition() {
		return position;
	}
	
	public AudioFormat getFormat() {
		return format;
	}
	
	public abstract int getFrameSize();
	
	protected abstract byte[] readNextSamples();

}
