package tow.engine.image;

import tow.engine2.image.Texture;
import tow.engine2.image.TextureLoader;
import tow.engine2.io.Logger;
import tow.engine2.resources.ResourceLoader;

import java.io.IOException;

public class TextureHandler {
	
	public Texture texture;
    public Mask mask;

    public String name;
	public String type;
	public int depth;

	public TextureHandler(String path, String type, int depth) {
		try {
			this.texture = TextureLoader.getTexture(path);
			Logger.println("Load image \"" + path + "\" complited", Logger.Type.DEBUG_IMAGE);
		} catch (IOException e1) {
			Logger.println("Image \"" + path + "\" not loading", Logger.Type.ERROR);
		}

		this.mask = new Mask(path, getWidth(), getHeight());

        this.name = path.substring(path.lastIndexOf("/")+1, path.lastIndexOf("."));
        this.type = type;
        this.depth = depth;
    }
	
	public int getWidth(){
		return texture.getWidth();
	}
	
	public int getHeight(){
		return texture.getHeight();
	}
	
}
