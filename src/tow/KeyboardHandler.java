package tow;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class KeyboardHandler {
	
	public ArrayList<Integer> bufferKey = new ArrayList<Integer>();
	public ArrayList<Boolean> bufferState = new ArrayList<Boolean>();
	
	public void update(){
		bufferKey.clear();
		bufferState.clear();
		while (Keyboard.next()) {
			bufferKey.add(Keyboard.getEventKey());
			bufferState.add(Keyboard.getEventKeyState());
		}
	}
}
