package arcadia;

import arcadia.*;
import arcadia.util.*;

import intro.IntroGame;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Arcadia extends JPanel implements KeyListener, Runnable {
	private final BufferedImage buffer;
	private final Queue<Overlay> overlays;
	private final Set<Integer> pressed;
	
	private Arcadia() {
		this.buffer   = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.overlays = Collections.asLifoQueue(new LinkedList<Overlay>());
		this.pressed  = new HashSet<Integer>();
	}
	
	public Arcadia(Game... games) {
		this();
		this.overlays.add(new SelectOverlay(overlays, games));
	}
	
	public Arcadia(Game game) {
		this();
		this.overlays.add(new GameOverlay(overlays, game));
	}
	
	public void paint(Graphics g) {
		synchronized(buffer) { 
			((Graphics2D)g).drawImage(buffer, 0, 0, Game.WIDTH, Game.HEIGHT, null); 
		}
	}
	
	public void run() {
		final int FPS_GOAL = 30;
		
		while(!overlays.isEmpty()) {
			long start = System.currentTimeMillis();
			
			synchronized(buffer) {
				Graphics2D g2d = buffer.createGraphics();
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				
				g2d.setColor(Color.BLACK);
				g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
				
				Queue<Overlay> toUpdate = Collections.asLifoQueue(new LinkedList<Overlay>());
				for(Overlay overlay : overlays) {
					toUpdate.add(overlay);
					if(overlay.focused()) { break; }
				}
				
				Queue<Overlay> toDraw = Collections.asLifoQueue(new LinkedList<Overlay>());
				for(Overlay overlay : overlays) {
					toDraw.add(overlay);
					if(overlay.opaque()) { break; }
				}
				
				for(Overlay overlay : toUpdate) { overlay.update(pressed);  }
				for(Overlay overlay : toDraw  ) { overlay.draw(g2d); }
			}
			
			repaint();
			long total = System.currentTimeMillis() - start;
			
			if(total < 1000 / FPS_GOAL) {
				try { Thread.sleep(1000 / FPS_GOAL - total); }
				catch(InterruptedException e) { }
			}
		}
		
		System.exit(0);
	}
	
	public void keyPressed(KeyEvent e) {
		pressed.add(e.getKeyCode());
	}
	
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
	}
	
	public void keyTyped(KeyEvent e) { }
	
	public static void display(Arcadia arcadia) {
		arcadia.setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		arcadia.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		
		new Thread(arcadia).start();
		
		JFrame frame = new JFrame("Arcadia");
		frame.add(arcadia);
		frame.addKeyListener(arcadia);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] { new IntroGame(), new IntroGame(), new IntroGame() }));
	}
}