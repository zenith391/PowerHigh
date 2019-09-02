package org.powerhigh.multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.powerhigh.multiplayer.channel.impl.BiChannelImpl;
import org.powerhigh.utils.BitOps;

public class ClientListener {

	private Client client;
	private ServerHandler handler;
	
	public ClientListener(ServerHandler handler, Client client) {
		this.handler = handler;
		this.client = client;
	}
	
	public Runnable getListener() {
		return () -> {
			Socket sock = client.getTcpSocket();
			InputStream is = null;
			try {
				BiChannelImpl ch = new BiChannelImpl(sock);
				is = sock.getInputStream();
				while (!sock.isClosed()) {
					byte[] sizeInt = new byte[4];
					int read = is.read(sizeInt);
					if (read == -1) {
						sock.close();
						break;
					}
					int size = BitOps.getInt(sizeInt, 0);
					byte[] tcpPacket = new byte[size];
					int off = 0;
					
					while (tcpPacket.length - off > 0) {
						read = is.read(tcpPacket, off, tcpPacket.length - off);
						if (read == -1) {
							sock.close();
							break;
						}
						off += read;
					}
					
					handler.handleFullPacket(client, ch, tcpPacket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}
	
}
