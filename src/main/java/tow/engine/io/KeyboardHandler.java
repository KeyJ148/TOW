package tow.engine.io;

import java.util.ArrayList;

public class KeyboardHandler {
	
	public static ArrayList<Integer> bufferKey = new ArrayList<Integer>();
	public static ArrayList<Character> bufferChar = new ArrayList<>();
	public static ArrayList<Boolean> bufferState = new ArrayList<Boolean>();//True - нажато, false - отпущено

	//Доступные для отображения символы
	public static String availableChars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM" +
			   							  "ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ" +
										  "1234567890`~!@#$%^&*()-_+=[]{};:'\"<>,./?\\|";

	public static void update(){

		//TODO:
		/*
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});


		bufferKey.clear();
		bufferChar.clear();
		bufferState.clear();
		while (Keyboard.next()) {
			bufferKey.add(Keyboard.getEventKey());
			bufferChar.add(isAvailableChar(Keyboard.getEventCharacter())? Keyboard.getEventCharacter():null);
			bufferState.add(Keyboard.getEventKeyState());
		}
		*/
	}

	public static boolean isKeyDown(int key){
		return false; //TODO
	}

	public static boolean isAvailableChar(char c){
		return availableChars.contains(String.valueOf(c));
	}

}
