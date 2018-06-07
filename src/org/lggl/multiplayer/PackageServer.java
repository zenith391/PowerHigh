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
			try {
				is = sock.getInputStream();

				os = sock.getOutputStream();
				
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			while (!sock.isClosed()) {
				try {
					String msg = read();
					String[] a = msg.split(" ");
					if (a[0].equals("SET")) {
						String[] b = a[1].split(":");
						String name = b[0];
						String value = b[1];
						handler.putValue(name, value);
					}
					else if (a[0].equals("GET")) {
						String[] b = a[1].split(":");
						String name = b[0];
						os.write(("EVT return.value." + name + " " + handler.getValue(name)).getBytes());
						os.flush();
					} else {
						try {
							short packetID = Short.parseShort(a[0].trim());
							byte[] data = a[1].getBytes();
							handler.onPacket(packetID, data);
						} catch (Exception e) {
							e.printStackTrace();
							DebugLogger.logError("Is that client an hacker ? He's sending invalid packets!");
						}
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
			char c = 1;
			while (c != '\0') {
				c = (char) is.read();
				b.append(c);
			}
			System.out.println(b.toString());
			return b.toString();
		}
		
	}
	
}
