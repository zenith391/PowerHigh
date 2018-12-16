package org.powerhigh.multiplayer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

	private ServerHandler handler;
	private DatagramSocket udp;
	private ServerSocket tcp;
	private Thread th;
	private List<Client> clients;
	
	
	/**
	 * Note: Both UDP and TCP serverports must be free.
	 * @param address
	 * @param port
	 * @return
	 */
	public static Server connect(ServerHandler handler, int port) {
		try {
			DatagramSocket udp = new java.net.DatagramSocket(port);
			//DatagramSocket udp = null;
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
		this.handler = handler;
		this.tcp = tcp;
		this.udp = udp;
		clients = new ArrayList<Client>();
		th = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Socket s = tcp.accept();
					System.out.println(s);
					Client client = new Client(new HashMap<String, Object>(), s.getInetAddress(), s);
					clients.add(client);
					
					ClientListener listener = new ClientListener(handler, client);
					Runnable run = listener.getListener();
					Thread t = new Thread(run);
					t.setName("Client-" + client.getInetAddress());
					t.setPriority(1);
					t.start();
				} catch (SocketException e) {
					return;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		th.setDaemon(true);
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
