package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.image.Color;

public abstract class Rendering extends Component implements Drawable {

    public double scale_x = 1;
    public double scale_y = 1;
    public Color color = Color.WHITE;

    public abstract int getWidthTexture();

    public abstract int getHeightTexture();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void setWidth(int width);

    public abstract void setHeight(int height);

    public abstract void setDefaultSize();

    @Override
    public Class getComponentClass() {
        return Rendering.class;
    }
}
