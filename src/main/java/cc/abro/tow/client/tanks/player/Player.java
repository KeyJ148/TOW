package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.Follower;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.net.client.udp.UDPControl;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.tanks.Effect;
import cc.abro.tow.client.tanks.Stats;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.equipment.armor.ADefault;
import cc.abro.tow.client.tanks.equipment.gun.GDefault;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;

import java.util.ArrayList;

import static cc.abro.tow.client.menu.InterfaceStyles.BLACK_COLOR;

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

    public Label hpLabel;
    public Label[] statsLabel;
    public Button[] buttonsTake = new Button[4];

    public Player(Location location, double x, double y, double direction) {
        super(location);

        controller = new PlayerController(this);

        armor = new ADefault();
        ((Armor) armor).init(this, x, y, direction, "ADefault");
        effects.add(((Armor) armor).effect);

        gun = new GDefault();
        ((Gun) gun).init(this, x, y, direction, "GDefault");
        effects.add(((Gun) gun).effect);

        bullet = new BulletFactory("BDefault", this);

        updateStats();
        hp = stats.hpMax;

        color = Context.getService(ClientData.class).color;
        setName(Context.getService(ClientData.class).name);
        setColor(color);

        addComponent(new Follower(armor));
        gun.addComponent(new Follower(armor, false)); //TODO: gun.follower дублируется в 3-х местах
        camera.addComponent(new Follower(armor));

        hpLabel = new Label();
        hpLabel.setFocusable(false);
        hpLabel.getStyle().setFontSize(30f);
        hpLabel.getStyle().setTextColor(BLACK_COLOR);
        hpLabel.setPosition(1, 10);
        Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(hpLabel);//TODO getComponent(Position.class)

        statsLabel = new Label[stats.toString().split("\n").length + 4];
        for (int i = 0; i < statsLabel.length; i++) {
            statsLabel[i] = new Label();
            statsLabel[i].setFocusable(false);
            statsLabel[i].getStyle().setFontSize(17f);
            statsLabel[i].getStyle().setTextColor(BLACK_COLOR);
            statsLabel[i].setPosition(1, 30 + i * 15);
            Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(statsLabel[i]);//TODO getComponent(Position.class)
        }

        //Создание кнопок для отключения подбора снаряжения
        buttonsTake = new Button[4];
        Background[] buttonsBackground = new Background[4];
        for (int i = 0; i < buttonsBackground.length; i++) buttonsBackground[i] = new Background();
        buttonsBackground[0].setColor(new Vector4f(0, 1, 0, 1));
        buttonsBackground[1].setColor(new Vector4f(1, 0, 0, 1));
        buttonsBackground[2].setColor(new Vector4f(0, 0, 1, 1));
        buttonsBackground[3].setColor(new Vector4f(1, 1, 1, 1));

        for (int i = 0; i < buttonsTake.length; i++) {
            buttonsTake[i] = new Button("");
            SimpleLineBorder buttonTakeBorder = new SimpleLineBorder(ColorConstants.black(), 1);
            buttonsTake[i].getStyle().setBorder(buttonTakeBorder);
            buttonsTake[i].getStyle().setBackground(buttonsBackground[i]);
            buttonsTake[i].setSize(15, 15);
            buttonsTake[i].setPosition(17 * i, Context.getService(Render.class).getHeight() - 15);
            Context.getService(LocationManager.class).getActiveLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(buttonsTake[i]);
            //TODO getComponent(Position.class) вместо Manager.getService(LocationManager.class).getActiveLocation(), но это конструктор
        }
    }


    @Override
    public void update(long delta) {
        super.update(delta);
        if (!alive) return;

        //Обновление параметров
        updateStats();

        //Обновление вампирского сета
        vampire -= GameSetting.VAMPIRE_DOWN_FROM_SEC * ((double) delta / 1000000000);
        if (vampire < 0.0) vampire = 0.0;

        //Отрисовка HP
        hpLabel.getTextState().setText("HP: " + Math.round(hp) + "/" + Math.round(stats.hpMax));

        //Отрисовка статов
        if (Context.getService(ClientData.class).printStats) {
            String[] array = stats.toString().split("\n");
            for (int i = 0; i < array.length; i++) {
                statsLabel[i].getTextState().setText(array[i]);
            }
            statsLabel[array.length].getTextState().setText("Armor: " + ((Armor) armor).title);
            statsLabel[array.length + 1].getTextState().setText("Gun: " + ((Gun) gun).title);
            statsLabel[array.length + 2].getTextState().setText("Bullet: " + bullet.title);
            statsLabel[array.length + 3].getTextState().setText("Vampire: " + Math.round(vampire * 100) + "%");
        } else {
            for (int i = 0; i < statsLabel.length; i++) {
                statsLabel[i].getTextState().setText("");
            }
        }

        //Отрисовка возможностей подбора


        //Проверка HP
        if (hp <= 0) {
            if (lastDamagerEnemyId != -1) {
                Context.getService(TCPControl.class).send(23, String.valueOf(lastDamagerEnemyId));
                Context.getService(ClientData.class).enemy.get(lastDamagerEnemyId).kill++;
            }

            Context.getService(TCPControl.class).send(12, "");
            exploded();
        } else {
            if ((hp + delta / Math.pow(10, 9) * stats.hpRegen) > stats.hpMax) {
                hp = stats.hpMax;
            } else {
                hp += delta / Math.pow(10, 9) * stats.hpRegen;
            }
        }

        //Отправка данных о игроке
        sendDataLast += delta;
        if (Context.getService(ClientData.class).battle && sendDataLast >= Math.pow(10, 9) / GameSetting.MPS) {
            sendDataLast -= Math.pow(10, 9) / GameSetting.MPS;
            Context.getService(UDPControl.class).send(2, getData());
        }
    }

    @Override
    public void exploded() {
        super.exploded();

        controller.runUp = false;
        controller.runDown = false;
        controller.turnRight = false;
        controller.turnLeft = false;
    }

    @Override
    public void replaceArmor(GameObject newArmor) {
        double lastMaxHp = stats.hpMax;
        effects.remove(((Armor) armor).effect);

        super.replaceArmor(newArmor);
        effects.add(((Armor) newArmor).effect);
        updateStats();

        //Устанавливаем новой броне параметры как у текущий брони игрока
        hp = (hp / lastMaxHp) * stats.hpMax; //Устанавливаем эквивалетное здоровье в процентах
        if (controller.runUp) newArmor.getComponent(Movement.class).speed = stats.speedTankUp;
        if (controller.runDown) newArmor.getComponent(Movement.class).speed = stats.speedTankDown;

        //Отправляем сообщение о том, что мы сменили броню
        String newName = ((Armor) armor).imageName;
        Context.getService(TCPControl.class).send(19, newName);
    }

    @Override
    public void replaceGun(GameObject newGun) {
        effects.remove(((Gun) gun).effect);

        super.replaceGun(newGun);
        effects.add(((Gun) newGun).effect);
        updateStats();

        //Отправляем сообщение о том, что мы сменили оружие
        Context.getService(TCPControl.class).send(20, ((Gun) newGun).imageName);
    }

    //Игрок попал по врагу и нанес damage урона
    public void hitting(double damage) {
        vampire += damage * GameSetting.VAMPIRE_UP_FROM_ONE_DAMAGE;
        if (vampire > 1.0) vampire = 1.0;
    }

    public void replaceBullet(String newBullet) {
        bullet = new BulletFactory(newBullet, this);
    }

    public String getData() {

        return Math.round(armor.getX())
                + " " + Math.round(armor.getY())
                + " " + Math.round(armor.getDirection())
                + " " + Math.round(gun.getDirection())
                + " " + Math.round(armor.getComponent(Movement.class).speed)
                + " " + armor.getComponent(Movement.class).getDirection()
                + " " + ((AnimationRender) armor.getComponent(Rendering.class)).getFrameSpeed()
                + " " + Player.numberPackage++;
    }

    private void updateStats() {
        stats = new Stats();

        for (Effect effect : effects) {
            effect.calcAddStats(stats);
        }

        for (Effect effect : effects) {
            effect.calcMultiStats(stats);
        }
    }
}
