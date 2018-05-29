package org.lggl.multiplayer;

public abstract class ServerHandler {

	public abstract void ProcessPacket(String name, String value);
	
	public abstract String GetPacket(String name);
}
