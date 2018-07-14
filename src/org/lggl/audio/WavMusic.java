package org.lggl.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WavMusic extends Music {

	private AudioInputStream ais;

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
	
	public WavMusic(File f) throws IOException {
		try {
			ais = AudioSystem.getAudioInputStream(f);
			format = ais.getFormat();
			length = ais.getFrameLength()*format.getFrameSize();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new IOException(f + " is not supported", e);
		}
	}
	
}
