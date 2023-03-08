package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.Follower;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.tanks.Effect;

public class Armor extends GameObject {

    public static final String PATH_SETTING = "game/armor/";
    public String name, title; //name - техническое название, title - игровое
    public String imageName;

    public Player player;
    public Effect effect = new Effect();

    public int animSpeed;
    public Animation textureHandlers;

    public Armor() {
        super(Context.getService(LocationManager.class).getActiveLocation());
    }

    public void init(Player player, double x, double y, double direction, String name) {
        this.player = player;
        this.name = name;

        loadData();

        setX(x);
        setY(y);
        setDirection(direction);
        addComponent(new AnimationRender(textureHandlers.textures(), 1000)); //TODO вынести глубины в константы
        addComponent(new Movement());
        getComponent(Movement.class).setDirection(direction);
        getComponent(Movement.class).update(0);

        addComponent(new Collision(textureHandlers.mask(), CollidableObjectType.ARMOR));
        getComponent(Collision.class)
                .addListener(CollidableObjectType.BOX, player.controller)
                .addListener(CollidableObjectType.ENEMY_ARMOR, player.controller)
                .addListener(DefaultCollidableObjectType.BORDER, player.controller);
                //TODO .addCollidableObjects(new Class[]{CollisedMapObject.class, DestroyedMapObject.class});

        if (player.gun != null) {
            player.gun.removeComponents(Follower.class);
            player.gun.addComponent(new Follower(this, false));
        }
    }

    @Override
    public void update(long delta) {
        super.update(delta);

        //Если мы мертвы, то ничего не делать
        if (!player.alive) return;

        //Для анимации гусениц
        AnimationRender animationRender = (AnimationRender) getComponent(Rendering.class);
        if (getComponent(Movement.class).speed != 0 && animationRender.getFrameSpeed() == 0) {
            animationRender.setFrameSpeed(animSpeed);
        }
        if (getComponent(Movement.class).speed == 0 && animationRender.getFrameSpeed() != 0) {
            animationRender.setFrameSpeed(0);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        player.effects.remove(effect);
    }

    public String getConfigFileName() {
        return PATH_SETTING + name + ".properties";
    }

    public void loadData() {
        ConfigReader cr = new ConfigReader(getConfigFileName());

        effect.addition.hpMax = cr.findDouble("HP_MAX");
        effect.addition.hpRegen = cr.findDouble("HP_REGEN");
        effect.addition.speedTankUp = cr.findDouble("SPEED_UP");
        effect.addition.speedTankDown = cr.findDouble("SPEED_DOWN");
        effect.addition.directionGunUp = cr.findDouble("DIRECTION_GUN_UP");
        effect.addition.directionTankUp = cr.findDouble("DIRECTION_TANK_UP");
        effect.addition.stability = cr.findInteger("STABILITY");

        animSpeed = cr.findInteger("ANIMATION_SPEED");
        imageName = cr.findString("IMAGE_NAME");
        textureHandlers = getAnimationStorage().getAnimation(imageName);
        title = cr.findString("TITLE");
    }
}
