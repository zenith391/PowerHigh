package org.powerhigh.multiplayer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

import org.powerhigh.multiplayer.channel.impl.BiChannelImpl;

public class ClientListener implements Runnable {

	private Client client;
	private ServerHandler handler;
	
	public ClientListener(ServerHandler handler, Client client) {
		this.handler = handler;
		this.client = client;
	}
	
	public void run() {
		SocketChannel sock = client.getTcpSocket();
		try {
			BiChannelImpl ch = new BiChannelImpl(sock);
			ByteBuffer sizeInt = ByteBuffer.allocateDirect(4);
			sizeInt.order(ByteOrder.BIG_ENDIAN);
			while (sock.isOpen()) {
				int read = sock.read(sizeInt.rewind());
				if (read == -1) {
					sock.close();
					break;
				}
				int size = sizeInt.getInt(0);
				ByteBuffer tcpPacket = ByteBuffer.allocateDirect(size);
				
				while (tcpPacket.position() < tcpPacket.limit()) {
					read = sock.read(tcpPacket);
					if (read == -1) {
						sock.close();
						break;
					}
				}
				
				handler.handleFullPacket(client, ch, tcpPacket.flip().asReadOnlyBuffer());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
