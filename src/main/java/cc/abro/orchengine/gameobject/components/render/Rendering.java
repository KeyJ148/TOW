package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.image.Color;

import java.util.Arrays;
import java.util.List;

public abstract class Rendering extends Component {

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
    public List<Class<? extends Component>> getPreliminaryUpdateComponents() {
        return Arrays.asList();
    }

    @Override
    public List<Class<? extends Component>> getPreliminaryDrawComponents() {
        return Arrays.asList();
    }
}
