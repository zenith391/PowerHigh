package org.lggl.objects;

import java.awt.Color;
import java.awt.Graphics;

import org.lggl.graphics.Animation;
import org.lggl.graphics.Texture;
import org.lggl.graphics.Window;

public class Sprite extends GameObject {
	
	private Texture texture;
	private Animation animation;
	
	public void setTexture(Texture texture) {
		this.texture = texture;
		if (texture != null) {
			setSize(texture.getWidth(), texture.getHeight());
		}
	}
	
	/**
	 * Note: Animation bypass Texture property
	 * @param anim
	 */
	public void setAnimation(Animation anim) {
		this.animation = anim;
	}

	public Texture getTexture() {
		return texture;
	}

	/** Will render a blue quad until you set an texture with setTexture(...) */
	public Sprite() {
		this( (Texture) null);
	}
	/** Will associate the following Texture object with this Sprite. */
	public Sprite(Texture texture) {
		setTexture(texture);
	}
	/** Will associate the following Animation object with this Sprite. */
	public Sprite(Animation anim) {
		setAnimation(anim);
	}
	

	@Override
	public void paint(Graphics g, Window source) {
		if (texture != null) {
			g.drawImage(texture.getAWTImage(), x, y, width, height, null);
		}
		else if (animation != null) {
			g.drawImage(animation.getCurrentSprite().getAWTImage(), x, y, width, height, null);
		}
		else {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
	}
	

}
