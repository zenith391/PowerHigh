package test.org.powerhigh;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.powerhigh.multiplayer.Client;
import org.powerhigh.multiplayer.ServerHandler;
import org.powerhigh.multiplayer.channel.Channel;

public class BasicServerHandler implements ServerHandler {

	
	@Override
	public void handleFullPacket(Client cl, Channel ch, byte[] packet) {
		String str = new String(packet);
		System.out.println("Echoeing \"" + str + "\"");
		try {
			ch.write(ByteBuffer.wrap(packet));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleIncompletePacket(Client cl, Channel ch, byte[] packet) {
		// Ignored
	}

}
