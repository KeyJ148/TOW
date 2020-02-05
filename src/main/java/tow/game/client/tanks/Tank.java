package tow.game.client.tanks;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.obj.Obj;
import tow.engine.obj.components.render.Animation;
import tow.game.client.ClientData;
import tow.game.client.GameSetting;
import tow.game.client.particles.Explosion;
import tow.game.client.tanks.enemy.Enemy;
import tow.engine.image.Color;

import java.util.Map;

public abstract class Tank extends Obj{

    public static final Color explodedTankColor = new Color(110, 15, 0);

    public Obj armor;
    public Obj gun;
    public Obj camera;

    public String name = "";
    public Color color = Color.WHITE;
    public boolean alive = true;

    public int kill = 0;
    public int death = 0;
    public int win = 0;

    public Tank(){
        super(0, 0, 0);

        initCamera();
    }

    public void initCamera(){
        //Инициализация камеры
        camera = new Obj(0, 0, 0);
        Global.room.objAdd(camera);
    }

    @Override
    public void update(long delta){
        super.update(delta);

        if (!alive) return;

        if (armor != null && armor.position != null) {
            Vector2<Integer> relativePosition = armor.position.getRelativePosition();
            int nameX = (int) Math.round(relativePosition.x - name.length() * 3.25); // lengthChar/2
            int nameY = relativePosition.y - 50;
            //TODO: Global.engine.render.addTitle(new Title(nameX, nameY, name));
        }
    }

    public void exploded(){
        alive = false;
        death++;

        armor.movement.speed = 0;
        ((Animation) armor.rendering).setFrameSpeed(0);

        setColor(explodedTankColor);

        Obj explosion = new Obj(armor.position.x, armor.position.y, -100);
        explosion.particles = new Explosion(explosion, 100);
        explosion.particles.destroyObject = true;
        Global.room.objAdd(explosion);

        //Если в данный момент камера установлена на этот объект
        if (Global.room.camera.getFollowObject() != null && Global.room.camera.getFollowObject() == camera){
            //Выбираем живого врага с инициализированной камерой, переносим камеру туда
            for (Map.Entry<Integer, Enemy> entry: ClientData.enemy.entrySet()) {
                if (entry.getValue().camera != null && entry.getValue().alive) {
                    Global.room.camera.setFollowObject(entry.getValue().camera);
                    break;
                }
            }
        }

        Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio("explosion"), (int) position.x, (int) position.y, GameSetting.SOUND_RANGE);
    }

    public void replaceArmor(Obj newArmor){
        //Устанавливаем новой броне параметры как у текущий брони танка
        newArmor.movement.setDirection(armor.movement.getDirection());

        armor.destroy();
        armor = newArmor;
        Global.room.objAdd(newArmor);
        setColorArmor(color);
    }

    public void replaceGun(Obj newGun){
        newGun.position.setDirectionDraw(gun.position.getDirectionDraw());

        gun.destroy();
        gun = newGun;
        Global.room.objAdd(newGun);
        setColorGun(color);
    }

    public void setColor(Color c){
        setColorArmor(c);
        setColorGun(c);
    }

    public void setColorArmor(Color c){
        if (armor == null || armor.rendering == null) return;
        armor.rendering.color = c;
    }

    public void setColorGun(Color c){
        if (gun == null || gun.rendering == null) return;
        gun.rendering.color = c;
    }

    public void followToArmor(Obj obj){
        if (armor == null || armor.position == null || obj == null || obj.position == null) return;

        obj.position.x = armor.position.x;
        obj.position.y = armor.position.y;

        Global.room.mapControl.update(obj);
    }
}
