package org.powerhigh.swing.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.powerhigh.audio.Music;

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
	
	public WavMusic(int flags, float volume, File f) throws IOException {
		super(flags, volume);
		try {
			ais = AudioSystem.getAudioInputStream(f);
			format = ais.getFormat();
			length = ais.getFrameLength() * getFrameSize();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new IOException(f + " is not supported", e);
		}
	}
	
}
