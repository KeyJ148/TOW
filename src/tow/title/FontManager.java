package tow.title;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.TrueTypeFont;

public class FontManager {
	
	private static ArrayList<TrueTypeFont> ttFontArray = new ArrayList<TrueTypeFont>();
	private static ArrayList<Integer> sizeArray = new ArrayList<Integer>();
	private static ArrayList<Integer> fontArray = new ArrayList<Integer>();//Тип, например awt.Font.BOLD

	public static int addFont(int size, int font) {
		Font awtFont = new Font(null, font, size);
		ttFontArray.add(new TrueTypeFont(awtFont, false));
		sizeArray.add(size);
		fontArray.add(font);
		return ttFontArray.size()-1;
	}
	
	public static int existFont(int size, int font){
		for (int i=0; i<ttFontArray.size(); i++){
			if (sizeArray.get(i) == size && fontArray.get(i) == font){
				return i;
			}
		}
		
		return -1;
	}
	
	public static TrueTypeFont getFont(int i){
		return ttFontArray.get(i);
	}

}
