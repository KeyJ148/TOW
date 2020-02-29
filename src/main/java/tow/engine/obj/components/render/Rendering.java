package tow.engine.obj.components.render;

import tow.engine.image.Color;
import tow.engine.obj.QueueComponent;

import java.util.Arrays;
import java.util.List;

public abstract class Rendering extends QueueComponent {

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

    @Override
    public List<Class<? extends QueueComponent>> getComponentsUpdatePreviously() {
        return Arrays.asList();
    }

    @Override
    public List<Class<? extends QueueComponent>> getComponentsDrawPreviously() {
        return Arrays.asList();
    }
}
