package arcadia;

import java.awt.Graphics2D;
import java.awt.Image;

public abstract class Game {
	public static final int WIDTH  = 1024;
	public static final int HEIGHT = 576;

	public abstract void tick(Graphics2D g, Input p1, Input p2, Sound s);
	public abstract void reset();
	public abstract Image banner();
}