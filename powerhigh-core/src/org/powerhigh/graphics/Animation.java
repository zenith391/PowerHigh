package org.powerhigh.graphics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Animation {

	private Properties p;
	private Texture img;
	private Texture[] loadedTextures;
	private int frame;
	private int maxFrame;
	private int tick, mTick;
	private static Thread animThread;
	private static List<Animation> activeAnimations = new ArrayList<>();
	private ZipFile file;
	private String fileName;
	
	static {
		activeAnimations = Collections.synchronizedList(activeAnimations);
	}
	
	/**
	 * Accepts *.GAN (Game ANimation)
	 * @param ani
	 */
	public Animation(File ani) throws IOException {
		
		if (animThread == null) { // The animation thread only get launched at the first animation instanced by the game.
			animThread = new Thread(() -> {
				while (true) {
					// Generate only one animation thread
					for (Animation anim : activeAnimations) {
						if (anim.loadedTextures == null) {
							anim.loadedTextures = new Texture[anim.maxFrame];
						}
						anim.tick += 1;
						if (anim.tick >= anim.mTick) {
							anim.tick = 0;
							anim.toNextFrame();
							if (loadedTextures[anim.frame] == null) {
								String name = anim.fileName.replace("{%FRAME%}", String.valueOf(anim.frame));
								ZipEntry entry = anim.file.getEntry(name);
								try {
									anim.loadedTextures[anim.frame] = TextureLoader.getTexture(anim.file.getInputStream(entry));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							anim.img = anim.loadedTextures[anim.frame];
						}
					}
					try {
						Thread.sleep(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			animThread.setName("powerhigh-animation-thread");
			animThread.setPriority(Thread.MIN_PRIORITY); // Performance over animation speed accuracy
			animThread.setDaemon(true);
			animThread.start();
		}
		
		file = new ZipFile(ani);
		ZipEntry desc = file.getEntry("spritesheet.properties");
		
		p = new Properties();
		p.load(file.getInputStream(desc));
		
		try {
			mTick = Integer.parseInt(p.getProperty("millisPerFrame"));
			maxFrame = Integer.parseInt(p.getProperty("totalNumberOfFrames"));
			fileName = p.getProperty("image_name", "{%FRAME%}");
		} catch (Exception e) {
			file.close();
			throw new IOException(ani + " is missing required properties.");
		}
		
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
	
	/**
	 * The frame must already be loaded.
	 * @param id
	 * @return
	 */
	public Texture getFrame(int id) {
		return loadedTextures[id];
	}
	
	public int getLength() {
		return maxFrame;
	}
	
	public void dispose() {
		stop();
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Texture getCurrentSprite() {
		return img;
	}

}
