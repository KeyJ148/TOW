package cc.abro.orchengine.gameobject.components.collision;

public enum DefaultCollidableObjectType implements CollidableObjectType {
    BORDER;

    @Override
    public boolean equals(CollidableObjectType collidableObjectType) {
        return this == collidableObjectType;
    }
}
