# Arcadia (alpha) #
Arcadia is a Virtual Arcade Console, written in Java, with the purpose of
providing a uniform set of constraints for interacting with graphics, input,
and sound.

## Getting Started ##
In order to write a game for the Arcadia system, your main class must extend
the `Game` abstract class. This class provides two static constants, `WIDTH`
and `HEIGHT`, and requires the implementation of three methods, `tick`, `reset`,
and `cover`. 

When a game is loaded, Arcadia will initialize the game using its no-argument 
constructor. The `cover` method is then called, and the result is cached for 
the remainder of execution. Each time the game is started, Arcadia begins by 
calling the `reset` method and then attempts to call the `tick` method at a rate
of 30 frames per second.

Lets see a simple example which simply draws a randomly chosen colored 
background to the screen and has a pure white cover:

	import arcadia.*;

	import java.awt.*;
	import java.awt.image.*;

	public class SampleGame extends Game {
		private final Color[] colors;
		private Color color;

		public SampleGame() {
			colors = new Color[] {
				Color.BLUE, 
				Color.CYAN, 
				Color.DARK_GRAY, 
				Color.GRAY, 
				Color.GREEN,
				Color.LIGHT_GRAY,
				Color.MAGENTA,
				Color.ORANGE,
				Color.PINK,
				Color.RED,
				Color.YELLOW
			};
			reset();	
		}
		
		public void tick(Graphics2D g, Input i, Sound s) {
			g.setColor(color);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
	
		public void reset() {
			color = colors[(int)(Math.random() * colors.length)];
		}
	
		public Image cover() { 
			Image cover = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			{
				Graphics2D g = ((BufferedImage)cover).createGraphics();
				
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
			return cover;
		}
		
		public static void main(String[] args) {
			Arcadia.display(new Arcadia(new SampleGame()));
		}
	}
	
In order to compile our sample game, we have to provide the Arcadia jar as part
of the classpath. Assuming the jar is in the same directory as the code:

	javac -cp .:arcadia.jar SampleGame.java

To run the game, simply execute the `SampleGame` class with arcadia on the
classpath:

	java -cp .:arcadia.jar SampleGame

This will execute your game directly, bypassing the multiplexing functionality.

## Running Multiple Games ##
