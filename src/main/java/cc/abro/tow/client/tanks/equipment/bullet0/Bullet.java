package cc.abro.tow.client.tanks.equipment.bullet0;


import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.DepthConstants;
import cc.abro.tow.client.settings.GameSettingsService;
import cc.abro.tow.client.tanks.player.PlayerTank;

public class Bullet extends GameObject {
    
    private final int soundRange;

    public static final String PATH_SETTING = "configs/game/bullet0/";
    public String name, title; //name - техническое название, title - игровое

    public double damage; //Дамаг (пушка и в loadData добавляем дамаг пули)
    public int range; //Дальность (пушка и в loadData добавляем дальность пули)
    public int explosionSize;

    public double startX;
    public double startY;
    public long idNet;

    public String sound_shot;
    public String sound_hit;

    public PlayerTank player;
    public String imageName;
    public Sprite texture;

    public Bullet() {
        super(Context.getService(LocationManager.class).getActiveLocation());
        soundRange = Context.getService(GameSettingsService.class).getGameSettings().getSoundRange();
    }

    public void init(PlayerTank player, double x, double y, double dir, double damage, int range, String name) {
        this.player = player;

        this.name = name;
        this.damage = damage; //Дамаг исключительно от выстрелевшей пушки
        this.range = range; //Дальность исключительно от выстрелевшей пушки
        this.idNet = Context.getService(ClientData.class).idNet++;
        this.startX = x;
        this.startY = y;

        addComponent(new Movement());
        this.getComponent(Movement.class).setDirection(dir);

        setX(x);
        setY(y);
        setDirection(dir);
        addComponent(new SpriteRender(texture.texture(), DepthConstants.BULLET_SPRITE_Z));

        addComponent(new Collision(texture.mask(), CollidableObjectType.BULLET));

        Context.getService(TCPControl.class).send(13, getData());
    }

    public String getData() {
        return Math.round(getX())
                + " " + Math.round(getY())
                + " " + getComponent(Movement.class).getDirection()
                + " " + getComponent(Movement.class).getSpeed()
                + " " + imageName
                + " " + idNet;
    }

}
