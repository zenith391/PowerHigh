package org.powerhigh.swing.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.powerhigh.audio.Music;
import org.powerhigh.audio.PCMType;

public class WavMusic extends Music {

	private AudioInputStream ais;
	private AudioFormat format;

	@Override
	public int getFrameSize() {
		return format.getFrameSize();
	}

	@Override
	protected byte[] readNextSamples() {
		byte[] sb = new byte[getFrameSize()];
		try {
			ais.read(sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb;
	}
	
	public WavMusic(float volume, File f) throws IOException {
		super(volume);
		try {
			ais = AudioSystem.getAudioInputStream(f);
			//System.out.println(ais.markSupported());
			format = ais.getFormat();
			length = ais.getFrameLength() * getFrameSize();
			System.out.println(format.getFrameRate());
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new IOException(f + " is not supported", e);
		}
	}

	@Override
	public int getSampleBits() {
		return format.getSampleSizeInBits();
	}

	@Override
	public int getSampleRate() {
		return (int) format.getFrameRate();
	}

	@Override
	public PCMType getPCMType() {
		if (format.isBigEndian()) {
			if (format.getEncoding() == Encoding.PCM_SIGNED) {
				return PCMType.SIGNED_BIG_ENDIAN;
			} else {
				return PCMType.UNSIGNED_BIG_ENDIAN;
			}
		} else {
			if (format.getEncoding() == Encoding.PCM_SIGNED) {
				return PCMType.SIGNED_LITTLE_ENDIAN;
			} else {
				return PCMType.UNSIGNED_LITTLE_ENDIAN;
			}
		}
	}

	@Override
	public int getChannels() {
		return format.getChannels();
	}
	
}
