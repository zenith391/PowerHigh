package org.powerhigh.multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientListener {

	private Client client;
	private ServerHandler handler;
	
	public ClientListener(ServerHandler handler, Client client) {
		this.handler = handler;
		this.client = client;
	}
	
	public Runnable getListener() {
		return () -> {
			InetAddress addr = client.getInetAddress();
			Socket sock = client.getTcpSocket();
			System.out.println(sock);
			InputStream is = null;
			OutputStream os = null;
			System.out.println("run");
			try {
				is = sock.getInputStream();
				os = sock.getOutputStream();
			} catch (IOException e) {
				
			}
			while (!sock.isClosed()) {
				char c = 1;
				try {
					if (is.available() != 0) {
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.err.println("closed");
		};
	}
	
}
