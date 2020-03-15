package tow.game.client.tanks.player;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import tow.engine.Global;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.GameObjectFactory;
import tow.engine.gameobject.components.Follower;
import tow.engine.gameobject.components.Movement;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.Animation;
import tow.engine.gameobject.components.render.GuiRender;
import tow.engine.gameobject.components.render.Rendering;
import tow.game.client.ClientData;
import tow.game.client.GameSetting;
import tow.game.client.tanks.Effect;
import tow.game.client.tanks.Stats;
import tow.game.client.tanks.Tank;
import tow.game.client.tanks.equipment.armor.ADefault;
import tow.game.client.tanks.equipment.gun.GDefault;

import java.util.ArrayList;

public class Player extends Tank {

    public boolean takeArmor = true;
    public boolean takeGun = true;
    public boolean takeBullet = true;
    public boolean takeHealth = true;

    public double hp;

    public ArrayList<Effect> effects = new ArrayList<>();
    public Stats stats;

    public PlayerController controller;
    public BulletFactory bullet;

    public double vampire = 0.5; //Сколько набрано вампиризма в процентах (от 0 до 1)

    public int lastDamagerEnemyId = -1;
    private int sendDataLast = 0;//Как давно отправляли данные
    private static long numberPackage = 0; //Номер пакета UDP

    public GameObject hpLabel;
    public GameObject[] statsLabel;
    public GameObject[] buttonsTake = new GameObject[4];

