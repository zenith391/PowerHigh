package org.lggl.game;

import java.awt.Toolkit;

import org.lggl.Camera;
import org.lggl.audio.Audio;
import org.lggl.graphics.ErrorBox;
import org.lggl.graphics.Window;
import org.lggl.utils.LGGLException;
import org.lggl.utils.debug.DebugLogger;

public abstract class SimpleGame {

	protected Window window = new Window();
	protected Audio audio;
	protected Camera camera = null;

	public abstract void update(Window win);

	public abstract void init(Window win);

	private void dbg() {
		String name = System.getProperty("os.name");
		String ver = System.getProperty("os.version");
		DebugLogger.logInfo("Operating System is " + name + "/" + ver);
	}
	
	/**
	 * Core init of game, if overriding this method, add <code>super.coreInit()</code> unless you know what you're doing !
	 * <p><b>Note:</b> This method is called before init, it is like a pre-init</p>
	 */
	protected void coreInit() {
		try {
			audio = new Audio();
		} catch (LGGLException e) {
			System.err.println("Error when creating Audio system! Aborting.");
			e.printStackTrace();
			System.exit(1);
		}
		camera = new Camera(0, 0);
	}
	
	public Audio getAudio() {
		return audio;
	}

	protected boolean launched;
	
	public void start() {
		try {
			
			DebugLogger.logInfo("Preparing game..");
			dbg();
			coreInit();
			init(window);
			
			window.show();
			
			while (true) {
				this.update(window);
				Thread.sleep(1000 / 60);
				if (!launched) {
					DebugLogger.logInfo("Game launched");
					launched = true;
				}
			}
		} catch (Throwable t) {
			Toolkit.getDefaultToolkit().beep();
			ErrorBox.create()
			.throwable(t)
			.show(window.getJFrame().getContentPane());
			System.exit(1);
		}
	}
}
