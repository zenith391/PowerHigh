package org.powerhigh.objects;

import org.powerhigh.components.MeshRender;
import org.powerhigh.components.Renderer;
import org.powerhigh.components.Transform;
import org.powerhigh.graphics.Animation;
import org.powerhigh.graphics.Drawer;
import org.powerhigh.graphics.IMeshRenderer;
import org.powerhigh.graphics.Texture;
import org.powerhigh.math.Vector2;
import org.powerhigh.utils.Color;
import org.powerhigh.graphics.Interface;
import org.powerhigh.graphics.Material;

/**
 * Object able to display a texture or an animation.
 */
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
			Transform t = getTransform();
			t.size.x = texture.getWidth();
			t.size.y = texture.getHeight();
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

	/** Will render a blue quad until you set a texture with {@link Sprite#setTexture(Texture)} */
	public Sprite() {
		addComponent(Transform.class);
		
		Renderer r = addComponent(Renderer.class);
		r.material = new Material(Color.WHITE);
		
		MeshRender render = addComponent(MeshRender.class);
		render.meshRenderer = new IMeshRenderer() {

			@Override
			public void render(Renderer r, Vector2 size, Drawer d) {
				int width = (int) size.x;
				int height = (int) size.y;
				if (texture != null) {
					d.drawTexture(0, 0, width, height, texture);
				}
				else if (animation != null) {
					if (animation.getCurrentSprite() != null)
						d.drawTexture(0, 0, width, height, animation.getCurrentSprite());
				}
				else {
					d.setColor(Color.BLUE);
					d.fillRect(0, 0, width, height);
				}
			}
			
		};
	}
	
	/** Will associate the following {@link org.powerhigh.graphics.Texture} object with this Sprite. */
	public Sprite(Texture texture) {
		this();
		setTexture(texture);
	}
	
	/** Will associate the following {@link org.powerhigh.graphics.Animation} object with this Sprite. */
	public Sprite(Animation anim) {
		this();
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

}
