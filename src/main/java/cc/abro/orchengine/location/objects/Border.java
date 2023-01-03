package cc.abro.orchengine.location.objects;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.location.Location;
import cc.abro.orchengine.resources.masks.MaskLoader;

public class Border extends GameObject {

    public Border(int roomWidth, int roomHeight, Type type, int size) {
        BorderData borderData = type.getBorderData(roomWidth, roomHeight, size);

        setComponent(new Position(borderData.x, borderData.y, 0));
        //Путь должен быть, иначе mask выкинет ошибку при парсе;
        setComponent(new Collision(MaskLoader.createDefaultMask(borderData.w, borderData.h)));
    }

    public static void createAndAddAll(Location location, int size) {
        location.add(new Border(location.getWidth(), location.getHeight(), Type.NORTH, size));
        location.add(new Border(location.getWidth(), location.getHeight(), Type.EAST, size));
        location.add(new Border(location.getWidth(), location.getHeight(), Type.SOUTH, size));
        location.add(new Border(location.getWidth(), location.getHeight(), Type.WEST, size));
    }

    public static record BorderData(int x, int y, int w, int h) {
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

}
