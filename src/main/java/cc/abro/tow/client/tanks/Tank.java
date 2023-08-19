package cc.abro.tow.client.tanks;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.particles.Particles;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.util.GameObjectFactory;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.tanks.components.TankNicknameComponent;
import cc.abro.tow.client.tanks.enemy.Enemy;
import lombok.Getter;

import java.util.Map;

public abstract class Tank extends GameObject {

    private static final Color EXPLODED_TANK_COLOR = new Color(110, 15, 0);

    private final LocationManager locationManager;

    private final PositionableComponent camera;
    private final TankNicknameComponent tankNicknameComponent;

    public GameObject armor;
    public GameObject gun;

    @Getter
    protected Color color = Color.WHITE;
    public boolean alive = true;

    public int kill = 0;
    public int death = 0;
    public int win = 0;

    public Tank(Location location) {
        super(location);
        locationManager = getLocationManager();

        camera = new PositionableComponent();
        addComponent(camera);

        tankNicknameComponent = new TankNicknameComponent();
        addComponent(tankNicknameComponent);
    }

    public void exploded() {
        alive = false;
        death++;

        tankNicknameComponent.destroy();

        if (armor != null) {
            armor.getComponent(Movement.class).speed = 0;
            ((AnimationRender) armor.getComponent(Rendering.class)).setFrameSpeed(0);

            setColor(EXPLODED_TANK_COLOR);

            GameObject explosion = GameObjectFactory.create(getLocation(), armor.getX(), armor.getY());
            Explosion explosionParticles = new Explosion(100);
            explosion.addComponent(explosionParticles);
            explosionParticles.activate();
            explosion.getComponent(Particles.class).destroyObject = true;
        }

        //Если в данный момент камера установлена на этот объект
        if (locationManager.getActiveLocation().getCamera().hasFollowObject() &&
                locationManager.getActiveLocation().getCamera().getFollowObject().orElse(null) == camera) {
            //Выбираем живого врага с инициализированной камерой, переносим камеру туда
            for (Map.Entry<Integer, Enemy> entry : Context.getService(ClientData.class).enemy.entrySet()) {
                if (entry.getValue().alive) {
                    entry.getValue().setLocationCameraToThisObject();
                    break;
                }
            }
        }

        getAudioService().playSoundEffect(getAudioStorage().getAudio("explosion"), (int) getX(), (int) getY(), GameSetting.SOUND_RANGE);
    }

    public void replaceArmor(GameObject newArmor) {
        //Устанавливаем новой броне параметры как у текущий брони танка
        newArmor.getComponent(Movement.class).setDirection(armor.getComponent(Movement.class).getDirection());

        armor.destroy();
        armor = newArmor;

        setColorArmor(color);
    }

    public void replaceGun(GameObject newGun) {
        newGun.setDirection(gun.getDirection());

        gun.destroy();
        gun = newGun;
        setColorGun(color);
    }

    public void setColor(Color c) {
        color = c;
        setColorArmor(c);
        setColorGun(c);
    }

    public void setColorArmor(Color c) {
        if (armor == null || !armor.hasComponent(Rendering.class)) return;
        armor.getComponent(Rendering.class).setColor(c);
    }

    public void setColorGun(Color c) {
        if (gun == null || !gun.hasComponent(Rendering.class)) return;
        gun.getComponent(Rendering.class).setColor(c);
    }

    public void setNickname(String nickname) {
        tankNicknameComponent.setNickname(nickname);
    }

    public String getNickname() {
        return tankNicknameComponent.getNickname();
    }

    public void setLocationCameraToThisObject() {
        getLocation().getCamera().setFollowObject(camera);
    }

    public boolean locationCameraIsThisObject() {
        return getLocation().getCamera().getFollowObject()
                .filter(locationCamera -> locationCamera == camera).isPresent();
    }
}
