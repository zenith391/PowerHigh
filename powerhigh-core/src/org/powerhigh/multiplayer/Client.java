package org.powerhigh.multiplayer;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class Client {

	private HashMap<String, Object> data;
	private InetAddress address;
	private Socket tcpSocket;
	
	public Client(HashMap<String, Object> savedData, InetAddress addr, Socket tcp) {
		tcpSocket = tcp;
		address = addr;
		data = savedData;
	}
	
	public InetAddress getInetAddress() {
		return address;
	}
	
	public Socket getTcpSocket() {
		return tcpSocket;
	}
	
	public Object getData(String name) {
		return data.get(name);
	}
	
	public void setData(String name, Object value) {
		data.put(name, value);
	}
	
}
