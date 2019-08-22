package org.powerhigh.objects;

import org.powerhigh.graphics.Animation;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.Texture;
import org.powerhigh.utils.Color;
import org.powerhigh.graphics.Interface;

public class Sprite extends GameObject {
	
	private Texture texture;
	private Animation animation;
	
	/**
	 * Sets the texture of this Sprite. Have more priority than Animation, so if
	 * set will overwrite any existing animation. If null, animation, if defined,
	 * will be used.<br/><br/>
	 * 
	 * The sprite, if texture isn't null, will resize to fit perfectly. 
	 * @param texture
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
		if (texture != null) {
			setSize(texture.getWidth(), texture.getHeight());
		}
	}
	
	/**
	 * <b>Note</b>: Texture bypass Animation property
	 * @param anim
	 */
	public void setAnimation(Animation anim) {
		this.animation = anim;
	}
	
	public Animation getAnimation() {
		return animation;
	}

	public Texture getTexture() {
		return texture;
	}

	/** Will render a blue quad until you set an texture with {@link Sprite#setTexture(Texture)} */
	public Sprite() {
		this((Texture) null);
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
	public void dispose() {
		super.dispose();
		if (animation != null)
			animation.dispose();
		animation = null;
		texture = null;
	}
	

	@Override
	public void paint(Drawer g, Interface source) {
		if (texture != null) {
			g.drawTexture(x, y, width, height, texture);
		}
		else if (animation != null) {
			if (animation.getCurrentSprite() != null)
				g.drawTexture(x, y, width, height, animation.getCurrentSprite());
		}
		else {
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
		}
	}
	

}
