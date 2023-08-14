package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.CollidableObjectType;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.masks.MaskLoader;

import java.util.List;

public class Border extends GameObject {

    public Border(Location location, Type type, int size) {
        super(location);
        BorderData borderData = type.getBorderData(location.getWidth(), location.getHeight(), size);
        setPosition(borderData.x, borderData.y);
        setDirection(90);
        addComponent(new BorderCollision(MaskLoader.getRectangularMask(borderData.w, borderData.h)));
    }

    public static List<Border> createAll(Location location, int size) {
        return List.of(
            new Border(location, Type.NORTH, size),
            new Border(location, Type.EAST, size),
            new Border(location, Type.SOUTH, size),
            new Border(location, Type.WEST, size)
        );
    }

    public enum Type {
        NORTH {
            @Override
            public BorderData getBorderData(int roomWidth, int roomHeight, int size) {
                return new BorderData(roomWidth / 2, -size / 2, roomWidth, size);
            }
        },

        EAST {
            @Override
            public BorderData getBorderData(int roomWidth, int roomHeight, int size) {
                return new BorderData(roomWidth + size / 2, roomHeight / 2, size, roomHeight);
            }
        },

        SOUTH {
            @Override
            public BorderData getBorderData(int roomWidth, int roomHeight, int size) {
                return new BorderData(roomWidth / 2, roomHeight + size / 2, roomWidth, size);
            }
        },

        WEST {
            @Override
            public BorderData getBorderData(int roomWidth, int roomHeight, int size) {
                return new BorderData(-size / 2, roomHeight / 2, size, roomHeight);
            }
        };

        public abstract BorderData getBorderData(int roomWidth, int roomHeight, int size);
    }

    private record BorderData(int x, int y, int w, int h) {
    }

    private static class BorderCollision extends Collision {
        public BorderCollision(Mask mask) {
            super(mask, DefaultCollidableObjectType.BORDER);
        }

        @Override
        public boolean isUnsuitableCollidableObject() {
            return true;
        }
    }

}
