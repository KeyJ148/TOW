package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.resources.masks.MaskLoader;

public class Border extends GameObject {

    public Border(Location location, Type type, int size) {
        super(location);
        BorderData borderData = type.getBorderData(location.getWidth(), location.getHeight(), size);

        addComponent(new Position(borderData.x, borderData.y, 0));
        //Путь должен быть, иначе mask выкинет ошибку при парсе;
        addComponent(new Collision(MaskLoader.createDefaultMask(borderData.w, borderData.h)));
    }

    public static void createAndAddAll(Location location, int size) {
        new Border(location, Type.NORTH, size);
        new Border(location, Type.EAST, size);
        new Border(location, Type.SOUTH, size);
        new Border(location, Type.WEST, size);
    }

    public record BorderData(int x, int y, int w, int h) {
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
