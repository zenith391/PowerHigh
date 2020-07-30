package org.powerhigh.multiplayer.channel;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface BiChannel extends Channel {
	
	/**
	 * Always safe (on TCP)
	 */
	public void write(ByteBuffer buf) throws IOException;
	
	/**
	 * Always safe (on TCP)
	 */
	public int read(byte[] bytes) throws IOException;
	
	/**
	 * Always safe (on TCP)
	 */
	public int read(ByteBuffer buf) throws IOException;
	
	public void writeUnsafe(ByteBuffer buf) throws IOException;
	public int readUnsafe(byte[] bytes) throws IOException;
	public int readUnsafe(ByteBuffer buf) throws IOException;
	
}
