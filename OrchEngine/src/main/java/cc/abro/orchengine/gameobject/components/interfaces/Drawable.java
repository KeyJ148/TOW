package cc.abro.orchengine.gameobject.components.interfaces;

public interface Drawable extends Positionable {
    void draw();
    int getZ();

    /*public void setZ(int z) { TODO setZ Listener! И вызываеть его при каждом setZ. Location добавляет свои listener в каждый объект и таким образом обновляет кеши и чанки
        int previousZ = this.z;
        this.z = z;
        //TODO if gameObj == null ??? getGameObject().getLocation.updateComponentZ(gameObject, previousZ);
    }*/

    /*
     * UnsuitableObject-ом считается объект, который может быть больше чанка и поэтому его надо рендерить всегда,
     * даже если он находить вне экрана на отдаление в несколько чанков
     */
    default boolean isUnsuitableObject() {
        return false;
    }
}
