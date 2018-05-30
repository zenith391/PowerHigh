package org.lggl.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lggl.ViewportManager;
import org.lggl.game.SimpleGame;
import org.lggl.graphics.objects.GameObject;
import org.lggl.graphics.renderers.IRenderer;
import org.lggl.graphics.renderers.lightning.Lightning;
import org.lggl.input.Keyboard;
import org.lggl.input.Mouse;
import org.lggl.utils.Event;
import org.lggl.utils.LGGLException;

/**
 * 
 *
 */
public class Window {

	private Canvas canvas;
	private JFrame win = new JFrame();
	private int width = 640, height = 480;
	private String title;

	public static final int DISPOSE_ON_CLOSE = 0;
	public static final int EXIT_ON_CLOSE = 1;

	private SimpleGame owner;
	private Keyboard input = new Keyboard();
	private Mouse mouse = new Mouse(-1, -1, this);
	private int buffersNum = 1;
	private int closeOperation = 0;
	private boolean fullscreen;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private GameObject focusedObj;
	private WindowEventThread thread = new WindowEventThread(this);
	private WindowPanel panel = new WindowPanel(this);
	private ArrayList<Event> pendingEvents = new ArrayList<Event>();
	private ViewportManager viewport;
	private Graphics customGraphics;
	
	private int vW, vH;

	public Graphics getCustomGraphics() {
		return customGraphics;
	}

	public void setCustomGraphics(Graphics customGraphics) {
		this.customGraphics = customGraphics;
	}

	private static IRenderer render;

	public static IRenderer getRenderer() {
		return render;
	}

	public static void setRenderer(IRenderer render) {
		Window.render = render;
		render.addPostProcessor(new DefaultPostProcessor());
	}

