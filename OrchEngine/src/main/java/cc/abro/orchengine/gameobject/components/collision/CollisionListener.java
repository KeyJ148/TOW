package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.gameobject.GameObject;

//Интерфейс реализуют все слушатели
//При коллизии с объектом из списка коллизий вызывается метод collision
@FunctionalInterface
public interface CollisionListener {
    void collision(GameObject gameObject);
}
