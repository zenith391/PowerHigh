package org.lggl.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Animation {

	private Properties p;
	private Texture img;
	private int frame;
	private int maxFrame;
	private int tick, mTick;
	private static Thread animThread;
	private static List<Animation> activeAnimations = new ArrayList<>();
	
	/**
	 * Accepts *.GAN (Game ANimation)
	 * @param ani
	 */
	public Animation(File ani) throws IOException {
		
		if (animThread == null) {
			animThread = new Thread(() -> {
				while (true) {
					// Generate only one animation thread
					// This thread handle around 1000 animations. Which is a lot
					for (Animation anim : activeAnimations) {
						anim.tick += 1;
						if (anim.tick >= anim.mTick) {
							anim.tick = 0;
							anim.frame++;
							if (anim.frame > anim.maxFrame) {
								anim.frame = 0;
							}
						}
					}
					try {
						Thread.sleep(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			animThread.setPriority(Thread.MIN_PRIORITY); // I don't think animation accuracy is important.
			animThread.setDaemon(true);
			animThread.start();
		}
		
		ZipFile f = new ZipFile(ani);
		ZipEntry spritesheet = f.getEntry("spritesheet.png");
		ZipEntry desc = f.getEntry("spritesheet.properties");
		
		p = new Properties();
		p.load(f.getInputStream(desc));
		
		try {
			mTick = Integer.parseInt(p.getProperty("millisPerFrame"));
			maxFrame = Integer.parseInt(p.getProperty("totalNumberOfFrames"));
		} catch (Exception e) {
			f.close();
			throw new IOException(ani + " is missing required properties.");
		}
		
		img = TextureLoader.getTexture(f.getInputStream(spritesheet));
		
		f.close();
	}
	
	public boolean toNextFrame() {
		if (frame + 1 >= maxFrame) {
			frame = 0;
			return false;
		} else {
			frame = frame + 1;
			return true;
		}
	}
	
	public void start() {
		activeAnimations.add(this);
	}
	
	public void stop() {
		activeAnimations.remove(this);
	}
	
	public void dispose() {
		stop();
	}
	
	public Texture getCurrentSprite() {
		return null;
	}

}
