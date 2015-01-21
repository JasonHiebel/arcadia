package arcadia;

import java.awt.event.*;
import java.util.*;

public class GameInput implements Input {
	private final Set<Button> buttons;
	
	public GameInput() {
		this.buttons = EnumSet.noneOf(Button.class);
	}
	
	public GameInput(Set<Integer> pressed, Map<Button, Integer> keycodes) {
		this();
		
		for(Button button : Button.values()) {
			if(pressed.contains(keycodes.get(button))) { buttons.add(button); }
		}
	}
	
	public boolean pressed(Button button) { 
		return buttons.contains(button); 
	}
	
	public static final Map<Button, Integer> p1keycodes = new HashMap<>();
	static {
		p1keycodes.put(Button.L, KeyEvent.VK_A         );
		p1keycodes.put(Button.R, KeyEvent.VK_D         );
		p1keycodes.put(Button.U, KeyEvent.VK_W         );
		p1keycodes.put(Button.D, KeyEvent.VK_S         );
		p1keycodes.put(Button.A, KeyEvent.VK_BACK_QUOTE);
		p1keycodes.put(Button.B, KeyEvent.VK_1         );
		p1keycodes.put(Button.C, KeyEvent.VK_2         );
		p1keycodes.put(Button.S, KeyEvent.VK_ESCAPE    );
	}
	
	public static final Map<Button, Integer> p2keycodes = new HashMap<>();
	static {
		p2keycodes.put(Button.L, KeyEvent.VK_LEFT  );
		p2keycodes.put(Button.R, KeyEvent.VK_RIGHT );
		p2keycodes.put(Button.U, KeyEvent.VK_UP    );
		p2keycodes.put(Button.D, KeyEvent.VK_DOWN  );
		p2keycodes.put(Button.A, KeyEvent.VK_COMMA );
		p2keycodes.put(Button.B, KeyEvent.VK_PERIOD);
		p2keycodes.put(Button.C, KeyEvent.VK_SLASH );
		p2keycodes.put(Button.S, KeyEvent.VK_ESCAPE);
	}
}