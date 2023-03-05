package cc.abro.orchengine.gameobject.components.collision;

//Интерфейс реализуют все слушатели
//При коллизии с объектом из списка коллизий вызывается метод collision
@FunctionalInterface
public interface CollisionListener {
    void collision(CollidableComponent collision);
}
