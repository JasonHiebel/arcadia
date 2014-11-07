package arcadia;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public abstract class Overlay {
	private boolean focused;
	private boolean opaque;
	
	protected Overlay(boolean focused, boolean opaque) {
		this.focused = focused;
		this.opaque  = opaque;
	}

	public abstract void update(Set<Integer> pressed);
	public abstract void draw(Graphics2D g2d);
	
	public final boolean focused() { return focused; }
	public final boolean opaque() { return opaque; }
}