package org.lggl.multiplayer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

import org.lggl.utils.LGGLException;

public class PackageSession {

	private Socket socket;
	private PrintStream out;
	private BufferedInputStream in;
	private boolean connected;
	private HashMap<String, String> events = new HashMap<>();
	
	
	PackageSession(Socket sock) {
		socket = sock;
	}
	
	public synchronized void send(String packageName, String packageValue) {
		out.println("SET "+packageName+":"+packageValue);
		out.flush();
	}
	
	public synchronized String get(String packageName) throws LGGLException {
		out.println("GET " + packageName);
		out.flush();
		String pck = waitForEvent("return.value."+packageName);
		return pck;
	}
	
	public String waitForEvent(String evtType) throws LGGLException {
		while (!events.containsKey(evtType)) {
			Thread.onSpinWait();
		}
		return events.get(evtType);
	}
	
	private String read() throws LGGLException {
		StringBuilder builder = new StringBuilder();
		try {
			char ch = (char) in.read();
			if (ch == -1)
				return null;
			builder.append(ch);
			while (in.available() != 0) {
				ch = (char) in.read();
				builder.append(ch);
			}
		} catch (IOException e) {
			throw new LGGLException("Network I/O Error: PackageSession.READ()", e);
		}
		return builder.toString();
	}
	
	private static int threadID = 0;
	
	public void connect() throws LGGLException {
		if (connected)
			throw new LGGLException("This package session is arleady connected.");
		try {
			out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
			in = new BufferedInputStream(socket.getInputStream());
		} catch (IOException e) {
			throw new LGGLException("Network I/O Error: PackageSession.CONNECT()", e);
		}
		connected = true;
		Thread th = new Thread() {
			
			public void run() {
				while (true) {
					try {
						String evt = read();
						if (evt.startsWith("EVT ")) {
							String[] evta = evt.split(" ");
							events.put(evta[1], evta[2]);
							System.out.println(evt);
							
						}
					} catch (LGGLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.err.println("EvtThread stop.. Connection disabled");
						break;
					}
				}
			}
			
		};
		th.setName("PckSess-EventThread-"+threadID);
		th.start();
		threadID++;
		
	}
	
}
