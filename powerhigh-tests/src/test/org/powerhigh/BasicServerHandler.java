package test.org.powerhigh;

import org.powerhigh.multiplayer.Client;
import org.powerhigh.multiplayer.ServerHandler;

public class BasicServerHandler implements ServerHandler {

	@Override
	public void handleFullPacket(Client cl, byte[] packet) {
		byte type = packet[0];
		byte arg1 = packet[1];
		if (type == 1) {
			System.out.println("Received byte: " + arg1 + ", with a packet from type " + type);
		}
	}

	@Override
	public void handleUnsafePacket(Client cl, byte[] packet) {
		// Ignored
	}

}
