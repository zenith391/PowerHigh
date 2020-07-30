package org.powerhigh.multiplayer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
	private DatagramSocket udp;
	private ServerSocketChannel tcp;
	private Thread th;
	private List<Client> clients;
	
	
	/**
	 * As it uses both of them, TCP and UDP ports must be free.
	 * @param address
	 * @param port
	 * @return
	 */
	public static Server listen(ServerHandler handler, int port) {
		try {
			DatagramSocket udp = new DatagramSocket(port);
			InetSocketAddress local = new InetSocketAddress(InetAddress.getLoopbackAddress(), port);
			return new Server(handler, udp, ServerSocketChannel.open().bind(local));
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Server(ServerHandler handler, DatagramSocket udp, ServerSocketChannel tcp) {
		this.tcp = tcp;
		this.udp = udp;
		clients = new ArrayList<Client>();
		th = new Thread(() -> {
			int num = 0;
			while (!Thread.currentThread().isInterrupted()) {
				try {
					SocketChannel s = tcp.accept();
					Client client = new Client(new HashMap<String, Object>(), s.getRemoteAddress(), s);
					clients.add(client);
					
					ClientListener listener = new ClientListener(handler, client);
					Thread t = new Thread(listener);
					t.setName("Client-Handler-" + num++);
					t.start();
				} catch (AsynchronousCloseException e) { // interrupted
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
