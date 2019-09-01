package org.powerhigh.swing;

import org.powerhigh.utils.*;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.awt.AWTEvent;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.PaintEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.powerhigh.graphics.Interface;
import org.powerhigh.graphics.PostProcessor;
import org.powerhigh.graphics.TextureLoader;
import org.powerhigh.input.Input;
import org.powerhigh.swing.input.SwingKeyboard;
import org.powerhigh.swing.input.SwingMouse;
import org.powerhigh.swing.input.SwingTexturePlugin;

public class SwingInterfaceImpl extends Interface {

	private boolean legacyFullscreen;
	private JFrame win;
	private boolean fullscreen;
	private int fullscreenWidth, fullscreenHeight;
	private GraphicsDevice device;
	private boolean closeRequest;
	private boolean painted;
	
	private static GamePanel gamePanel;
	private SwingKeyboard keyboard;
	private SwingMouse mouse;
	private PostProcessor postProcessor;
	
	private CompiledScript shaderScript;
	private Invocable shaderInvocable;
	private ScriptEngine shaderEngine;
	private Object shaderThiz;
	
	public SwingInterfaceImpl() {
		init();
	}
	
	public void setTitle(String title) {
		win.setTitle(title);
	}
	
	public void setPosition(int x, int y) {
		win.setLocation(x, y);
	}
	
	public Point getPosition() {
		return new Point(win.getX(), win.getY());
	}
	
	public void setResizable(boolean resizable) {
		win.setResizable(resizable);
	}
	
	protected void init() {
		win = new JFrame();
		win.setSize(800, 600);
		win.setLocationRelativeTo(null);
		gamePanel = new GamePanel(this);
		keyboard = new SwingKeyboard(this);
		mouse = new SwingMouse(-1, -1, this);
		Input.setKeyboardImpl(keyboard);
		Input.setMouseImpl(mouse);
		TextureLoader.setPlugin(new SwingTexturePlugin());
		win.addKeyListener(keyboard);
		win.setLayout(null);
		gamePanel.addMouseListener(mouse);
		win.getContentPane().setBackground(java.awt.Color.BLACK);
		gamePanel.addMouseMotionListener(mouse);
		win.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				closeRequest = true;
			}
			
		});
		win.add(gamePanel);
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		fullscreenWidth = device.getDisplayMode().getWidth();
		fullscreenHeight = device.getDisplayMode().getHeight();
		fullscreen = false;
		win.getRootPane().setDoubleBuffered(false);
		super.init();
	}
	
	private Area oldViewport;
	
	@Override
	public void update() {
		super.update();
		if (oldViewport != viewport) {
			oldViewport = viewport;
			gamePanel.setBounds(viewport.getX(), viewport.getY(), viewport.getWidth(), viewport.getHeight());
		}
		
		try {
			SwingUtilities.invokeAndWait(() -> {
				gamePanel.repaint();
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setBackground(Color color) {
		win.setBackground(SwingUtils.phColorToAwtColor(color));
	}

	@Override
	public Color getBackground() {
		return new Color(win.getBackground().getRed(), win.getBackground().getGreen(), win.getBackground().getBlue());
	}
	
	public PostProcessor getPostProcessor() {
		return postProcessor;
	}
	
	public void setPostProcessor(PostProcessor postProcessor) throws PowerHighException {
		this.postProcessor = postProcessor;
		if (postProcessor != null) {
			ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
			if (engine == null) {
				throw new PowerHighException("No JavaScript engine found");
			}
			Compilable comp = null;
			try {
				comp = (Compilable) engine;
			} catch (Exception e) {
				throw new PowerHighException("The JavaScript engine cannot compile scripts", e);
			}
			try {
				shaderScript = comp.compile(postProcessor.getShader());
			} catch (ScriptException e) {
				throw new PowerHighException("Could not compile PowerHigh shader", e);
			}
			engine.put("time", (Supplier<Long>) () -> {
				return System.currentTimeMillis();
			});
			try {
				shaderThiz = shaderScript.eval();
			} catch (Exception e) {
				throw new PowerHighException("Could not evaluate PowerHigh shdaer", e);
			}
			shaderInvocable = (Invocable) engine;
			shaderEngine = engine;
		} else {
			shaderScript = null;
		}
	}
	
	public boolean hasMethod(String name) {
		return shaderEngine.get(name) != null;
	}
	
	public Object invokeShader(String name, Object... args) throws Exception {
		return shaderInvocable.invokeFunction(name, args);
	}
	
	public CompiledScript getScript() {
		return shaderScript;
	}
	
	public void setFullscreenSize(int width, int height) {
		fullscreenWidth = width;
		fullscreenHeight = height;
	}
	
	public void setFullscreen(boolean fullscreen) {
		try {
			if (System.getProperty("sun.java2d.opengl", "false").equals("true") && System.getProperty("os.name").toLowerCase().contains("windows")) {
				legacyFullscreen = true;
			}
			if (fullscreen == true) {
				if (device.isFullScreenSupported() && !legacyFullscreen) {
					device.setFullScreenWindow(win);
					DisplayMode found = device.getDisplayMode();
					if (fullscreenWidth != 0 && fullscreenHeight != 0) {
						for (DisplayMode mode : device.getDisplayModes()) {
							if ((mode.getWidth() >= fullscreenWidth && mode.getWidth() < found.getWidth())) {
								if (mode.getHeight() >= fullscreenHeight && mode.getHeight() < found.getHeight()) {
									found = mode;
								}
							}
						}
					}
					if (!found.equals(device.getDisplayMode())) {
						device.setDisplayMode(found);
					}

					win.createBufferStrategy(1);

				} else {
					win.dispose();

					win.setUndecorated(true);
					win.setExtendedState(JFrame.MAXIMIZED_BOTH);

					show();
				}
			} else {
				if (device.isFullScreenSupported() && !legacyFullscreen) {
					device.setFullScreenWindow(null);
					fullscreen = false;
				} else {
					win.dispose();

					win.setUndecorated(false);
					win.setExtendedState(JFrame.NORMAL);

					show();
				}
			}
			this.fullscreen = fullscreen;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		win.setVisible(visible);
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}

	@Override
	public void show() {
		win.setVisible(true);
	}

	@Override
	public void hide() {
		win.setVisible(false);
	}

	@Override
	public boolean isCloseRequested() {
		return closeRequest;
	}

	@Override
	public boolean isVisible() {
		return win.isVisible();
	}

	@Override
	public int getWidth() {
		return win.getWidth();
	}

	@Override
	public int getHeight() {
		return win.getHeight();
	}

	@Override
	public void setSize(int width, int height) {
		win.setSize(width, height);
	}

	@Override
	public Area getSize() {
		return new Area(getWidth(), getHeight());
	}

	@Override
	public void unregister() {
		win.dispose();
	}

}
