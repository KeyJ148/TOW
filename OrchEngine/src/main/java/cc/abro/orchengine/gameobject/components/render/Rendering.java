package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.textures.Texture;
import lombok.Getter;
import lombok.Setter;

public abstract class Rendering extends DrawableComponent {

    @Getter @Setter
    private double scaleX = 1, scaleY = 1;
    @Getter @Setter
    private Color color = Color.WHITE;

    public int getWidth() {
        return (int) (getTexture().getWidth() * scaleX);
    }

    public int getHeight() {
        return (int) (getTexture().getHeight() * scaleY);
    }

    public void setWidth(int width) {
        scaleX = (double) width / getTexture().getWidth();
    }

    public void setHeight(int height) {
        scaleY = (double) height / getTexture().getHeight();
    }

    public void setDefaultSize() {
        scaleX = 1;
        scaleY = 1;
    }

    @Override
    public Class<? extends Component> getComponentClass() {
        return Rendering.class;
    }

    public abstract Texture getTexture();
}
