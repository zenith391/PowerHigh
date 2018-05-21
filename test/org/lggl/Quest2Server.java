package test.org.lggl;

import org.lggl.multiplayer.ServerHandler;

public class Quest2Server extends ServerHandler {

	@Override
	public void ProcessPacket(String name, String value) {
		// TODO Auto-generated method stub
		System.out.println("[QUEST2 SERVER] PUTTING" + name+":"+value);
	}

	@Override
	public String GetPacket(String name) {
		System.out.println("[QUEST2 SERVER] ASKING " + name);
		return null;
	}

}
