package engine.image;

import engine.io.Logger;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class TextureHandler {
	
	public Texture texture;
    public Mask mask;

    public String name;
	public String type;
	public int depth;

	public TextureHandler(String path, String type, int depth) {
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
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
		return texture.getImageWidth();
	}
	
	public int getHeight(){
		return texture.getImageHeight();
	}
	
}
