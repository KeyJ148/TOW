package tow.engine3.image;

import tow.engine2.Global;
import tow.engine2.io.Logger;

import java.util.TreeMap;

public class TextureManager {

	private static TreeMap<String, TextureHandler> textures = new TreeMap<>();
	private static TreeMap<String, TextureHandler[]> animations = new TreeMap<>();

	//Загрузка всех текстур и анимаций при инициализации
	public static void init(){
		String[][] images = Global.storage.getImages();
        for (int i=0; i<images.length; i++){
            TextureHandler texture = new TextureHandler(Global.storage.getPathImagesRoot() + images[i][0],
					images[i][1], Integer.parseInt(images[i][2]));
            textures.put(texture.name, texture);
        }

        String[][] anims = Global.storage.getAnimations();
        for (int i=0; i<anims.length; i++){
            TextureHandler[] animation = parseAnimation(Global.storage.getPathAnimationsRoot() + anims[i][0],
					anims[i][1], Integer.parseInt(anims[i][2]));
            animations.put(anims[i][0].substring(anims[i][0].lastIndexOf("/")+1), animation);
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
		while (Global.getFile(beginPath + "_" + (n+1) + ".png").exists()){
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
