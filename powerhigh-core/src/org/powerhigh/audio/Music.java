package org.powerhigh.audio;

public abstract class Music extends AudioSource {
	
	public Music(float volume) {
		super(volume);
	}
	
	public Music() {
		this(1f);
	}

	protected long position = -1;
	protected long length = Integer.MAX_VALUE;
	protected byte[] sampleBuffer;
	protected int sampleBufferSize = 65536;
	protected float volume;
	protected int audioFlags;
	
	private byte[] s;
	private int j;
	
	public byte getNextSampleByte() {
		position++;
		if ((position % (sampleBufferSize)) >= sampleBufferSize-1 || sampleBuffer == null) {
			if (sampleBuffer == null) {
				sampleBuffer = new byte[sampleBufferSize];
			}
			int i = 0;
			while (i < sampleBufferSize-1) {
				if (s == null || j >= s.length) {
					s = readNextSamples();
					j = 0;
				}
				for (; j < s.length; j++) {
					try {
						sampleBuffer[i + j] = s[j];
					} catch (ArrayIndexOutOfBoundsException e) {
						break;
					}
				}
				i += j;
			}
		}

		//System.out.println(position % sampleBufferSize);
		return sampleBuffer[(int) (position % sampleBufferSize)];
	}
	
	public byte[] getNextSample() {
		byte[] sb = new byte[getFrameSize()];
		for (int i = 0; i < sb.length; i++) {
			sb[i] = getNextSampleByte();
		}
		return sb;
	}

	public long getLength() {
		return length;
	}
	
	public boolean hasNextSample() {
		return getPosition() < getLength();
	}
	
	public long getPosition() {
		return position;
	}
	
	public abstract int getFrameSize();
	
	protected abstract byte[] readNextSamples();

}
