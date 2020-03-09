package tow.engine.image;

import tow.engine.resources.textures.Texture;
import tow.engine.resources.textures.TextureLoader;

public class TextureHandler {
	
	public Texture texture;
    public Mask mask; //TODO: mask и name вынести в texture

    public String name;
	public String type; //TODO: type - разные наследники для MapObject
	public int depth; //TODO: depth - свойство для наследника MapObject

	public TextureHandler(String path, String type, int depth) {
		this.texture = TextureLoader.getTexture(path);
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
