package org.powerhigh.multiplayer;

public interface ServerHandler {

	/**
	 * Handle full packets, packet sended to this method are always full.<br/>
	 * Using handleFullPacket method, incomplete packets are totally discarded<br/>
	 * Packet types are TCP packets and full UDP packets checked with a checksum.
	 * @param packet
	 */
	public void handleFullPacket(Client cl, byte[] packet);
	
	/**
	 * Handle all packets, if sended through UDP, they might appear incomplete
	 * @param packet
	 */
	public void handleUnsafePacket(Client cl, byte[] packet);
	
}
