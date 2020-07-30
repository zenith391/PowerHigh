package org.powerhigh.multiplayer;

import java.nio.ByteBuffer;

import org.powerhigh.multiplayer.channel.Channel;

public interface ServerHandler {

	/**
	 * Handle full packets, packet sent to this method are always full.<br/>
	 * Using handleFullPacket method, incomplete packets are totally discarded<br/>
	 * Packet types are TCP packets and full UDP packets checked with a checksum.
	 * @param packet
	 */
	public void handleFullPacket(Client cl, Channel ch, ByteBuffer packet);
	
	/**
	 * Handle incomplete packets, if sent through UDP, they might appear incomplete
	 * @param packet
	 */
	public void handleIncompletePacket(Client cl, Channel ch, ByteBuffer packet);
	
}
