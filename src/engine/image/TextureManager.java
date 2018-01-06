package engine.image;

import engine.io.Logger;

import java.io.File;
import java.util.TreeMap;

import static game.client.TextureStorage.*;

public class TextureManager {

	private static TreeMap<String, TextureHandler> textures = new TreeMap<>();
	private static TreeMap<String, TextureHandler[]> animations = new TreeMap<>();

	//Загрузка всех текстур и анимаций при инициализации
	public static void init(){
        for (int i=0; i<image.length; i++){
            TextureHandler texture = new TextureHandler(pathImageRoot + image[i][0], image[i][1], Integer.parseInt(image[i][2]));
            textures.put(texture.name, texture);
        }

        for (int i=0; i<anim.length; i++){
            TextureHandler[] animation = parseAnimation(pathAnimRoot + anim[i][0], anim[i][1], Integer.parseInt(anim[i][2]));
            animations.put(anim[i][0].substring(anim[i][0].lastIndexOf("/")+1), animation);
        }
	}

	public static TextureHandler getTexture(String name){
        if (textures.containsKey(name)) return textures.get(name);

        Logger.println("Not find texture: " + name, Logger.Type.ERROR);
        return textures.get("error");
    }

    public static TextureHandler[] getAnimation(String name){
        if (animations.containsKey(name)) return animations.get(name);

        Logger.println("Not find animation: " + name, Logger.Type.ERROR);
        return null;
    }
	
	private static TextureHandler[] parseAnimation(String path, String type, int depth){
		int n=0;
		String beginPath = path + "/" + path.substring(path.lastIndexOf("/")+1);
		while (new File(beginPath + "_" + (n+1) + ".png").exists()){
			n++;
		}
		
		TextureHandler[] textureHandler = new TextureHandler[n];
		
		//Загрузка изображений
		for(int i=0;i<n;i++){
			textureHandler[i] = new TextureHandler(beginPath + "_" + (i+1) + ".png", type, depth);
		}
		
		return textureHandler;
	}

}
