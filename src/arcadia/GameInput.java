package arcadia;

import java.awt.event.*;
import java.util.*;

public class GameInput implements Input {
	private final Set<Button> buttons;
	
	public GameInput() {
		this.buttons = EnumSet.noneOf(Button.class);
	}
	
	public GameInput(Set<Integer> pressed) {
		this();
		
		for(Button button : Button.values()) {
			if(pressed.contains(keycode(button))) { buttons.add(button); }
		}
	}
	
	public boolean pressed(Button button) { 
		return buttons.contains(button); 
	}
	
	private static final Map<Button, Integer> keycodes = new HashMap<>();
	static {
		keycodes.put(Button.L, KeyEvent.VK_LEFT  );
		keycodes.put(Button.R, KeyEvent.VK_RIGHT );
		keycodes.put(Button.U, KeyEvent.VK_UP    );
		keycodes.put(Button.D, KeyEvent.VK_DOWN  );
		keycodes.put(Button.A, KeyEvent.VK_Z     );
		keycodes.put(Button.B, KeyEvent.VK_X     );
		keycodes.put(Button.C, KeyEvent.VK_C     );
		keycodes.put(Button.S, KeyEvent.VK_ESCAPE);
	}
	
	public static int keycode(Button button) {
		return keycodes.get(button);
	}
}