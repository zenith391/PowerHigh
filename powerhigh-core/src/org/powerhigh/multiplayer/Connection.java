package org.powerhigh.multiplayer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Connection {

	private DatagramSocket udp;
	private Socket tcp;
	
	public Connection(DatagramSocket udp, Socket tcp) {
		this.tcp = tcp;
		this.udp = udp;
	}
	
	public void close() throws IOException {
		udp.close();
		tcp.close();
	}
	
	/**
	 * Sends an unstable packet that goes through UDP protocol, the packet might be incomplete (unusable) or lost
	 * @param packet
	 * @throws IOException
	 */
	public void sendPacket(byte[] packet) throws IOException {
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
