package org.powerhigh.multiplayer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.powerhigh.multiplayer.channel.Channel;

public class Metadata {

	private HashMap<String, Object> data;
	private static final int METADATA_PACKET_TYPE = 0x12345678;
	
	public Metadata() {
		data = new HashMap<>();
	}
	
	public static CompletableFuture<Metadata> retrieve(Channel ch) {
		return CompletableFuture.supplyAsync(() -> {
			Metadata metadata = new Metadata();
			
			ByteBuffer buf = ByteBuffer.allocateDirect(4);
			buf.putInt(METADATA_PACKET_TYPE);
			try {
				ch.write(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			return metadata;
		});
	}
	
}
