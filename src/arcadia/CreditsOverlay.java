package arcadia;

import arcadia.util.*;
import static arcadia.Game.*;
import static arcadia.Button.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;


/**
 *
 */
public class CreditsOverlay extends Overlay {
	private final Queue<Overlay> overlays;
	private final GameInput input;
	
	private final SkyhookFont lFont = new SkyhookFont(40);
	private final SkyhookFont mFont = new SkyhookFont(15);
	
	private boolean quit;
		
	public CreditsOverlay(Queue<Overlay> overlays) {
		super(true, true);
		this.overlays = overlays;
		this.input    = new GameInput();
		this.quit     = false;
	}
	
	public synchronized void update(Set<Integer> pressed) {
		GameInput input = new GameInput(pressed, GameInput.p1keycodes);
		if(input.pressed(S)) { overlays.remove(); }
	}
		
	public synchronized void draw(Graphics2D g2d) {
		g2d.setColor(new Color(0.0f, 0.0f, 0.0f, 0.7f));  
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		g2d.setColor(new Color(0.1f, 0.1f, 0.1f, 0.9f));
		g2d.fillRect(0, HEIGHT / 2 - 40, WIDTH, 165);
		
		g2d.setColor(Color.CYAN);
		lFont.draw(g2d, 50, HEIGHT / 2 - 20, "PAUSED");
		mFont.draw(g2d, 50, HEIGHT / 2 + 70, "CONTINUE");
		mFont.draw(g2d, 50, HEIGHT / 2 + 90, "QUIT");
		
		if(!quit) { g2d.fillRect(35, HEIGHT / 2 + 70, 5, 15); }
		else      { g2d.fillRect(35, HEIGHT / 2 + 90, 5, 15); }
	}
}