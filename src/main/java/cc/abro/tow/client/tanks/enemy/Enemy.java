package cc.abro.tow.client.tanks.enemy;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.Follower;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.orchengine.resources.animations.AnimationStorage;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.tanks.Tank;

public class Enemy extends Tank {

    public int id = -1;

    public boolean valid = false; //Этот враг уже инициализирован? (Отправл свои данные: цвет, ник)
    public static final long REQUEST_DATA_EVERY_TIME = (long) (0.5 * Math.pow(10, 9));//Промежуток времени между запросами информации о враге
    public long timeLastRequestDelta = 0;
    private long lastNumberPackage = -1; //Номер последнего пакета UDP

    public Enemy(int id) {
        this.id = id;

        setComponent(new Position(0, 0, 0));
        setComponent(new Movement());
    }

    public Enemy(Enemy enemy) {
        this(enemy.id);
        this.valid = enemy.valid;
        this.timeLastRequestDelta = enemy.timeLastRequestDelta;
        this.lastNumberPackage = enemy.lastNumberPackage;
        this.color = enemy.color;
        this.setName(enemy.name);
        this.kill = enemy.kill;
        this.death = enemy.death;
        this.win = enemy.win;
    }

    @Override
    public void update(long delta) {
        super.update(delta);

        if (!valid) {
            timeLastRequestDelta -= delta;
            if (timeLastRequestDelta <= 0) {
                timeLastRequestDelta = REQUEST_DATA_EVERY_TIME;
                Context.getService(TCPControl.class).send(16, String.valueOf(id));
            }
        }
    }

    public void setData(int x, int y, int direction, int directionGun, int speed, double moveDirection, int animSpeed, long numberPackage) {
        if (!Context.getService(ClientData.class).battle) return;
        if (!alive) return;
        if (numberPackage < lastNumberPackage) return;
        lastNumberPackage = numberPackage;

        //Инициализация брони
        if (armor == null) {
            Animation armorAnimation = Context.getService(AnimationStorage.class).getAnimation("a_default");
            armor = new EnemyArmor(x, y, direction, 1000, armorAnimation, this);
            Context.getService(LocationManager.class).getActiveLocation().add(armor);
            setColorArmor(color);

            setComponent(new Follower(armor));
            camera.setComponent(new Follower(armor));
        }

        //Инициализация пушки
        if (gun == null) {
            Texture gunTexture = Context.getService(SpriteStorage.class).getSprite("g_default").texture();
            gun = GameObjectFactory.create(x, y, 2000, directionGun, gunTexture);
            gun.setComponent(new Movement());
            gun.getComponent(Movement.class).directionDrawEquals = false;
            gun.setComponent(new Follower(armor, false));
            Context.getService(LocationManager.class).getActiveLocation().add(gun);
            setColorGun(color);
        }

        //Инициализация камеры
        if (camera == null) {
            initCamera();
            camera.setComponent(new Follower(armor));
        }

        armor.getComponent(Position.class).x = x;
        armor.getComponent(Position.class).y = y;
        armor.getComponent(Position.class).setDirectionDraw(direction);

        gun.getComponent(Position.class).setDirectionDraw(directionGun);

        //Для интерполяции (предсказания) движения врага
        armor.getComponent(Movement.class).speed = speed;
        armor.getComponent(Movement.class).setDirection(moveDirection);

        //Анимация гусениц
        if (alive) {
            AnimationRender animationRender = (AnimationRender) armor.getComponent(Rendering.class);
            if (animationRender.getFrameSpeed() != animSpeed) animationRender.setFrameSpeed(animSpeed);
        }
    }

    public void newArmor(String nameArmor) {
        if (armor == null) return;

        armor.setComponent(new AnimationRender(Context.getService(AnimationStorage.class).getAnimation(nameArmor).textures()));
        setColorArmor(color);

        Context.getService(LocationManager.class).getActiveLocation().checkGameObjectChunkChanged(armor);
    }

    public void newGun(String nameGun) {
        if (gun == null) return;

        gun.setComponent(new SpriteRender(Context.getService(SpriteStorage.class).getSprite(nameGun).texture()));
        setColorGun(color);

        Context.getService(LocationManager.class).getActiveLocation().checkGameObjectChunkChanged(gun);
    }
}
