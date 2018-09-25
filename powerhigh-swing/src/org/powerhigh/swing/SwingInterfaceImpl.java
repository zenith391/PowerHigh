package org.powerhigh.swing;

import org.powerhigh.utils.Area;
import org.powerhigh.utils.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import org.powerhigh.graphics.Interface;
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
	
	private static GamePanel gamePanel;
	private SwingKeyboard keyboard;
	private SwingMouse mouse;
	
	public SwingInterfaceImpl() {
		init();
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
		win.addMouseListener(mouse);
		win.addMouseMotionListener(mouse);
		win.add(gamePanel);
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		fullscreenWidth = device.getDisplayMode().getWidth();
		fullscreenHeight = device.getDisplayMode().getHeight();
		fullscreen = false;
		super.init();
		
		win.setVisible(true);
	}
	
	@Override
	public void update() {
		super.update();
		gamePanel.repaint();
	}
	
	@Override
	public void setBackground(Color color) {
		win.setBackground(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
	}

	@Override
	public Color getBackground() {
		return new Color(win.getBackground().getRed(), win.getBackground().getGreen(), win.getBackground().getBlue());
	}
	
	public void setFullscreen(boolean fullscreen) {
		try {
			if (System.getProperty("sun.java2d.opengl", "false").equals("true")) {
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
		return false;
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
		return new Area(win.getWidth(), win.getHeight());
	}

}
