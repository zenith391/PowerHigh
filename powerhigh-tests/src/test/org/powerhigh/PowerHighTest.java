package test.org.powerhigh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.powerhigh.Material;
import org.powerhigh.SizedViewport;
import org.powerhigh.game.SimpleGame;
import org.powerhigh.graphics.*;
import org.powerhigh.graphics.ParticleBlueprint.ParticleRenderer;
import org.powerhigh.graphics.renderers.SimpleRenderer;
import org.powerhigh.graphics.renderers.lightning.Lightning;
import org.powerhigh.input.AbstractKeyboard;
import org.powerhigh.input.KeyCodes;
import org.powerhigh.objects.Button;
import org.powerhigh.objects.Particle;
import org.powerhigh.objects.Rectangle;
import org.powerhigh.objects.Sprite;
import org.powerhigh.objects.Text;
import org.powerhigh.utils.*;
import org.powerhigh.utils.debug.DebugLogger;

public class PowerHighTest extends SimpleGame {

	private Sprite player;
	private Text fps;
	private ParticleBox box;
	private ParticleBlueprint damageBlueprint;
	private ImplementationSettings impl = new ImplementationSettings(ImplementationSettings.Interface.SWING, ImplementationSettings.Audio.AWT);

	@Override
	public void update(Interface win, double delta) {
		delta = 1;
		fps.setText("FPS: " + win.getFPS() + ", Sleep Time: " + win.getEventThread().getSleepTime() + ", Delta: " + win.getEventThread().getDelta());
		handleKeys(win, delta);
		
		Particle p = new Particle(damageBlueprint, 50, 500);
		box.addParticle(p);
		box.windRandom((int)(4d*delta), (int)(5*delta));
		player.setRotation(player.getRotation() + 1);
	}
	
	public void exit(Interface win) {
		System.exit(0);
	}

	public void handleKeys(Interface win, double delta) {
		AbstractKeyboard keyboard = win.getInput().getKeyboard();
		int speed = (int) (5d * delta);
		player.setX((int) (player.getX() + (input.getAxis("Horizental") * speed)));
		player.setY((int) (player.getY() + (input.getAxis("Vertical") * speed)));
		if (keyboard.isKeyDown(KeyCodes.KEY_G)) {
			if (!(Interface.getRenderer() instanceof SimpleRenderer)) {
				DebugLogger.logInfo("Changed Renderer to: Simple (Just for tests)");
				Interface.setRenderer(new SimpleRenderer());
			}
		}
		if (keyboard.isKeyDown(KeyCodes.KEY_H)) {
			if (!(Interface.getRenderer() instanceof Lightning)) {
				DebugLogger.logInfo("Changed Renderer to: Lightning (Default)");
				Interface.setRenderer(new Lightning());
			}
		}
	}

	@Override
	public void init(Interface win) {
		Rectangle firecamp = new Rectangle(25, 505, 70, 20, Color.LIGHT_GRAY);
		win.add(firecamp);
		win.setViewportManager(new SizedViewport(800, 600));
		player = new Sprite();
		fps = new Text();
		box = new ParticleBox(512);
		box.setX(0);
		box.setY(0);
		ParticleRenderer damageRenderer = new ParticleRenderer() {

			@Override
			public void render(Drawer d, Particle p) {
				int a = p.getLife() % 255;
				if (a < 1) {
					a = 1;
				}
				a -= 255;
				a *= -1;
				Color c = new Color(255, 255, 255, a);
				d.setColor(c);
				int size = p.getBlueprint().getSize();
				float dd = (float) a / 255;
				size *= dd;
				d.fillRect(p.getX(), p.getY(), size, size);
			}
			
		};
		damageBlueprint = new ParticleBlueprint(10, (short)255, damageRenderer);
		player.setSize(128, 128);
		player.setColor(Color.YELLOW);
		player.setMaterial(new Material(0.2f));
		//player.centerTo(win);
		try {
			player.setAnimation(new Animation(new File("Dumb Man.gan")));
			player.getAnimation().start();
		} catch (IOException e2) {
			e2.printStackTrace();
			player.setSize(128, 128);
		}
		win.setBackground(Color.CYAN);
		win.add(player);
		
		Button bt = new Button();
		bt.setText("Hello World!");
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
					bt.setText("Test, again");
				} else {
					bt.setSize(100, 60);
					bt.setText("Hello World!");
				}
			}

		});
		Button stj = new Button("Switch to JavaFX");
		stj.setX(400);
		stj.setY(100);
		stj.setSize(100, 60);
		stj.addAction(() -> {
			impl = new ImplementationSettings(ImplementationSettings.Interface.JAVAFX, ImplementationSettings.Audio.AWT);
			restartImplementation();
		});
		
		Button sts = new Button("Switch to Swing");
		sts.setX(550);
		sts.setY(100);
		sts.setSize(100, 60);
		sts.addAction(() -> {
			impl = new ImplementationSettings(ImplementationSettings.Interface.SWING, ImplementationSettings.Audio.AWT);
			restartImplementation();
		});
		
		win.add(bt);
		//win.add(stj);
		//win.add(sts);
		win.add(fps);

		win.add(box);
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("shaders/colors.s");
			//win.setPostProcessor(new PostProcessor(new String(fis.readAllBytes())));
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		PowerHighTest test = new PowerHighTest();
		test.start();
	}

	@Override
	public ImplementationSettings getImplementationSettings() {
		return impl;
	}

}
