# Arcadia (alpha) #
Arcadia is a Virtual Arcade Console, written in Java, with the purpose of
providing a uniform set of constraints for interacting with graphics, input,
and sound.

## Keybindings ##
<table>
<b><tr><td>Button</td><td>Player 1</td><td>Player 2</td></tr></b>
</table>

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
		
		public void tick(Graphics2D g, Input p1, Input p2, Sound s) {
			g.setColor(color);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
	
		public void reset() {
			color = colors[(int)(Math.random() * colors.length)];
		}
	
		public Image banner() { 
			return null;
		}
		
		public static void main(String[] args) {
			Arcadia.display(new Arcadia(new SampleGame()));
		}
	}
	
We don't need a banner quite yet, so for now we return the null value.

In order to compile our sample game, we have to provide the Arcadia jar as part
of the classpath. Assuming the jar is in the same directory as the code:

	javac -cp .:arcadia.jar SampleGame.java

To run the game, simply execute the `SampleGame` class with arcadia on the
classpath:

	java -cp .:arcadia.jar SampleGame

This will execute your game directly using the main method we provided.

Drawing a solid color isn't that impressive, so lets add some interactivity to
our program. We will draw two circles to the screen, and the circles will
respond to user input.

First, we need to add some state to our `SampleGame` class to represent the
center points of our two spheres.

	private int s1x, s1y;
	private int s2x, s2y;

In the `reset` method, initialize the values to be random along the width and
height.

	s1x = (int)(Math.random() * WIDTH);
	s1y = (int)(Math.random() * HEIGHT);
	s2x = (int)(Math.random() * WIDTH);
	s2y = (int)(Math.random() * HEIGHT);

Finally, draw the spheres using the graphics context in the `tick` method.

	g.setColor(Color.BLACK);
	g.fillOval(s1x - 10, s1y - 10, 20, 20);
	
	g.setColor(Color.BLACK);
	g.fillOval(s2x - 10, s2y - 10, 20, 20);

Compile and run your code. You'll notice that on top of the randomly colored
background are two black dots. Unfortunately the circles don't do anything yet,
so lets tackle that problem.

Both players have a joystick which provides an digital signal for the four
cardinal directions (up, down, left, right). Using the joystick each player
will be able to move the circle around.

Arcadia provides an enumeration (`Button`) of all possible inputs for a 
player. Of interest are the joystick "buttons" named `U`, `D`, `L`, and `R`.
Typically you would refer to these values using the `Button` enumeration, i.e.
`Button.U`, `Button.L`, etc., however we can add a static import in order to
eliminate this verbosity.

	import static arcadia.Button.*;

The `Input` class contains a single method `pressed` which takes as an
argument an instance of `Button` and returns boolean value indicating whether 
or not that button has been pressed. If we wanted to check if player 1 has 
pushed up on the joystick, we can call `p1.pressed(U)`, or more verbosely
`p1.pressed(Button.U)`. In order to make each circle move, we query each
direction for each player and adjust the coordinates accordingly. In the `tick`
method, add the following code.

	if(p1.pressed(U)) { s1y -= 2; }
	if(p1.pressed(D)) { s1y += 2; }
	if(p1.pressed(L)) { s1x -= 2; }
	if(p1.pressed(R)) { s1x += 2; }
        
	if(p2.pressed(U)) { s2y -= 2; }
	if(p2.pressed(D)) { s2y += 2; }
	if(p2.pressed(L)) { s2x -= 2; }
	if(p2.pressed(R)) { s2x += 2; }

Compile and run your code. The keyboard mappings for the game are listed above.
Play around and see the circles move at a slow pace.

In addition to the four cardinal directions, each player has access to three
action buttons labeled `A`, `B`, and `C`. For completeness, lets add some
functionality to each of these buttons. For our purposes, `A` will draw a
white boarder around the circle, `B` will randomly teleport circle, and `C`
will randomly teleport the opposing circle.

	if(p1.pressed(A)) {
		g.setColor(Color.WHITE);
		g.drawOval(s1x - 10, s1y - 10, 20, 20);
	}
	if(p1.pressed(B)) {
		s1x = (int)(Math.random() * WIDTH);
		s1y = (int)(Math.random() * HEIGHT);
	}
	if(p1.pressed(C)) {
		s2x = (int)(Math.random() * WIDTH);
		s2y = (int)(Math.random() * HEIGHT);
	}
	
	if(p2.pressed(A)) {
		g.setColor(Color.WHITE);
		g.drawOval(s2x - 10, s2y - 10, 20, 20);
	}
	if(p2.pressed(B)) {
		s2x = (int)(Math.random() * WIDTH);
		s2y = (int)(Math.random() * HEIGHT);
	}
	if(p2.pressed(C)) {
		s1x = (int)(Math.random() * WIDTH);
		s1y = (int)(Math.random() * HEIGHT);
	}

And now you have a simple "game" which uses the core (currently implemented)
functionality from Arcadia. I wish your luck in your endeavors to create
interesting and fun games as well as powerful game engines.

## Running Multiple Games ##
