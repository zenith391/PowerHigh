package test.org.lggl;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.SystemTray;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

import org.lggl.game.SimpleGame;
import org.lggl.graphics.Window;
import org.lggl.graphics.objects.Button;
import org.lggl.graphics.objects.Rectangle;
import org.lggl.graphics.objects.SwingObject;
import org.lggl.graphics.renderers.SimpleRenderer;
import org.lggl.graphics.renderers.lightning.Lightning;
import org.lggl.input.Keyboard;
import org.lggl.multiplayer.PackageServer;
import org.lggl.multiplayer.PackageSession;
import org.lggl.multiplayer.PackageSystem;
import org.lggl.tools.Iziditor;
import org.lggl.utils.LGGLException;
import org.lggl.utils.debug.DebugLogger;

public class LGGLTest extends SimpleGame {

	private Rectangle player;
	
	@Override
	public void update(Window win) {
		win.setViewport(0, 0, win.getWidth(), win.getHeight());
		
		handleKeys(win);
		
		if (win.isClosed()) {
			win.hide();
			System.exit(0);
		}
	}
	
	public void handleKeys(Window win) {
		Keyboard keyboard = win.getKeyboard();
		int speed = 2;
		if (keyboard.isKeyDown(Keyboard.KEY_D)) {
			player.setX(player.getX() + speed);
		}
		if (keyboard.isKeyDown(Keyboard.KEY_Q)) {
			player.setX(player.getX() - speed);
		}
		if (keyboard.isKeyDown(Keyboard.KEY_Z)) {
			player.setY(player.getY() - speed);
		}
		if (keyboard.isKeyDown(Keyboard.KEY_S)) {
			player.setY(player.getY() + speed);
		}
		if (keyboard.isKeyDown(Keyboard.KEY_G)) {
			if (!(Window.getRenderer() instanceof SimpleRenderer)) {
				DebugLogger.logInfo("Changed Renderer to: Simple (Default)");
				Window.setRenderer(new SimpleRenderer());
			}
		}
		if (keyboard.isKeyDown(Keyboard.KEY_H)) {
			if (!(Window.getRenderer() instanceof Lightning)) {
				DebugLogger.logInfo("Changed Renderer to: Lightning (WIP)");
				Window.setRenderer(new Lightning());
			}
		}
	}

	private PackageSession sess;
	public void srv() {
		try {
			PackageServer srv = PackageSystem.createServer(56552);
			srv.start();
		} catch (LGGLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void init(Window win) {
		Iziditor.main(new String[] {});
		srv();
		player = new Rectangle();
		player.setRounded(true);
		player.centerTo(win);
		win.setBackground(Color.CYAN);
		win.setTitle("LGGL Test Application");
		win.add(player);
		SwingObject obj = new SwingObject();
		JTabbedPane pane = new JTabbedPane();
		JButton button = new JButton("Test");
		Button bt = new Button();
		bt.setText("Hello World!");
		bt.setX(100);
		bt.setY(200);
		bt.setWidth(100);
		bt.setHeight(60);
		bt.addAction(new Runnable() {

			@Override
			public void run() {
				if (bt.getText().equals("Hello World!")) {
					bt.setSize(200, 80);
					bt.setText("Don't Hug Me I'm Scared");
				} else {
					bt.setSize(100, 60);
					bt.setText("Hello World!");
				}
			}
			
		});
		win.add(bt);
		obj.setContent(button);
		win.add(obj);
		win.setResizable(true);
		obj.setX(100);
		obj.setY(100);
		try {
			sess = PackageSystem.connect(InetAddress.getLocalHost(), 56552);
			System.out.println("Sucefully connected to .. virtual server");
		} catch (UnknownHostException | LGGLException e) {
			System.out.println("Could not connect to .. virtual server");
			e.printStackTrace();
		}
		sess.send("server.playerConnect", "Player1");
//		for (Desktop.Action act : Desktop.Action.values()) {
//			Desktop desk = Desktop.getDesktop();
//			System.out.println(act + ": " + desk.isSupported(act));
//		}
	}
	
	public static void main(String[] args) {
		new LGGLTest().start();
	}

}