    public Player(double x, double y, double direction){
        setComponent(new Position(x, y, 0));

        controller = new PlayerController(this);
        Global.location.objAdd(controller);

        armor = new ADefault();
        ((Armor) armor).init(this, x, y, direction, "ADefault");
        effects.add(((Armor) armor).effect);
        Global.location.objAdd(armor);

        gun = new GDefault();
        ((Gun) gun).init(this, x, y, direction, "GDefault");
        effects.add(((Gun) gun).effect);
        Global.location.objAdd(gun);

        bullet = new BulletFactory("BDefault", this);

        updateStats();
        hp = stats.hpMax;

        color = ClientData.color;
        name = ClientData.name;
        setColor(color);

        setComponent(new Follower(armor));
        gun.setComponent(new Follower(armor, false)); //TODO: gun.follower дублируется в 3-х местах
        camera.setComponent(new Follower(armor));

        hpLabel = GameObjectFactory.create(1, 10, 0);
        hpLabel.getComponent(Position.class).absolute = false;
        Global.location.objAdd(hpLabel);
        hpLabel.setComponent(new GuiRender(new Label(), 1, 1));
        ((Label) ((GuiRender) hpLabel.getComponent(Rendering.class)).getComponent()).setFocusable(false);
        ((Label) ((GuiRender) hpLabel.getComponent(Rendering.class)).getComponent()).getTextState().setFontSize(30);

        statsLabel = new GameObject[stats.toString().split("\n").length + 4];
        for (int i = 0; i < statsLabel.length; i++) {
            statsLabel[i] = GameObjectFactory.create(1, 30+i*15, 0);
            statsLabel[i].getComponent(Position.class).absolute = false;
            Global.location.objAdd(statsLabel[i]);
            statsLabel[i].setComponent(new GuiRender(new Label(), 1, 1));
            ((Label) ((GuiRender) statsLabel[i].getComponent(Rendering.class)).getComponent()).setFocusable(false);
            ((Label) ((GuiRender) statsLabel[i].getComponent(Rendering.class)).getComponent()).getTextState().setFontSize(17);
        }

        //Создание кнопок для отключения подбора снаряжения
        Button[] buttons = new Button[4];
        Background[] buttonsBackground = new Background[4];
        for (int i = 0; i < buttonsBackground.length; i++) buttonsBackground[i] = new Background();
        buttonsBackground[0].setColor(new Vector4f(0, 1, 0, 1));
        buttonsBackground[1].setColor(new Vector4f(1, 0, 0, 1));
        buttonsBackground[2].setColor(new Vector4f(0, 0, 1, 1));
        buttonsBackground[3].setColor(new Vector4f(1, 1, 1, 1));

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button("");
            SimpleLineBorder buttonTakeBorder = new SimpleLineBorder(ColorConstants.black(), 1);
            buttons[i].getStyle().setBorder(buttonTakeBorder);
            buttons[i].getStyle().setBackground(buttonsBackground[i]);

            buttonsTake[i] = GameObjectFactory.create(10+17*i, Global.engine.render.getHeight()-15, 0);
            buttonsTake[i].getComponent(Position.class).absolute = false;
            Global.location.objAdd(buttonsTake[i]);
            buttonsTake[i].setComponent(new GuiRender(buttons[i], 15, 15));
        }
    }


    @Override
    public void update(long delta){
        super.update(delta);
        if (!alive) return;

        //Обновление параметров
        updateStats();

        //Обновление вампирского сета
        vampire -= GameSetting.VAMPIRE_DOWN_FROM_SEC * ((double) delta/1000000000);
        if (vampire < 0.0) vampire = 0.0;

        //Отрисовка HP
        ((Label) ((GuiRender) hpLabel.getComponent(Rendering.class)).getComponent()).getTextState().setText("HP: " +  Math.round(hp) + "/" + Math.round(stats.hpMax));

        //Отрисовка статов
        if (ClientData.printStats){
            String[] array = stats.toString().split("\n");
            for (int i = 0; i < array.length; i++) {
                ((Label) ((GuiRender) statsLabel[i].getComponent(Rendering.class)).getComponent()).getTextState().setText(array[i]);
            }
            ((Label) ((GuiRender) statsLabel[array.length].getComponent(Rendering.class)).getComponent()).getTextState().setText("Armor: " + ((Armor) armor).title);
            ((Label) ((GuiRender) statsLabel[array.length+1].getComponent(Rendering.class)).getComponent()).getTextState().setText("Gun: " + ((Gun) gun).title);
            ((Label) ((GuiRender) statsLabel[array.length+2].getComponent(Rendering.class)).getComponent()).getTextState().setText("Bullet: " + bullet.title);
            ((Label) ((GuiRender) statsLabel[array.length+3].getComponent(Rendering.class)).getComponent()).getTextState().setText("Vampire: " + Math.round(vampire*100) + "%");
       } else {
            for (int i = 0; i < statsLabel.length; i++) {
                ((Label) ((GuiRender) statsLabel[i].getComponent(Rendering.class)).getComponent()).getTextState().setText("");
            }
        }

        //Отрисовка возможностей подбора


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
        sendDataLast += delta;
        if (ClientData.battle && sendDataLast >= Math.pow(10,9)/GameSetting.MPS){
            sendDataLast -= Math.pow(10,9)/GameSetting.MPS;
            Global.udpControl.send(2, getData());
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
    public void replaceArmor(GameObject newArmor){
        double lastMaxHp = stats.hpMax;
        effects.remove(((Armor) armor).effect);

        super.replaceArmor(newArmor);
        effects.add(((Armor) newArmor).effect);
        updateStats();

        //Устанавливаем новой броне параметры как у текущий брони игрока
        hp = (hp/lastMaxHp) * stats.hpMax; //Устанавливаем эквивалетное здоровье в процентах
        if (controller.runUp) newArmor.getComponent(Movement.class).speed = stats.speedTankUp;
        if (controller.runDown) newArmor.getComponent(Movement.class).speed = stats.speedTankDown;

        //Отправляем сообщение о том, что мы сменили броню
        String newName = ((Armor) armor).textureHandlers[0].name;
        Global.tcpControl.send(19, newName.substring(0, newName.lastIndexOf("_")));
    }

    @Override
    public void replaceGun(GameObject newGun){
        effects.remove(((Gun) gun).effect);

        super.replaceGun(newGun);
        effects.add(((Gun) newGun).effect);
        updateStats();

        //Отправляем сообщение о том, что мы сменили оружие
        Global.tcpControl.send(20, ((Gun) newGun).texture.name);
    }

    //Игрок попал по врагу и нанес damage урона
    public void hitting(double damage){
        vampire += damage*GameSetting.VAMPIRE_UP_FROM_ONE_DAMAGE;
        if (vampire > 1.0) vampire = 1.0;
    }

    public void replaceBullet(String newBullet){
        bullet = new BulletFactory(newBullet, this);
    }

    public String getData(){

        return  Math.round(armor.getComponent(Position.class).x)
                + " " + Math.round(armor.getComponent(Position.class).y)
                + " " + Math.round(armor.getComponent(Position.class).getDirectionDraw())
                + " " + Math.round(gun.getComponent(Position.class).getDirectionDraw())
                + " " + Math.round(armor.getComponent(Movement.class).speed)
                + " " + armor.getComponent(Movement.class).getDirection()
                + " " + ((Animation) armor.getComponent(Rendering.class)).getFrameSpeed()
                + " " + Player.numberPackage++;
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
