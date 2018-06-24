package org.lggl.audio;

import javax.sound.sampled.AudioFormat;

public abstract class Music {

	protected int position = 0;
	protected int length = Integer.MAX_VALUE;
	protected byte[] sampleBuffer;
	protected int sampleBufferSize = 4096;
	protected AudioFormat format;
	
	public byte getNextSample() {
		if (position % sampleBufferSize >= sampleBufferSize || sampleBuffer == null) {
			for (int i = 0; i < sampleBufferSize; i++) {
				sampleBuffer[i] = readNextSample();
			}
		}
		return sampleBuffer[position % sampleBufferSize];
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
	
	protected abstract byte readNextSample();

}
