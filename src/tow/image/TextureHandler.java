package tow.image;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import tow.Global;

public class TextureHandler {
	
	public Texture texture;
	public String path;
	public Mask mask;

	public TextureHandler(String path) {
		try {
			this.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
			if (Global.setting.DEBUG_CONSOLE_IMAGE) Global.p("Load image \"" + path + "\" complited.");
		} catch (IOException e1) {
			Global.error("Image \"" + path + "\" not loading");
		}
		this.path = path;
		this.mask = new Mask(path, getWidth(), getHeight());
	}
	
	public int getWidth(){
		return texture.getImageWidth();
	}
	
	public int getHeight(){
		return texture.getImageHeight();
	}
	
}
