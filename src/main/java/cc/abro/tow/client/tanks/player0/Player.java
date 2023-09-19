package cc.abro.tow.client.tanks.player0;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.tanks.equipment.bullet0.BulletFactory;
import com.spinyowl.legui.component.Label;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.BLACK_COLOR;

public class Player {
    public BulletFactory bullet;

    //TODO это всё тоже в отдельный класс, связанный с GUI
    public Label hpLabel;
    public Label[] statsLabel;

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
    }
}
