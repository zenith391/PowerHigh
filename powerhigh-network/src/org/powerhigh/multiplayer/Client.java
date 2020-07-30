package org.powerhigh.multiplayer;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class Client {

	private HashMap<String, Object> data;
	private SocketAddress address;
	private SocketChannel tcpSocket;
	
	public Client(HashMap<String, Object> savedData, SocketAddress addr, SocketChannel tcp) {
		tcpSocket = tcp;
		address = addr;
		data = savedData;
	}
	
	public SocketAddress getInetAddress() {
		return address;
	}
	
	public SocketChannel getTcpSocket() {
		return tcpSocket;
	}
	
	public Object getData(String name) {
		return data.get(name);
	}
	
	public void setData(String name, Object value) {
		data.put(name, value);
	}
	
}
