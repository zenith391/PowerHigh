package org.powerhigh.multiplayer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
	private DatagramSocket udp;
	private ServerSocket tcp;
	private Thread th;
	private List<Client> clients;
	
	
	/**
	 * Both UDP and TCP server ports must be free.
	 * @param address
	 * @param port
	 * @return
	 */
	public static Server connect(ServerHandler handler, int port) {
		try {
			DatagramSocket udp = new java.net.DatagramSocket(port);
			ServerSocket tcp = new ServerSocket(port);
			return new Server(handler, udp, tcp);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Server(ServerHandler handler, DatagramSocket udp, ServerSocket tcp) {
		this.tcp = tcp;
		this.udp = udp;
		clients = new ArrayList<Client>();
		th = new Thread(() -> {
			int num = 1;
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Socket s = tcp.accept();
					Client client = new Client(new HashMap<String, Object>(), s.getInetAddress(), s);
					clients.add(client);
					
					ClientListener listener = new ClientListener(handler, client);
					Runnable run = listener.getListener();
					Thread t = new Thread(run);
					t.setName("Client-Handler-" + num);
					t.setPriority(1);
					t.start();
					num++;
				} catch (SocketException e) {
					return;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			Thread.currentThread().interrupt();
		});
		th.setName("Server-TCPSocketThread");
		th.setPriority(1);
		th.start();
	}

	public void close() throws IOException {
		udp.close();
		tcp.close();
		th.interrupt();
	}
	
}
