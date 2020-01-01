package tow.game.client.map;

import tow.engine3.image.TextureHandler;
import tow.engine2.io.Logger;
import tow.engine3.obj.components.Collision;

public class Wall extends MapObject {

    public int stabillity;

    public Wall(double x, double y, double direction, TextureHandler textureHandler, int mid){
        super(x, y, direction, textureHandler, mid);

        collision = new Collision(this, textureHandler.mask);
        stabillity = getStabillityByType(textureHandler.type);
    }

    private int getStabillityByType(String type){
        switch (type){
            case "home": return 100;
            case "tree": return 30;
            default:
                Logger.println("This type not have stability parameter: " + type, Logger.Type.ERROR);
                return 100;
        }
    }

    public void destroyByArmor(){
        destroy();
    }
}
