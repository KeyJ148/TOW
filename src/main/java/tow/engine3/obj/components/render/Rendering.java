package tow.engine3.obj.components.render;

import tow.engine3.obj.Obj;
import tow.engine3.obj.components.Component;
import tow.engine2.image.Color;

public abstract class Rendering extends Component {

    public double scale_x = 1;
    public double scale_y = 1;
    public Color color = new Color(Color.WHITE);

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
