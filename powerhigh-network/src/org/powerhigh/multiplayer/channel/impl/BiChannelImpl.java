package org.powerhigh.multiplayer.channel.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

import org.powerhigh.multiplayer.channel.BiChannel;

public class BiChannelImpl implements BiChannel {
	
	private SocketChannel socket;
	
	public BiChannelImpl(SocketChannel socket) throws IOException {
		this.socket = socket;
	}

	@Override
	public void write(ByteBuffer buf) throws IOException {
		socket.write(buf);
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		return socket.read(ByteBuffer.wrap(bytes));
	}

	/**
	 * Reads the available data and puts it at then end of the ByteBuffer.
	 */
	@Override
	public int read(ByteBuffer buf) throws IOException {
		return socket.read(buf);
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
