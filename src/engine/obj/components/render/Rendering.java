package engine.obj.components.render;

import engine.obj.Obj;
import engine.obj.components.Component;
import org.newdawn.slick.Color;

public abstract class Rendering extends Component {

    public double scale_x = 1;
    public double scale_y = 1;
    public Color color = Color.white;

    public Rendering(Obj obj) {
        super(obj);
    }

    public abstract int getWidthTexture();
    public abstract int getHeightTexture();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract void setWidth(int width);
    public abstract void setHeight(int height);
    public abstract void setDefaultSize();
}
