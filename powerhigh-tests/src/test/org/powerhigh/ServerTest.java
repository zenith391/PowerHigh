package test.org.powerhigh;

import org.powerhigh.multiplayer.Connection;
import org.powerhigh.multiplayer.Metadata;
import org.powerhigh.multiplayer.Server;

public class ServerTest {

	public static void main(String[] args) throws Exception {
		BasicServerHandler handler = new BasicServerHandler();
		Server server = Server.listen(handler, 4567);
		Connection conn = Connection.connect("localhost", 4567);
		conn.sendPacket("Hello, World.".getBytes("UTF-8"));
		conn.sendPacket("The server echoes eveything!".getBytes("UTF-8"));
		conn.sendPacket(";)".getBytes("UTF-8"));
		server.close();
		conn.close();
	}

}
