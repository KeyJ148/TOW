package tow.game.client.map.types;

import tow.engine.Global;
import tow.engine.gameobject.components.Collision;
import tow.engine.logger.Logger;
import tow.engine.resources.sprites.Sprite;
import tow.game.client.map.MapObject;

public class Wall extends MapObject {

    public int stability;

    public Wall(double x, double y, double direction, Sprite sprite, int mid){
        super(x, y, direction, sprite.getTexture(), mid);

        setComponent(new Collision(sprite.getMask()));
        stability = getStabilityByType(textureHandler.type);
    }

    private int getStabilityByType(String type){
        switch (type){
            case "home": return 100;
            case "tree": return 30;
            default:
                Global.logger.println("This type not have stability parameter: " + type, Logger.Type.ERROR);
                return 100;
        }
    }

    public void destroyByArmor(){
        destroy();
    }
}
