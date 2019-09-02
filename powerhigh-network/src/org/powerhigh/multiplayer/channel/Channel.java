package org.powerhigh.multiplayer.channel;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Channel {
	
	public void write(ByteBuffer buf) throws IOException;
	public int read(byte[] bytes) throws IOException;
	public int read(ByteBuffer buf) throws IOException;
	
}
