package cc.abro.tow.client;

public enum CollidableObjectType implements cc.abro.orchengine.gameobject.components.collision.CollidableObjectType {
    ARMOR, ENEMY_ARMOR, BULLET, WALL, BOX;

    @Override
    public boolean equals(cc.abro.orchengine.gameobject.components.collision.CollidableObjectType collidableObjectType) {
        return this == collidableObjectType;
    }
}
