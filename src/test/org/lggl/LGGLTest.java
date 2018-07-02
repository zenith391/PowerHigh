package test.org.lggl;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import org.lggl.Material;
import org.lggl.SizedViewport;
import org.lggl.audio.AISSound;
import org.lggl.game.SimpleGame;
import org.lggl.graphics.ParticleBlueprint;
import org.lggl.graphics.ParticleBox;
import org.lggl.graphics.TextureLoader;
import org.lggl.graphics.Window;
import org.lggl.objects.Button;
import org.lggl.objects.Particle;
import org.lggl.objects.Sprite;
import org.lggl.objects.SwingObject;
import org.lggl.objects.Text;
import org.lggl.graphics.renderers.SimpleRenderer;
import org.lggl.graphics.renderers.lightning.Lightning;
import org.lggl.input.Keyboard;
import org.lggl.multiplayer.PackageServer;
import org.lggl.multiplayer.PackageSession;
import org.lggl.multiplayer.PackageSystem;
import org.lggl.utils.LGGLException;
import org.lggl.utils.PackOutputStream;
import org.lggl.utils.debug.DebugLogger;

public class LGGLTest extends SimpleGame {

	private Sprite player;
	private Text fps;
	private ParticleBox box;
	private ParticleBlueprint damageBlueprint;

	@Override
	public void update(Window win, double delta) {
		
		handleKeys(win);
		//throw new Error("Random error in random game.");
	}
	
	public void exit(Window win) {
		try {
			PackOutputStream pos = new PackOutputStream(new FileOutputStream("data.pak"), true);
			pos.write(player);
			pos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void handleKeys(Window win) {
		Keyboard keyboard = win.getKeyboard();
		int speed = (int) (5d * win.getEventThread().getDelta());
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
		if (keyboard.isKeyDown(Keyboard.KEY_F11)) {
			win.setFullscreen(!win.isFullscreen());
			keyboard.setKeyDown(Keyboard.KEY_F11, false);
		}
		if (keyboard.isKeyDown(Keyboard.KEY_G)) {
			if (!(Window.getRenderer() instanceof SimpleRenderer)) {
				DebugLogger.logInfo("Changed Renderer to: Simple ()");
				Window.setRenderer(new SimpleRenderer());
			}
		}
		if (keyboard.isKeyDown(Keyboard.KEY_H)) {
			if (!(Window.getRenderer() instanceof Lightning)) {
				DebugLogger.logInfo("Changed Renderer to: Lightning");
				Window.setRenderer(new Lightning());
			}
		}
		Particle p = new Particle(damageBlueprint, 600, 400);
		box.addParticle(p);
		box.windLeft(10, 5);
		fps.setText("FPS: " + win.getFPS() + ", SPF: " + win.getSPF() + ", Delta: " + win.getEventThread().getDelta());
		player.setRotation(player.getRotation() + 1);
	}

	private PackageSession sess;

	public void srv() {
		try {
			PackageServer srv = PackageSystem.createServer(56552);
			srv.setHandler(new Quest2Server(srv));
			srv.start();
		} catch (LGGLException e) {
			e.printStackTrace();
		}
	}
	
	public void dbgsound() {
		AISSound sound = new AISSound(new File("Alonzo - Santana.wav"));
		audio.playSound(sound);
	}

	@Override
	public void init(Window win) {
		
		((Lightning) Window.getRenderer()).turnOffDebug();
		
		win.setViewportManager(new SizedViewport(1280, 720));
		// Iziditor.main(new String[] {});
		try {
			player = new Sprite(TextureLoader.getTexture(new File("base/base_ui_skin.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		fps = new Text();
		box = new ParticleBox(50);
		box.setX(0);
		box.setY(0);
		box.setSize(1280, 720);
		damageBlueprint = new ParticleBlueprint(Color.RED, 10, (short) 50);
		player.setSize(64, 64);
		player.setColor(Color.yellow);
		player.setMaterial(new Material(0.2f));
		player.centerTo(win);
		win.setBackground(Color.BLACK);
		win.setTitle("Hello LGGL!");
		win.add(player);
		win.add(box);
		
		win.setSize(1280, 720);
		SwingObject obj = new SwingObject();
		JTabbedPane pane = new JTabbedPane();
		JButton button = new JButton("Test");
		pane.addTab("JButton", button);
		pane.addTab("JLabel", new JLabel("Hi"));
		Button bt = new Button();
		bt.setText("Musique !");
		bt.setX(100);
		bt.setY(200);
		fps.setY(24);
		fps.setColor(Color.WHITE);
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
				dbgsound();
			}

		});
		Button tbt = new Button();
		tbt.setText("Test Button");
		tbt.setX(400);
		tbt.setY(100);
		tbt.setSize(100, 60);
		win.add(bt);
		win.add(tbt);
		obj.setContent(pane);
		win.add(obj);
		win.add(fps);
		win.setResizable(true);
		obj.setX(100);
		obj.setY(100);
		win.setFullscreenWidth(1280);
		win.setFullscreenHeight(720);
		try {
			sess = PackageSystem.connect(InetAddress.getLocalHost(), 56552);
			System.out.println("Sucefully connected to .. virtual server");
		} catch (UnknownHostException | LGGLException e) {
			System.out.println("Could not connect to .. virtual server");
			srv();
			try {
				sess = PackageSystem.connect(InetAddress.getLocalHost(), 56552);
			} catch (UnknownHostException | LGGLException e1) {
				e1.printStackTrace();
			}
		}
		sess.send("server.playerConnect", "Player1");
		sess.sendPacket((short) 256, new byte[] {52, 47, 96, 32, 14, 58});
		sess.send("server.playerDisconnect", "Player1");
		try {
			System.err.println("Online? " + sess.get("server.isPlayerOnline.Player1"));
		} catch (LGGLException e) {
			e.printStackTrace();
		}
		// for (Desktop.Action act : Desktop.Action.values()) {
		// Desktop desk = Desktop.getDesktop();
		// System.out.println(act + ": " + desk.isSupported(act));
		// }
		
		win.getEventThread().setTargetFPS(60);
	}

	public static void main(String[] args) {
		LGGLTest test = new LGGLTest();
		test.start();
	}

}
