package arcadia;

import arcadia.util.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import java.util.List;

import static arcadia.Game.*;
import static arcadia.Button.*;

/**
 *
 */
public class SelectOverlay extends Overlay {
	private final Queue<Overlay>      overlays;
	private final SelectionList<Game> games;
	private final Selection<Game>     selection;
	
	private       Tweener tweener;
	private final Tweener pulse;
	
	class SinTweener extends Tweener {
		private double a;
		private double b;

		public SinTweener(double a, double b) {
			this.a = a;
			this.b = b;
		}

		public double value() {
			time %= 1.0;
			return (b - a) * (1 + Math.sin(time * 2 * Math.PI)) / 2 + a;
		}
	}
		
	public SelectOverlay(Queue<Overlay> overlays, Game... games) {
		super(true, true);
		this.overlays  = overlays;
		this.games     = new SelectionList<>(Arrays.asList(games));
		this.selection = this.games.selection();
		this.pulse     = new SinTweener(0.3, 1.0);	
	}
	
	public synchronized void update(Set<Integer> pressed) {
		if(tweener != null) { 
			if(tweener.isComplete()) { tweener = null; }
			else { tweener.tick(0.25); }
		}
		else {
			GameInput input = new GameInput(pressed, GameInput.p1keycodes);
			if(input.pressed(S)) { overlays.add(new CreditsOverlay(overlays)); }
				
			if(input.pressed(U) && selection.hasPrevious()) { 
				selection.previous(); 
				tweener = new LinearTweener(-128 - 20, 0); }
			if(input.pressed(D) && selection.hasNext()) { 
				selection.next();
				tweener = new LinearTweener(+128 + 20, 0);
			}
			if(input.pressed(A)) {
				pressed.remove(KeyEvent.VK_Z);
				selection.current().reset();
				overlays.add(new GameOverlay(overlays, selection.current()));
			}
		}
	}
		
	public synchronized void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		// draw game covers
		List<Game> window = new ArrayList<Game>();
		window.add(selection.current());
		
		Selection<Game> prev = selection.copy();
		window.add(0, prev.previous());
		window.add(0, prev.previous());
		window.add(0, prev.previous());
		
		Selection<Game> next = selection.copy();
		window.add(next.next());
		window.add(next.next());
		window.add(next.next());
		
		
		final int yBase   = (HEIGHT - 128) / 2;
		final int yOffset = 128 + 20;
		
		
		for(int y = 0; y < 7; y += 1) {
			int yPos = yBase + (y - 3) * yOffset;
			if(tweener != null) { yPos += tweener.value(); }
			
			Game g = window.get(y);
			if(g != null) {
				g2d.drawImage(
					g.banner(),
					(WIDTH - 512) / 2, yPos, (WIDTH + 512) / 2, yPos + 128,
					0, 0, 512, 128,
					null
				);
			}
		}

		// draw interface
		
		g2d.setColor(new Color(0.0f, (float)pulse.value(), (float)pulse.value(), 1.0f));
		g2d.setStroke(new BasicStroke(2.0f));
		if(!selection.hasPrevious()) { g2d.setColor(new Color(0.0f, 0.3f, 0.3f, 1.0f)); }
		U.draw(g2d, (WIDTH - 512) / 2 - 50, (HEIGHT - 30) / 2 - 50, 30, 30);
		
		g2d.setColor(new Color(0.0f, (float)pulse.value(), (float)pulse.value(), 1.0f));
		g2d.setStroke(new BasicStroke(2.0f));
		if(!selection.hasNext()) { g2d.setColor(new Color(0.0f, 0.3f, 0.3f, 1.0f)); }
		D.draw(g2d, (WIDTH - 512) / 2 - 50, (HEIGHT - 30) / 2 + 50, 30, 30);
		
		g2d.setColor(new Color(0.0f, (float)pulse.value(), (float)pulse.value(), 1.0f));
		A.draw(g2d, (WIDTH - 512) / 2 - 50, (HEIGHT - 30) / 2, 30, 30);
		S.draw(g2d, WIDTH - 50, HEIGHT - 50, 30, 30);
		
		g2d.setColor(Color.CYAN);
		new SkyhookFont(20).draw(g2d, 20, HEIGHT - 45, "Arcadia");
		new SkyhookFont(20).draw(g2d, WIDTH - 160, HEIGHT - 45, "Credit");
		
		// update
		pulse.tick(0.025);
	}
}