package org.powerhigh.multiplayer.channel.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.powerhigh.multiplayer.channel.BiChannel;

public class BiChannelImpl implements BiChannel {

	private OutputStream out;
	private InputStream in;
	
	private int def = 0; // 0 = TCP, 1 = UDP
	
	public BiChannelImpl(Socket safe) throws IOException {
		out = safe.getOutputStream();
		in = safe.getInputStream();
	}
	
	public void setDefault(int def) {
		this.def = def;
	}

	@Override
	public void write(ByteBuffer buf) throws IOException {
		if (def == 0)
			writeSafe(buf);
		else
			writeUnsafe(buf);
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		if (def == 0)
			return readSafe(bytes);
		else
			return readUnsafe(bytes);
	}

	@Override
	public int read(ByteBuffer buf) throws IOException {
		if (def == 0)
			return readSafe(buf);
		else
			return readUnsafe(buf);
	}

	@Override
	public void writeSafe(ByteBuffer buf) throws IOException {
		out.write(buf.array());
	}

	@Override
	public int readSafe(byte[] bytes) throws IOException {
		return in.read(bytes);
	}

	/**
	 * Reads the available data and puts it at then end of the ByteBuffer.
	 */
	@Override
	public int readSafe(ByteBuffer buf) throws IOException {
		byte[] bytes = new byte[in.available()];
		int readed = readSafe(bytes);
		byte[] newBytes = new byte[readed];
		System.arraycopy(bytes, 0, newBytes, 0, readed);
		buf.put(newBytes);
		return readed;
	}

	@Override
	public void writeUnsafe(ByteBuffer buf) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUnsafe(byte[] bytes) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUnsafe(ByteBuffer buf) throws IOException {
		throw new UnsupportedOperationException();
	}
	
}
