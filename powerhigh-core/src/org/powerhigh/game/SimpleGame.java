package org.powerhigh.game;

import java.lang.reflect.InvocationTargetException;

import org.powerhigh.Camera;
import org.powerhigh.audio.Audio;
import org.powerhigh.graphics.Interface;
import org.powerhigh.input.Input;
import org.powerhigh.utils.debug.DebugLogger;

public abstract class SimpleGame {

	protected Interface window = null;
	protected Audio audio = null;
	protected Camera camera = null;
	protected Input input = null;
	
	public static boolean enableLaunchDebug = true;

	public abstract void update(Interface win, double delta);

	public abstract void init(Interface win);
	
	public void exit(Interface win) {}

	private void dbg() {
		String name = System.getProperty("os.name");
		String ver = System.getProperty("os.version");
		DebugLogger.logInfo("Operating System is " + name + "/" + ver);
	}
	
	public static class ImplementationSettings {
		
		private Interface itrf;
		private Audio audio;
		
		public static enum Interface {
			
			SWING(),
			OPENGL(),
			ANDROID();
			
			public String toString() {
				if (this == SWING) {
					return "powerhigh-swing";
				}
				if (this == OPENGL) {
					return "powerhigh-opengl";
				}
				if (this == ANDROID) {
					return "powerhigh-android";
				}
				return null;
			}
			
		}
		
		public static enum Audio {
			AWT,
			OPENAL,
			ANDROID;
		}
		
		public ImplementationSettings(Interface itrf, Audio audio) {
			this.itrf = itrf;
			this.audio = audio;
		}
		
		public Interface getInterfaceType() {
			return itrf;
		}
		
		public Audio getAudioType() {
			return audio;
		}
		
		
	}
	
	public abstract ImplementationSettings getImplementationSettings();
	private ImplementationSettings is;
	
	/**
	 * Core init of game, if overriding this method, add <code>super.coreInit()</code> unless you know what you're doing !
	 * <p><b>Note:</b> This method is called before init, it is like a pre-init</p>
	 */
	protected void coreInit() {
		is = getImplementationSettings();
		String wcl = "";
		if (is.getInterfaceType() == ImplementationSettings.Interface.SWING) {
			wcl = "org.powerhigh.swing.SwingInterfaceImpl";
		}
		if (is.getInterfaceType() == ImplementationSettings.Interface.OPENGL) {
			wcl = "org.powerhigh.opengl.OpenGLInterfaceImpl";
		}
		if (is.getInterfaceType() == ImplementationSettings.Interface.ANDROID) {
			throw new UnsupportedOperationException("Android");
		}
		
		try {
			Class<?> cl = Class.forName(wcl);
			try {
				window = (org.powerhigh.graphics.Interface) cl.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			throw new IllegalArgumentException(is.getInterfaceType().toString() + " jar is not in dependencies");
		}
		try {
			audio = null;
			if (enableLaunchDebug)
				DebugLogger.logInfo("Audio ready !");
		} catch (Exception e) {
			System.err.println("Error when creating Audio system! Aborting.");
			e.printStackTrace();
			System.exit(1);
		}
		input = new Input(window);
		camera = new Camera(0, 0);
	}
	
	public Audio getAudio() {
		return audio;
	}
	
	public void setFrameRate(int frames) {
		if (window != null) {
			window.getEventThread().setFrameRate(frames);
		}
	}

	protected boolean launched, a1;
	
	public void start() {
		try {
			
			if (enableLaunchDebug) {
				DebugLogger.logInfo("Preparing game..");
				dbg();
			}
			
			coreInit();
			init(window);
			window.show();
			
			window.getEventThread().addUpdateListener(new Runnable() {
				public void run() {
					try {
						update(window, window.getEventThread().getDelta());
						if (window.isCloseRequested() && a1 == false) {
							exit(window);
							a1 = true;
						}
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			});
			
			while (true) {
				Thread.sleep(1000 / 60);
				if (!launched) {
					if (enableLaunchDebug) {
						DebugLogger.logInfo("Game launched");
					}
					launched = true;
				}
			}
		} catch (Throwable t) {
//			t.printStackTrace();
//			Toolkit.getDefaultToolkit().beep();
//			Component parent = window.getJFrame().getContentPane();
//			if (!window.isVisible()) {
//				parent = null;
//			}
//			ErrorBox.create()
//				.throwable(t)
//					.show(parent);
//			System.exit(1);
			t.printStackTrace();
		}
	}
}
