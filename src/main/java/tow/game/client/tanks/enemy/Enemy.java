package tow.game.client.tanks.enemy;

import tow.engine2.Global;
import tow.engine3.image.TextureHandler;
import tow.engine3.image.TextureManager;
import tow.engine3.obj.Obj;
import tow.engine3.obj.components.Movement;
import tow.engine3.obj.components.Position;
import tow.engine3.obj.components.render.Animation;
import tow.engine3.obj.components.render.Sprite;
import tow.game.client.ClientData;
import tow.game.client.tanks.Tank;

public class Enemy extends Tank {

    public int id = -1;

    public boolean valid = false; //Этот враг уже инициализирован? (Отправл свои данные: цвет, ник)
    public static final long REQUEST_DATA_EVERY_TIME = (long) (0.5 * Math.pow(10, 9));//Промежуток времени между запросами информации о враге
    public long timeLastRequestDelta = 0;
    private long lastNumberPackage = -1; //Номер последнего пакета UDP

    public Enemy(int id){
        this.id = id;

        position = new Position(this, 0, 0, 0);
        movement = new Movement(this);
    }

    @Override
    public void update(long delta){
        super.update(delta);

        if (!valid) {
            timeLastRequestDelta -= delta;
            if (timeLastRequestDelta <= 0) {
                timeLastRequestDelta = REQUEST_DATA_EVERY_TIME;
                Global.tcpControl.send(16, String.valueOf(id));
            }
        }
    }

    public void setData(int x, int y, int direction, int directionGun, int speed, double moveDirection, int animSpeed, long numberPackage){
        if (!ClientData.battle) return;
        if (numberPackage < lastNumberPackage) return;
        lastNumberPackage = numberPackage;

        //Инициализация брони
        if (armor == null){
            TextureHandler[] armorAnimation = TextureManager.getAnimation("a_default");
            armor = new EnemyArmor(x, y, direction, armorAnimation, this);
            Global.room.objAdd(armor);
            setColorArmor(color);
        }

        //Инициализация пушки
        if (gun == null){
            TextureHandler gunTexture = TextureManager.getTexture("g_default");
            gun = new Obj(x, y, directionGun, gunTexture);
            gun.movement = new Movement(gun);
            gun.movement.directionDrawEquals = false;
            Global.room.objAdd(gun);
            setColorGun(color);
        }

        //Инициализация камеры
        if (camera == null){
            initCamera();
        }

        armor.position.x = x;
        armor.position.y = y;
        armor.position.setDirectionDraw(direction);

        followToArmor(gun);
        gun.position.setDirectionDraw(directionGun);

        //Для верных координат источника звука при взрыве
        followToArmor(this);

        //Для интерполяции (предсказания) движения врага
        armor.movement.speed = speed;
        armor.movement.setDirection(moveDirection);

        //Анимация гусениц
        if (alive) {
            Animation animation = (Animation) armor.rendering;
            if (animation.getFrameSpeed() != animSpeed) animation.setFrameSpeed(animSpeed);
        }
    }

    public void newArmor(String nameArmor){
        armor.rendering = new Animation(armor, TextureManager.getAnimation(nameArmor));
        setColorArmor(color);

        Global.room.mapControl.update(armor);
    }

    public void newGun(String nameGun){
        gun.rendering = new Sprite(gun, TextureManager.getTexture(nameGun));
        setColorGun(color);

        Global.room.mapControl.update(gun);
    }
}
