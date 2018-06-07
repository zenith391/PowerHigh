package org.lggl.multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.lggl.utils.debug.DebugLogger;

public class PackageServer extends Thread {

	private ServerSocket server;
	private ServerHandler handler;
	
	public void setHandler(ServerHandler handler) {
		this.handler = handler;
	}
	
	public ServerHandler getHandler() {
		return handler;
	}
	
	public PackageServer(ServerSocket srv) {
		server = srv;
	}
	
	public void run() {
		if (handler == null) {
			DebugLogger.logError("No ServerHandler associated to PackageServer! Could not launch it!");
			return;
		}
		while (true) {
			try {
				Socket cl = server.accept();
				Thread th = new Thread(new ClientProcess(cl));
				th.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ClientProcess implements Runnable {

		private Socket sock;
		private InputStream is;
		private OutputStream os;
		
		public ClientProcess(Socket sk) {
			sock = sk;
		}
		
		@Override
		public void run() {
			while (!sock.isClosed()) {
				try {
					String msg = read();
					String[] a = msg.split(" ");
					if (a[0].equals("SET")) {
						
					}
					if (a[0].equals("GET")) {
						
					}
				} catch (IOException e) {
					DebugLogger.logError("Error handling client " + sock.getInetAddress());
					e.printStackTrace();
				}
			}
			DebugLogger.logError("Client " + sock.getInetAddress() + " unsafely exited socket! Is that a client crash ?");
		}
		
		private String read() throws IOException {
			StringBuilder b = new StringBuilder();
			b.append((char) is.read());
			while (is.read() != '\0') {
				b.append((char) is.read());
			}
			return b.toString();
		}
		
	}
	
}
