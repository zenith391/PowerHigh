package org.lggl.graphics.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lggl.graphics.Texture;
import org.lggl.graphics.Window;

public class Sprite extends GameObject {
	
	private Texture texture;
	
	public void setTexture(Texture texture) {
		this.texture = texture;
		if (texture != null) {
			setSize(texture.getWidth(), texture.getHeight());
		}
	}

	public Texture getTexture() {
		return texture;
	}

	/** Will render a blue quad until you set an texture with setTexture(...) */
	public Sprite() {
		this(null);
	}
	/** Will associate the following Texture object with this Sprite. */
	public Sprite(Texture texture) {
		setTexture(texture);
	}
	

	@Override
	public void paint(Graphics g, Window source) {
		if (texture != null) {
			g.drawImage(texture.getAWTImage(), x, y, width, height, null);
		}
		else {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
	}
	

}
