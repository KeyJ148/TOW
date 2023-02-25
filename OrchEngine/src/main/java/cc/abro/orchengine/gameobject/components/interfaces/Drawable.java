package cc.abro.orchengine.gameobject.components.interfaces;

public interface Drawable extends Positionable {
    void draw();
    int getZ();

    /*
     * UnsuitableObject-ом считается объект, который может быть больше чанка и поэтому его надо рендерить всегда,
     * даже если он находить вне экрана на отдаление в несколько чанков
     */
    default boolean isUnsuitableObject() {
        return false;
    }
}
