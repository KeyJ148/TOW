package game.client.tanks.player;

import engine.Global;
import engine.inf.title.Title;
import engine.obj.Obj;
import engine.obj.components.Position;
import engine.obj.components.render.Animation;
import game.client.ClientData;
import game.client.tanks.Effect;
import game.client.tanks.Stats;
import game.client.tanks.Tank;
import game.client.tanks.equipment.armor.ADefault;
import game.client.tanks.equipment.bullet.BVampire;
import game.client.tanks.equipment.gun.GDefault;
import org.newdawn.slick.Color;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Tank {

    private static final int SEND_DATA_EVERY_TICKS = 1;//Отправлять данные каждые N степов
    private int sendDataLastTicks = 0;//Как давно отправляли данные

    public boolean takeArmor = true;
    public boolean takeGun = true;
    public boolean takeBullet = true;
    public boolean takeHealth = true;

    public double hp;
    public int lastDamagerEnemyId = -1;
    
    public ArrayList<Effect> effects = new ArrayList<>();
    public Stats stats;

    public PlayerController controller;
    public String bullet;

    public Player(double x, double y, double direction){
        position = new Position(this, x, y, 0);

        controller = new PlayerController(this);
        Global.room.objAdd(controller);

        armor = new ADefault();
        ((Armor) armor).init(this, x, y, direction, "ADefault");
        effects.add(((Armor) armor).effect);
        Global.room.objAdd(armor);

        gun = new GDefault();
        ((Gun) gun).init(this, x, y, direction, "GDefault");
        effects.add(((Gun) gun).effect);
        Global.room.objAdd(gun);

        bullet = "BDefault";

        updateStats();
        hp = stats.hpMax;

        color = ClientData.color;
        name = ClientData.name;
        setColor(color);
    }


    @Override
    public void update(long delta){
        super.update(delta);
        if (!alive) return;

        //Обновление параметров
        updateStats();

        //Обновление вампирского патрона
        BVampire.updateVampire(delta);

        //Отрисовка HP
        Global.engine.render.addTitle(new Title(1, -3, "HP: " +  Math.round(hp) + "/" + Math.round(stats.hpMax), Color.black, 20, Font.BOLD));

        //Отрисовка статов
        if (ClientData.printStats){
            String[] array = stats.toString().split("\n");
            for (int i = 0; i < array.length; i++) {
                Global.engine.render.addTitle(new Title(1, 22+i*15, array[i], Color.black, 14, Font.PLAIN));
            }
            Global.engine.render.addTitle(new Title(1, 22+array.length*15+7, "Armor: " + ((Armor) armor).name, Color.black, 14, Font.PLAIN));
            Global.engine.render.addTitle(new Title(1, 22+array.length*15+7+15, "Gun: " + ((Gun) gun).name, Color.black, 14, Font.PLAIN));
            Global.engine.render.addTitle(new Title(1, 22+array.length*15+7+30, "Bullet: " + bullet, Color.black, 14, Font.PLAIN));
        }

        //Проверка HP
        if(hp <= 0){
            if (lastDamagerEnemyId != -1){
                Global.tcpControl.send(23, String.valueOf(lastDamagerEnemyId));
                ClientData.enemy.get(lastDamagerEnemyId).kill++;
            }

            Global.tcpControl.send(12, "");
            exploded();
        } else {
            if ((hp+delta/Math.pow(10, 9)*stats.hpRegen) > stats.hpMax){
                hp = stats.hpMax;
            } else {
                hp += delta/Math.pow(10, 9)*stats.hpRegen;
            }
        }

        //Отправка данных о игроке
        sendDataLastTicks++;
        if (ClientData.battle && sendDataLastTicks >= SEND_DATA_EVERY_TICKS){
            sendDataLastTicks = 0;
            Global.tcpControl.send(2, getData());
        }
    }

    @Override
    public void exploded(){
        super.exploded();

        controller.runUp = false;
        controller.runDown = false;
        controller.turnRight = false;
        controller.turnLeft = false;
    }

    @Override
    public void replaceArmor(Obj newArmor){
        double lastMaxHp = stats.hpMax;
        effects.remove(((Armor) armor).effect);

        super.replaceArmor(newArmor);
        effects.add(((Armor) newArmor).effect);
        updateStats();

        //Устанавливаем новой броне параметры как у текущий брони игрока
        hp = (hp/lastMaxHp) * stats.hpMax; //Устанавливаем эквивалетное здоровье в процентах
        if (controller.runUp) newArmor.movement.speed = stats.speedTankUp;
        if (controller.runDown) newArmor.movement.speed = stats.speedTankDown;

        //Отправляем сообщение о том, что мы сменили броню
        String newName = ((Armor) armor).textureHandlers[0].name;
        Global.tcpControl.send(19, newName.substring(0, newName.lastIndexOf("_")));
    }

    @Override
    public void replaceGun(Obj newGun){
        effects.remove(((Gun) gun).effect);

        super.replaceGun(newGun);
        effects.add(((Gun) newGun).effect);
        updateStats();

        //Отправляем сообщение о том, что мы сменили оружие
        Global.tcpControl.send(20, ((Gun) newGun).texture.name);
    }

    public void replaceBullet(String newBullet){
        bullet = newBullet;
        if (newBullet.equals(BVampire.nameVampire)) BVampire.setThisBullet();
    }


    public String getData(){

        return  Math.round(armor.position.x)
                + " " + Math.round(armor.position.y)
                + " " + Math.round(armor.position.getDirectionDraw())
                + " " + Math.round(gun.position.getDirectionDraw())
                + " " + Math.round(armor.movement.speed)
                + " " + armor.movement.getDirection()
                + " " + ((Animation) armor.rendering).getFrameSpeed();
    }
    
    private void updateStats(){
        stats = new Stats();

        for (Effect effect : effects) {
            effect.calcAddStats(stats);
        }

        for (Effect effect : effects) {
            effect.calcMultiStats(stats);
        }
    }
}
