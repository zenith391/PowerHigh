package org.lggl.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PackageServer extends Thread {

	private ServerSocket server;
	
	public PackageServer(ServerSocket srv) {
		server = srv;
	}
	
	public void run() {
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
		
		public ClientProcess(Socket sk) {
			sock = sk;
		}
		
		@Override
		public void run() {
			
		}
		
	}
	
}
