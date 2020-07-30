package test.org.powerhigh;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.powerhigh.multiplayer.Client;
import org.powerhigh.multiplayer.ServerHandler;
import org.powerhigh.multiplayer.channel.Channel;

public class BasicServerHandler implements ServerHandler {

	
	@Override
	public void handleFullPacket(Client cl, Channel ch, ByteBuffer packet) {
		String str = Charset.forName("UTF-8").decode(packet).toString();
		System.out.println("Echoeing \"" + str + "\"");
		try {
			ch.write(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleIncompletePacket(Client cl, Channel ch, ByteBuffer packet) {
		// Ignored
	}

}
