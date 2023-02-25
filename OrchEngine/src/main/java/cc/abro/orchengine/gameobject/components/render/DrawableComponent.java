package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import lombok.Getter;

public abstract class DrawableComponent extends PositionableComponent implements Drawable {
    @Getter
    private int z;

    public void setZ(int z) {
        this.z = z;
        notifyListeners();
    }
}