	static {
		try {
			if (false)
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}

	public boolean shouldRender(GameObject obj) {
		return render.shouldRender(this, obj);
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public boolean isEventAvailable() {
		return !pendingEvents.isEmpty();
	}

	public void setBackground(Color bg) {
		panel.setBackground(bg);
		win.setBackground(Color.BLACK);
	}

	public Color getBackground() {
		return panel.getBackground();
	}

	public Window() {
		this("LGGL Game");
	}

	public Window(String title) {
		init();
		setTitle(title);
		setSize(width, height);
		canvas = new Canvas();
	}

	public ViewportManager getViewportManager() {
		return viewport;
	}

	public void setViewportManager(ViewportManager manager) {
		viewport = manager;
	}

	public void setIcon(Image img) {
		win.setIconImage(img);
	}

	public WindowEventThread getEventThread() {
		return thread;
	}

	public Image getIcon() {
		return win.getIconImage();
	}

	/**
	 * Set whether or not if the Window is visible. If visible equals to true, it's
	 * will execute <code>show()</code>. If visible equals to false, it's will
	 * execute <code>hide()</code>.
	 * 
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		if (visible == true) {
			show();
		}
		if (visible == false) {
			hide();
		}
	}

	/**
	 * Will execute <code>setVisible(true)</code> if visible is equals ignore case
	 * to "Visible" or "True" Or, it's will execute <code>setVisible(false)</code>
	 * if visible is equals ignore case to "Invisible" or "False" If visible is
	 * equals to nothing of these, it will execute <code>setVisible(false)</code>.
	 * 
	 * @param visible
	 */
	public void setVisible(String visible) {
		if (visible.equalsIgnoreCase("visible")) {
			setVisible(true);
		} else if (visible.equalsIgnoreCase("invisible")) {
			setVisible(false);
		} else {
			setVisible(Boolean.valueOf(visible));
		}
	}

	public Window(String title, int width, int height) {
		init();
		setTitle(title);
		setSize(width, height);
	}

	public Window(int width, int height) {
		this("LGGL Game", width, height);
	}

	private void init() {
		if (render == null)
			setRenderer(new Lightning());
		win.setLayout(null);
		win.add(panel);
		win.getContentPane().setBackground(Color.BLACK);
		win.addKeyListener(input);
		panel.addMouseMotionListener(Mouse.getListener());
		panel.addMouseListener(Mouse.getListener2());
		win.setResizable(false);
		win.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				hide();
				win.dispose();
			}
		});
		win.setLocation(500, 500);
		thread.start();
	}

	public void setViewport(int x, int y, int width, int height) {
		panel.setLocation(x, y);
		panel.setSize(width, height);
		vW = width;
		vH = height;
	}

	public Rectangle getViewport() {
		return new Rectangle(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
	}

	public JFrame getJFrame() {
		return win;
	}

	public void setFullscreen(boolean fullscreen) {
		try {
			if (fullscreen == true) {
				win.dispose();

				win.setUndecorated(true);
				win.setExtendedState(JFrame.MAXIMIZED_BOTH);

				show();
			} else {
				win.dispose();

				win.setUndecorated(false);
				win.setExtendedState(JFrame.NORMAL);
				setSize(win.getWidth(), win.getHeight());

				show();
			}
			this.fullscreen = fullscreen;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCloseOperation() {
		return closeOperation;
	}

	public void setCloseOperation(int closeOperation) {
		this.closeOperation = closeOperation;
	}

	public void setResizable(boolean resize) {
		win.setResizable(resize);
	}

	public void setSize(int w, int h) {
		width = w;
		height = h;
		win.setSize(w, h);
	}

	public void show() {
		try {
			canvas.createBufferStrategy(buffersNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		win.setVisible(true);
	}
	
	public void showinit() {
		try {
			canvas.createBufferStrategy(buffersNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hide() {
		win.setVisible(false);
	}

	public void setTitle(String newTitle) {
		title = newTitle;
		win.setTitle(title);
	}

	public Keyboard getKeyboard() {
		return input;
	}

	public void setBufferQuantity(int buffers) {
		buffersNum = buffers;
	}

	public boolean isClosed() {
		return !win.isVisible();
	}

	public boolean isVisible() {
		return win.isVisible();
	}

	public void add(GameObject obj) {
		objects.add(obj);
	}

	public void update() {
		if (viewport != null) {
			Rectangle view = viewport.getViewport(this);
			if (!view.equals(panel.getBounds())) {
				setViewport(view.x, view.y, view.width, view.height);
			}
		}
		if (customGraphics == null) {
			panel.repaint();
		} else {
			// Custom double-buffering
			int w = vW;
			int h = vH;
			if (w < 1) w = 1;
			if (h < 1) h = 1;
			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
			panel.paint(img.createGraphics());
			System.out.println(img);
			customGraphics.drawImage(img, panel.getX(), panel.getY(), null);
		}
	}

	public GameObject[] getObjects() {
		return objects.toArray(new GameObject[objects.size()]);
	}

	public void remove(GameObject obj) {
		try {
			objects.remove(obj);
		} catch (Exception e) {
			throw e;
		}
	}

	public int getWidth() {
		return win.getWidth();
	}

	public int getHeight() {
		return win.getHeight();
	}

	public Mouse getMouse() {
		return mouse;
	}

	public void throwEvent(Event e) {
		pendingEvents.add(0, e);
	}

	public void fireEvent(String type, Object... args) {
		if (type.equals("mousePressed")) {
			GameObject[] a = getObjects();
			int mx = (int) args[0];
			int my = (int) args[1];
			focusedObj = null;
			for (GameObject b : a) {

				if (mx > b.getX() && my > b.getY() && mx < b.getX() + b.getWidth() && my < b.getY() + b.getHeight()) {
					focusedObj = b;
					break;
				}
			}
		}
		if (focusedObj != null)
			focusedObj.onEvent(type, args);
	}

	public Event dispatchEvent() throws LGGLException {
		if (isEventAvailable()) {
			Event evt = pendingEvents.get(pendingEvents.size() - 1);
			pendingEvents.remove(pendingEvents.size() - 1);
			return evt;
		} else {
			throw new LGGLException("No Event");
		}
	}

	public void removeAll() {
		for (GameObject obj : objects) {
			remove(obj);
		}
	}
}
