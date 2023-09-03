package cc.abro.tow.client.tanks.player0;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.tow.client.ClientData;
import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.style.Background;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import org.joml.Vector4f;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.BLACK_COLOR;

public class Player {

    public boolean takeArmor = true;
    public boolean takeGun = true;
    public boolean takeBullet = true;
    public boolean takeHealth = true;

    public PlayerController controller;
    public BulletFactory bullet;

    public int lastDamagerEnemyId = -1;

    //TODO это всё тоже в отдельный класс, связанный с GUI
    public Label hpLabel;
    public Label[] statsLabel;
    public Button[] buttonsTake = new Button[4];

    public Player(Location location, double x, double y, double direction) {
        controller = new PlayerController(this);
        bullet = new BulletFactory("BDefault", this);

        color = Context.getService(ClientData.class).color;
        setNickname(Context.getService(ClientData.class).name);
        setColor(color);

        hpLabel = new Label();
        hpLabel.setFocusable(false);
        hpLabel.getStyle().setFontSize(30f);
        hpLabel.getStyle().setTextColor(BLACK_COLOR);
        hpLabel.setPosition(1, 10);
        getLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(hpLabel);

        statsLabel = new Label[stats.toString().split("\n").length + 4];
        for (int i = 0; i < statsLabel.length; i++) {
            statsLabel[i] = new Label();
            statsLabel[i].setFocusable(false);
            statsLabel[i].getStyle().setFontSize(17f);
            statsLabel[i].getStyle().setTextColor(BLACK_COLOR);
            statsLabel[i].setPosition(1, 30 + i * 15);
            getLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(statsLabel[i]);
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
            buttonsTake[i].setPosition(17 * i, getRender().getHeight() - 15);
            getLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(buttonsTake[i]);
        }
    }


    @Override
    public void update(long delta) {
        super.update(delta);
        if (!alive) return;

        //Отрисовка HP
        hpLabel.getTextState().setText("HP: " + Math.round(hp) + "/" + Math.round(stats.hpMax));

        //Отрисовка статов
        if (Context.getService(ClientData.class).printStats) {
            String[] array = stats.toString().split("\n");
            for (int i = 0; i < array.length; i++) {
                statsLabel[i].getTextState().setText(array[i]);
            }
            statsLabel[array.length].getTextState().setText("Armor: " + ((ArmorLoader) armor).title);
            statsLabel[array.length + 1].getTextState().setText("Gun: " + ((GunLoader) gun).title);
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
        if (controller.runUp) newArmor.getComponent(Movement.class).speed = stats.speedTankUp;
        if (controller.runDown) newArmor.getComponent(Movement.class).speed = stats.speedTankDown;
    }
}
