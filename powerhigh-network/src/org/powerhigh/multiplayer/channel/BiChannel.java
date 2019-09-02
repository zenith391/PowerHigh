package org.powerhigh.multiplayer.channel;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface BiChannel extends Channel {

	public void writeSafe(ByteBuffer buf) throws IOException;
	public int readSafe(byte[] bytes) throws IOException;
	public int readSafe(ByteBuffer buf) throws IOException;
	
	public void writeUnsafe(ByteBuffer buf) throws IOException;
	public int readUnsafe(byte[] bytes) throws IOException;
	public int readUnsafe(ByteBuffer buf) throws IOException;
	
}
