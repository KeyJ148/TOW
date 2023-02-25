package cc.abro.orchengine.gameobject.location;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.resources.masks.MaskLoader;

import java.util.List;

public class Border extends GameObject {

    public Border(Location location, BorderData borderData) {
        super(location, borderData.x, borderData.y);
        //Путь должен быть, иначе mask выкинет ошибку при парсе;
        addComponent(new Collision(MaskLoader.createDefaultMask(borderData.w, borderData.h)));
    }

    private static Border createOne(Location location, Type type, int size) {
        return new Border(location, type.getBorderData(location.getWidth(), location.getHeight(), size));
    }

    public static List<Border> createAll(Location location, int size) {
        return List.of(
            createOne(location, Type.NORTH, size),
            createOne(location, Type.EAST, size),
            createOne(location, Type.SOUTH, size),
            createOne(location, Type.WEST, size)
        );
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
