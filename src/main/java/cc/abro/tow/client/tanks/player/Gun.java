package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Follower;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.tanks.Effect;

public class Gun extends GameObject {

    public static final String PATH_SETTING = "game/gun/";
    public String name, title; //name - техническое название, title - игровое
    public String imageName;

    public int countTrunk; //Кол-во стволов
    public Vector2<Integer>[] trunksOffset; //Смещение конца каждого ствола по (x,y) (откуда вылетает снаряд)
    public double[] directionTrunk; //Направление конца каждого ствола (в каком направление вылетает снаряд)

    public Effect effect = new Effect();
    public long nanoSecFromAttack = 0;//Кол-во времени до конца перезарядки в наносекундах

    public Player player;
    public Texture texture;

    public void init(Player player, double x, double y, double direction, String name) {
        this.player = player;
        this.name = name;

        loadData();

        setComponent(new Position(x, y, 2000, direction));
        setComponent(new SpriteRender(texture));

        setComponent(new Movement());
        getComponent(Movement.class).directionDrawEquals = false;

        setComponent(new Follower(player.armor, false));
    }

    @Override
    public void update(long delta) {
        super.update(delta);

        //Если мы мертвы, то ничего не делать
        if (!player.alive) return;

        //Уменьшаем время до выстрела
        nanoSecFromAttack -= delta;
    }

    @Override
    public void destroy() {
        super.destroy();
        player.effects.remove(effect);
    }

    public void attack() {
        nanoSecFromAttack = (long) ((double) 1 / player.stats.attackSpeed * Math.pow(10, 9)); //Устанавливаем время перезарядки

        //По очереди стреляем из всех стволов
        for (int i = 0; i < countTrunk; i++) {
            attackFromTrunk(trunksOffset[i].x, trunksOffset[i].y, directionTrunk[i]);
        }
    }

    private void attackFromTrunk(int trunkX, int trunkY, double direction) {
        double trunkXdx = trunkX * Math.cos(Math.toRadians(getComponent(Position.class).getDirectionDraw()) - Math.PI / 2);//первый отступ "вперед"
        double trunkXdy = trunkX * Math.sin(Math.toRadians(getComponent(Position.class).getDirectionDraw()) - Math.PI / 2);//в отличие от маски мы отнимаем от каждого по PI/2
        double trunkYdx = trunkY * Math.cos(Math.toRadians(getComponent(Position.class).getDirectionDraw()) - Math.PI);//потому что изначально у теустуры измененное направление
        double trunkYdy = trunkY * Math.sin(Math.toRadians(getComponent(Position.class).getDirectionDraw()) - Math.PI);//второй отступ "вбок"

        Bullet newBullet = player.bullet.create();
        newBullet.init(
                player,
                getComponent(Position.class).x + trunkXdx + trunkYdx,
                getComponent(Position.class).y - trunkXdy - trunkYdy,
                getComponent(Position.class).getDirectionDraw() + direction,
                player.stats.damage,
                player.stats.range,
                player.bullet.name
        );

        Manager.getService(LocationManager.class).getActiveLocation().getMap().add(newBullet);
    }

    public String getConfigFileName() {
        return PATH_SETTING + name + ".properties";
    }

    public void loadData() {
        ConfigReader cr = new ConfigReader(getConfigFileName());

        countTrunk = cr.findInteger("COUNT_TRUNK");
        trunksOffset = new Vector2[countTrunk];
        directionTrunk = new double[countTrunk];

        for (int i = 0; i < countTrunk; i++) {
            int trunkX = cr.findInteger("TRUNK_" + (i + 1) + "_X");
            int trunkY = cr.findInteger("TRUNK_" + (i + 1) + "_Y");
            double trunkDir = cr.findInteger("TRUNK_" + (i + 1) + "_DIR");

            trunksOffset[i] = new Vector2<>(trunkX, trunkY);
            directionTrunk[i] = trunkDir;
        }

        effect.addition.attackSpeed = cr.findDouble("ATTACK_SPEED");
        effect.addition.directionGunUp = cr.findDouble("DIRECTION_UP");
        effect.addition.damage = cr.findDouble("DAMAGE");
        effect.addition.range = cr.findInteger("RANGE");

        imageName = cr.findString("IMAGE_NAME");
        texture = Manager.getService(SpriteStorage.class).getSprite(imageName).getTexture();
        title = cr.findString("TITLE");
    }
}