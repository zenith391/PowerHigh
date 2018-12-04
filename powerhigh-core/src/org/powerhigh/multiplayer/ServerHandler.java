package org.powerhigh.multiplayer;

public interface ServerHandler {

	/**
	 * Handle full packets, packet sended to ServerHandler is always complete.<br/>
	 * Using handleFullPacket method, incomplete packets are totally discarded
	 * @param packet
	 */
	public void handleFullPacket(byte[] packet);
	
	/**
	 * Handle packets, if sended through UDP, they might appear incomplete
	 * @param packet
	 */
	public void handleUnsafePacket(byte[] packet);
	
}
