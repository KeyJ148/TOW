package cc.abro.tow.client.tanks;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Manager;
import cc.abro.orchengine.audio.AudioPlayer;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.Follower;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.gui.GuiElement;
import cc.abro.orchengine.gameobject.components.particles.Particles;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.audios.AudioStorage;
import cc.abro.orchengine.services.LeguiComponentService;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.tanks.enemy.Enemy;
import org.liquidengine.legui.component.Label;

import java.util.Arrays;
import java.util.Map;

public abstract class Tank extends GameObject {

    public static final Color explodedTankColor = new Color(110, 15, 0);

    public GameObject armor;
    public GameObject gun;
    public GameObject camera;
    public GameObject nickname;

    public String name;
    public Color color = Color.WHITE;
    public boolean alive = true;

    public int kill = 0;
    public int death = 0;
    public int win = 0;

    public Tank() {
        super(Arrays.asList(new Position(0, 0, 0)));

        name = "";
        initCamera();
    }

    public void initCamera() {
        //Инициализация камеры
        camera = GameObjectFactory.create(0, 0, 0);
        Global.location.objAdd(camera);

        nickname = Manager.getService(LeguiComponentService.class).addComponentToLocationShiftedToCenter(new Label(), 500, 30, Global.location);//TODO Position.location
        nickname.getComponent(GuiElement.class).setMoveComponentToGameObjectPosition(true);
    }

    @Override
    public void update(long delta) {
        super.update(delta);

        if (!alive) return;

        if (armor != null && armor.hasComponent(Position.class)) {
            Label label = ((Label) (nickname.getComponent(GuiElement.class)).getComponent());
            label.setFocusable(false); //Иначе событие мыши перехватывает надпись, и оно не поступает в игру
            label.getTextState().setText(name); //TODO: присваивать только один раз

            nickname.getComponent(Position.class).x = armor.getComponent(Position.class).x - name.length() * 3.45 + label.getSize().x / 2;
            nickname.getComponent(Position.class).y = armor.getComponent(Position.class).y - 50;
        }
    }

    public void exploded() {
        alive = false;
        death++;

        armor.getComponent(Movement.class).speed = 0;
        ((AnimationRender) armor.getComponent(Rendering.class)).setFrameSpeed(0);

        setColor(explodedTankColor);

        GameObject explosion = GameObjectFactory.create(armor.getComponent(Position.class).x, armor.getComponent(Position.class).y, -100);
        explosion.setComponent(new Explosion(100));
        explosion.getComponent(Particles.class).destroyObject = true;
        Global.location.objAdd(explosion);

        //Если в данный момент камера установлена на этот объект
        if (Global.location.camera.getFollowObject() != null && Global.location.camera.getFollowObject() == camera) {
            //Выбираем живого врага с инициализированной камерой, переносим камеру туда
            for (Map.Entry<Integer, Enemy> entry : ClientData.enemy.entrySet()) {
                if (entry.getValue().camera != null && entry.getValue().alive) {
                    Global.location.camera.setFollowObject(entry.getValue().camera);
                    break;
                }
            }
        }

        Manager.getService(AudioPlayer.class).playSoundEffect(Manager.getService(AudioStorage.class).getAudio("explosion"), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
    }

    public void replaceArmor(GameObject newArmor) {
        //Устанавливаем новой броне параметры как у текущий брони танка
        newArmor.getComponent(Movement.class).setDirection(armor.getComponent(Movement.class).getDirection());

        armor.destroy();
        armor = newArmor;
        Global.location.objAdd(newArmor);

        setColorArmor(color);
        camera.setComponent(new Follower(armor));
    }

    public void replaceGun(GameObject newGun) {
        newGun.getComponent(Position.class).setDirectionDraw(gun.getComponent(Position.class).getDirectionDraw());

        gun.destroy();
        gun = newGun;
        Global.location.objAdd(newGun);
        setColorGun(color);
    }

    public void setColor(Color c) {
        setColorArmor(c);
        setColorGun(c);
    }

    public void setColorArmor(Color c) {
        if (armor == null || !armor.hasComponent(Rendering.class)) return;
        armor.getComponent(Rendering.class).color = c;
    }

    public void setColorGun(Color c) {
        if (gun == null || !gun.hasComponent(Rendering.class)) return;
        gun.getComponent(Rendering.class).color = c;
    }
}
