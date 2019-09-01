package org.powerhigh.game;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.powerhigh.Camera;
import org.powerhigh.ViewportManager;
import org.powerhigh.audio.Audio;
import org.powerhigh.audio.AudioImplementation;
import org.powerhigh.graphics.Interface;
import org.powerhigh.input.Input;
import org.powerhigh.objects.Container;
import org.powerhigh.utils.Area;
import org.powerhigh.utils.debug.DebugLogger;

public abstract class SimpleGame {

	protected Interface window = null;
	protected Audio audio = null;
	protected Camera camera = null;
	protected Input input = null;
	
	public static boolean enableLaunchDebug = true;

	public abstract void update(Interface win, double delta);

	public abstract void init(Interface win);
	
	public abstract void exit(Interface win);

	private void dbg() {
		String name = System.getProperty("os.name");
		String ver = System.getProperty("os.version");
		DebugLogger.debug("Operating System is " + name + "/" + ver);
	}
	
	public static class ImplementationSettings {
		
		private Interface itrf;
		private Audio audio;
		
		public static enum Interface {
			
			SWING(),
			OPENGL(),
			ANDROID(),
			JAVAFX();
			
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
				if (this == JAVAFX) {
					return "powerhigh-javafx";
				}
				return null;
			}
			
		}
		
		/**
		 * Includes all implementations that support low-level audio API
		 * @author zenith391
		 *
		 */
		public static enum Audio {
			AWT,    // sun     sound api
			OPENAL, // openal  sample api
			ANDROID;// android media api
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
	protected void implInit() {
		is = getImplementationSettings();
		Objects.requireNonNull(is, "implementationSettings");
		String wcl = "";
		String acl = "";
		if (is.getInterfaceType() == ImplementationSettings.Interface.SWING) {
			wcl = "org.powerhigh.swing.SwingInterfaceImpl";
		}
		if (is.getInterfaceType() == ImplementationSettings.Interface.OPENGL) {
			wcl = "org.powerhigh.opengl.OpenGLInterfaceImpl";
		}
		if (is.getInterfaceType() == ImplementationSettings.Interface.JAVAFX) {
			wcl = "org.powerhigh.jfx.JFXInterfaceImpl";
		}
		if (is.getInterfaceType() == ImplementationSettings.Interface.ANDROID) {
			throw new UnsupportedOperationException("Android");
		}
		
		if (is.getAudioType() == ImplementationSettings.Audio.AWT) {
			acl = "org.powerhigh.swing.audio.SwingAudioImpl";
		}
		
		AudioImplementation aimpl = null;
		try {
			Class<?> cl = Class.forName(wcl);
			Class<?> cl2 = Class.forName(acl);
			try {
				window = (org.powerhigh.graphics.Interface) cl.getConstructor().newInstance();
				aimpl = (org.powerhigh.audio.AudioImplementation) cl2.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			throw new IllegalArgumentException("Unable to locate " + is.getInterfaceType().toString());
		}
		try {
			audio = new Audio(Audio.AUDIO_BIT_24);
			if (aimpl != null)
				Audio.setImplementation(aimpl);
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
	
	public void restartImplementation() {
		
		// Save state
		Container c = window.getObjectContainer();
		ViewportManager manager = window.getViewportManager();
		Area size = window.getSize();
		
		window.hide();
		window.unregister();
		window.getEventThread().interrupt();
		implInit();
		
		// Load state
		/*try {
			Thread.sleep(500); // To wait some implementations to init
		} catch (InterruptedException e) {}*/
		window.show();
		window.setObjectContainer(c);
		window.setSize(size);
		window.setViewportManager(manager);
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
	}

	protected boolean launched, a1;
	
	public void start() {
		try {
			
			if (enableLaunchDebug) {
				DebugLogger.logInfo("Launching game..");
				dbg();
			}
			
			implInit();
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
						if (!launched) {
							if (enableLaunchDebug) {
								DebugLogger.logInfo("Game launched");
							}
							launched = true;
						}
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
