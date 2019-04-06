package org.powerhigh.graphics.renderers.nawork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Interface;
import org.powerhigh.graphics.renderers.IRenderer;
import org.powerhigh.objects.GameObject;
import org.powerhigh.utils.Color;

public class Nawork implements IRenderer {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public Nawork(Socket socket) throws IOException {
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	}
	
	public void close() throws IOException {
		dis.close();
		dos.close();
		socket.close();
	}
	
	@Override
	public void render(Interface win, Drawer draw) {
		try {
			int op = dis.read();
			if (op == 1) { // fill rect
				int x = dis.readUnsignedShort();
				int y = dis.readUnsignedShort();
				int width = dis.readUnsignedShort();
				int height = dis.readUnsignedShort();
				draw.fillRect(x, y, width, height);
			}
			if (op == 2) { // change color
				int rgb = dis.readInt();
				boolean rgba = dis.readBoolean();
				draw.setColor(new Color(rgb, rgba));
			}
			if (op == 3) { // draw rect
				int x = dis.readUnsignedShort();
				int y = dis.readUnsignedShort();
				int width = dis.readUnsignedShort();
				int height = dis.readUnsignedShort();
				draw.drawRect(x, y, width, height);
			}
			if (op == 4) { // draw circle
				int x = dis.readUnsignedShort();
				int y = dis.readUnsignedShort();
				int radius = dis.readUnsignedShort();
				draw.drawCircle(x, y, radius);
			}
			if (op == 5) { // fill circle
				int x = dis.readUnsignedShort();
				int y = dis.readUnsignedShort();
				int radius = dis.readUnsignedShort();
				draw.fillCircle(x, y, radius);
			}
			if (op == 6) // restore state
				draw.restoreState();
			if (op == 7) // save state
				draw.saveState();
			if (op == 8) { // estimated width
				String str = dis.readUTF();
				dos.writeInt(draw.getEstimatedWidth(str));
			}
			if (op == 9) { // estimated height
				dos.writeInt(draw.getEstimatedHeight());
			}
			if (op == 10 ) { // draw text
				int x = dis.readUnsignedShort();
				int y = dis.readUnsignedShort();
				String text = dis.readUTF();
				draw.drawText(x, y, text);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean shouldRender(Interface w, GameObject obj) {
		return false;
	}

	@Override
	public void setUsePostProcessing(boolean use) {
		// unsupported
	}

	@Override
	public boolean isUsingPostProcessing() {
		return false;
	}

}
