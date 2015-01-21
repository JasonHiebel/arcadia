package arcadia;

import static arcadia.Game.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;

import static arcadia.Button.*;

/**
 *
 */
public class GameOverlay extends Overlay {
	private final BufferedImage buffer;
	private final Queue<Overlay> overlays;
	private final Game game;
		
	public GameOverlay(Queue<Overlay> overlays, Game game) {
		super(true, true);
		this.buffer   = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.overlays = overlays;
		this.game     = game;
	}
	
	public synchronized void update(Set<Integer> pressed) {
		GameInput p1 = new GameInput(pressed, GameInput.p1keycodes);
		GameInput p2 = new GameInput(pressed, GameInput.p2keycodes);

		if(p1.pressed(S)) {
			pressed.remove(GameInput.p1keycodes.get(S));
			overlays.add(new PauseOverlay(overlays, game.banner()));
		}
		else {
			game.tick(buffer.createGraphics(), p1, p2, null);
		}
	}
		
	public synchronized void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.drawImage(buffer, 0, 0, WIDTH, HEIGHT, null);
	}
}