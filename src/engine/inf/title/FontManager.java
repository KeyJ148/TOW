package engine.inf.title;

import engine.Global;
import engine.Loader;
import engine.io.Logger;
import game.client.Storage;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FontManager {

	private static ArrayList<TrueTypeFont> ttFontArray = new ArrayList<>();
	private static ArrayList<Integer> sizeArray = new ArrayList<>();
	private static ArrayList<Integer> fontArray = new ArrayList<>();//Тип, например awt.Font.BOLD

	private static String path = "res/font/";
	private static String name = "arial";
	private static char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщьыъэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ".toCharArray();

	public static void init(){
		int[][] fonts = Global.storage.getFonts();
		for(int i=0; i<fonts.length; i++){
			addFont(fonts[i][0], fonts[i][1]);
		}
	}

	public static int addFont(int size, int font) {
		try {
			String type = "";
			switch (font){
				case Font.PLAIN: type = ""; break;
				case Font.BOLD: type = "bd"; break;
				case Font.ITALIC: type = "i"; break;
			}

			InputStream inputStream = ResourceLoader.getResourceAsStream(path + name + type + ".ttf");
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont((float) size);

			ttFontArray.add(new TrueTypeFont(awtFont, true, alphabet));
			sizeArray.add(size);
			fontArray.add(font);
			return ttFontArray.size() - 1;
		} catch (FontFormatException | IOException e){
			Logger.println("Create font", Logger.Type.ERROR);
			Loader.exit();
		}

		return -1;
	}

	public static TrueTypeFont getFont(int size, int font){
		int i = existFont(size, font);
		if (i == -1){
			Logger.println("Font not found (size=" + size + ", font=" + font + ")", Logger.Type.ERROR);
			Loader.exit();
		}

		return ttFontArray.get(i);
	}

	private static int existFont(int size, int font){
		for (int i=0; i<ttFontArray.size(); i++){
			if (sizeArray.get(i) == size && fontArray.get(i) == font){
				return i;
			}
		}

		return -1;
	}



}
