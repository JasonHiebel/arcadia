package intro;

import arcadia.*;
import arcadia.util.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

import arcadia.Button;
import static arcadia.Button.*;

import java.util.List;

public class IntroGame extends Game {
	private final Image banner;
	private final SelectionList<Slide> slides;
	private final Selection<Slide> selection;
	
	private long ticks;
	private final Tweener pulse;
		
	public IntroGame() {
		this.banner = new BufferedImage(512, 128, BufferedImage.TYPE_INT_ARGB);
		{
			Graphics2D g = ((BufferedImage)banner).createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON  );
	    	g.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_QUALITY);
	    	
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
			g.fillRect(0, 0, 512, 128);
			
			g.setColor(Color.RED);
			U.draw(g, (512 - 40) / 2 - 180, (128 - 40) / 2 + 30, 40, 40);
			R.draw(g, (512 - 40) / 2 - 120, (128 - 40) / 2 + 30, 40, 40);
			D.draw(g, (512 - 40) / 2 -  60, (128 - 40) / 2 + 30, 40, 40);
			L.draw(g, (512 - 40) / 2 -   0, (128 - 40) / 2 + 30, 40, 40);
			A.draw(g, (512 - 40) / 2 +  60, (128 - 40) / 2 + 30, 40, 40);
			B.draw(g, (512 - 40) / 2 + 120, (128 - 40) / 2 + 30, 40, 40);
			C.draw(g, (512 - 40) / 2 + 180, (128 - 40) / 2 + 30, 40, 40);
			
			g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.50f));
			g.fillRect(0, 0, 512, 128);
			
			g.setColor(Color.RED);
			new SkyhookFont(25).draw(g, (512 - 150) / 2, (128 - 64) / 2, "Arcadia");
		}
		
		this.slides = new SelectionList<Slide>();
		this.slides.add(new WelcomeSlide());
		this.slides.add(new GoalSlide()   );
		this.slides.add(new WhatSlide()   );
		this.slides.add(new WhatNotSlide());
		this.slides.add(new SkyhookSlide());
		this.slides.add(new GoodbyeSlide());
		
		this.selection = this.slides.selection();
		
		this.ticks = 0;
		this.pulse = new SinTweener(0.3, 1.0);	
	}
	
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
	
	public void reset() {
	
	}
	
	public void tick(Graphics2D g2d, Input p1, Input p2, Sound s) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON  );
    	g2d.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);

		ticks += 1;
		selection.current().tick(g2d, p1, p2, s);
		
		if(ticks > 15 && p1.pressed(R)) { selection.next();     ticks = 0; }
		if(ticks > 15 && p1.pressed(L)) { selection.previous(); ticks = 0; }
		
		pulse.tick(0.025);
		
		g2d.setColor(new Color((float)pulse.value(), 0.0f, 0.0f, 1.0f));
		if(!selection.hasPrevious()) { g2d.setColor(new Color(0.3f, 0.0f, 0.0f, 1.0f)); }
		L.draw(g2d, WIDTH - 100, HEIGHT - 50, 30, 30);
		
		g2d.setColor(new Color((float)pulse.value(), 0.0f, 0.0f, 1.0f));
		if(!selection.hasNext()) { g2d.setColor(new Color(0.3f, 0.0f, 0.0f, 1.0f)); }
		R.draw(g2d, WIDTH -  50, HEIGHT - 50, 30, 30);
	}
	
	public Image banner() { 
		return banner;
	}
	
	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new IntroGame()));
	}
	
	private abstract class Slide {
		protected final SkyhookFont lFont = new SkyhookFont(40);
		protected final SkyhookFont mFont = new SkyhookFont(25);
		protected final SkyhookFont sFont = new SkyhookFont(15);
		
		public abstract void tick(Graphics2D g2d, Input p1, Input p2, Sound s);
		
		public void standard(Graphics2D g2d, String header, String... bullets) {
			Point2D point = new Point2D.Double(50, 150);
			
			g2d.setColor(Color.RED);
			lFont.draw(g2d, 50, 50, header);
			for(String bullet : bullets) {
				point = mFont.draw(g2d, point, bullet);
				point.setLocation(point.getX(), point.getY() + 20);
			}
		}
	}
	
	private class WelcomeSlide extends Slide {
		public void tick(Graphics2D g2d, Input p1, Input p2, Sound s) {
			g2d.setColor(Color.RED);
			sFont.draw(g2d, 50, 30, "Hiebel Productions");
			sFont.draw(g2d, WIDTH - 35 * 14 - 50, 30, "sponsored by Husky Game Development");
			sFont.draw(g2d, 50, HEIGHT / 2 - 50, "Welcome to"); 
			lFont.draw(g2d, 50, HEIGHT / 2 - 20, "Arcadia");
		}
	}
	
	private class GoalSlide extends Slide {
		private String[] bullets = {
			"To create a fun and engaging experience,\nconsistant and easily available\n",
			"To provide a \"simple\" platform for new\ngame designers to gain experience\n",
			"To encourage creativity through\nrestrictive constraints\n"
		};
	
		public void tick(Graphics2D g2d, Input p1, Input p2, Sound s) {
			standard(g2d, "Goals", bullets);
		}
	}
	
	private class WhatSlide extends Slide {
		private String[] bullets = {
			"Arcade Console Virtual Machine\n",
			"Multiplexing Selector\n",
			"1 and/or 2 Player Arcade-Style Games\n",
			"Simple Controls (U,D,L,R,A,B,C)\n",
			"Simple Audio (8- or 16-bit Sampled)\n"
		};
	
		public void tick(Graphics2D g2d, Input p1, Input p2, Sound s) {
			standard(g2d, "What Is Arcadia?", bullets);
		}
	}
	
	private class WhatNotSlide extends Slide {
		private String[] bullets = {
			"Game Engine or Physics Engine\n",
			"General Purpose\n",
		};
	
		public void tick(Graphics2D g2d, Input p1, Input p2, Sound s) {
			standard(g2d, "What Isn't Arcadia?", bullets);
		}
	}
	
	private class SkyhookSlide extends Slide {
		private String[] examples = {
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"abcdefghijklmnopqrstuvwxyz",
			"0123456789",
			"!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~",
		};
	
		public void tick(Graphics2D g2d, Input p1, Input p2, Sound s) {
			g2d.setColor(Color.RED);
		
			lFont.draw(g2d, 50, 50, "Skyhook Mono");
			for(int x = 0; x < examples.length; x += 1) {
				mFont.draw(g2d, 50, 85 * x + 150,      examples[x]);
				sFont.draw(g2d, 50, 85 * x + 150 + 40, examples[x]);
			}
		}	
	}
	
	private class GoodbyeSlide extends Slide {
		public void tick(Graphics2D g2d, Input p1, Input p2, Sound s) {
			g2d.setColor(Color.RED);
			lFont.draw(g2d, 50, HEIGHT / 2 - 20, "Have Fun!");
			sFont.draw(g2d, 50, HEIGHT / 2 - 50, "Thank You, and");  
			sFont.draw(g2d, 50, 30, "Hiebel Productions");
			sFont.draw(g2d, WIDTH - 35 * 14 - 50, 30, "sponsored by Husky Game Development");
		}
	}
}