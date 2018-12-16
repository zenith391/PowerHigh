package org.powerhigh.multiplayer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {

	private DatagramSocket udp;
	private Socket tcp;
	private InetAddress addr;
	
	/**
	 * Note: Both UDP and TCP ports must be free.
	 * @param address
	 * @param port
	 * @return
	 */
	public static Connection connect(String address, int port) {
		try {
			InetAddress addr = InetAddress.getByName(address);
			DatagramSocket udp = new DatagramSocket();
			Socket tcp = new Socket(addr, port);
			udp.connect(addr, port);
			return new Connection(udp, tcp);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Connection(DatagramSocket udp, Socket tcp) {
		this.tcp = tcp;
		this.udp = udp;
		addr = udp.getInetAddress();
	}
	
	public void close() throws IOException {
		tcp.close();
		udp.close();
	}
	
	/**
	 * Sends an unstable packet that goes through UDP protocol, the packet might be incomplete (unusable) or lost
	 * @param packet
	 * @throws IOException
	 */
	public void sendPacket(byte[] packet) throws IOException {
		System.out.println(packet);
		DatagramPacket pck = new DatagramPacket(packet, packet.length);
		udp.send(pck);
	}
	
	/**
	 * Sends a stable packet that goes through TCP protocol
	 * @param packet
	 * @throws IOException
	 */
	public void sendStablePacket(byte[] packet) throws IOException {
		OutputStream os = tcp.getOutputStream();
		os.write(packet);
		os.flush();
	}
	
}
