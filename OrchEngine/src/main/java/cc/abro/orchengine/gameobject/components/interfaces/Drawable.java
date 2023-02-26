package cc.abro.orchengine.gameobject.components.interfaces;

import java.util.function.Consumer;

public interface Drawable extends Positionable {
    void draw();
    int getZ();

    void addChangeZListener(Consumer<Drawable> listener);
    void removeChangeZListener(Consumer<Drawable> listener);
    void notifyChangeZListeners();

    /*
     * UnsuitableObject-ом считается объект, который может быть больше чанка и поэтому его надо рендерить всегда,
     * даже если он находить вне экрана на отдаление в несколько чанков
     */
    default boolean isUnsuitableObject() {
        return false;
    }
}
