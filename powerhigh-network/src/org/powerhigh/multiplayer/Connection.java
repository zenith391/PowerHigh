package org.powerhigh.multiplayer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.powerhigh.utils.BitOps;

public class Connection {

	private DatagramSocket udp;
	private Socket tcp;
	
	/**
	 * As it uses both of them, TCP and UDP port given must be free.
	 * @param address
	 * @param port
	 * @return Connection object
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
	
	/**
	 * Manual instanciator for providing a custom DatagramSocket and a custom Socket.
	 * @param udp
	 * @param tcp
	 */
	public Connection(DatagramSocket udp, Socket tcp) {
		this.tcp = tcp;
		this.udp = udp;
	}
	
	public void close() throws IOException {
		tcp.close();
		udp.close();
	}
	
	/**
	 * Sends an unstable packet that goes through the UDP protocol, the packet might be incomplete (unusable) or lost
	 * @param packet
	 * @throws IOException
	 */
	public void sendUnstablePacket(byte[] packet) throws IOException {
		DatagramPacket pck = new DatagramPacket(packet, packet.length);
		udp.send(pck);
	}
	
	/**
	 * Sends a stable packet that goes through the TCP protocol.
	 * @param packet
	 * @throws IOException
	 */
	public void sendPacket(byte[] packet) throws IOException {
		OutputStream os = tcp.getOutputStream();
		byte[] newPacket = new byte[4 + packet.length];
		System.arraycopy(packet, 0, newPacket, 4, packet.length);
		BitOps.putInt(newPacket, 0, packet.length);
		os.write(newPacket);
		os.flush();
	}
	
}
