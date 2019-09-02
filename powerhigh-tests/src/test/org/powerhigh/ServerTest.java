package test.org.powerhigh;

import org.powerhigh.multiplayer.Connection;
import org.powerhigh.multiplayer.Server;

public class ServerTest {

	public static void main(String[] args) throws Exception {
		BasicServerHandler handler = new BasicServerHandler();
		Server server = Server.connect(handler, 4444);
		Connection conn = Connection.connect("localhost", 4444);
		conn.sendPacket("Hello, World.".getBytes("UTF-8"));
		server.close();
		conn.close();
	}

}
