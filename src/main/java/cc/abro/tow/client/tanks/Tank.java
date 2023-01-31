package cc.abro.tow.client.tanks;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.Follower;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.particles.Particles;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.location.Location;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.orchengine.resources.audios.AudioStorage;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.tanks.enemy.Enemy;
import org.liquidengine.legui.component.Label;

import java.util.Arrays;
import java.util.Map;

public abstract class Tank extends GameObject {

    public static final Color explodedTankColor = new Color(110, 15, 0);
    private final LocationManager locationManager;

    public GameObject armor;
    public GameObject gun;
    public GameObject camera;
    public Label nickname;

    protected String name = "";
    public Color color = Color.WHITE;
    public boolean alive = true;

    public int kill = 0;
    public int death = 0;
    public int win = 0;

    public Tank(Location location) {
        super(location, Arrays.asList(new Position(0, 0, 0)));
        locationManager = Context.getService(LocationManager.class);
        initCamera();
    }

    public void initCamera() {
        //Инициализация камеры
        camera = GameObjectFactory.create(getLocation(), 0, 0, 0);

        nickname = new Label();
        nickname.setSize(500, 30);
        locationManager.getActiveLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(nickname);//TODO Position.location

        nickname.setFocusable(false); //Иначе событие мыши перехватывает надпись, и оно не поступает в игру
        nickname.getTextState().setText(name);
    }

    @Override
    public void update(long delta) {
        super.update(delta);

        if (!alive) return;

        if (armor != null && armor.hasComponent(Position.class)) {
            double x = armor.getComponent(Position.class).x - name.length() * 3.45;
            double y = armor.getComponent(Position.class).y - 55;
            Vector2<Integer> relativePosition = locationManager.getActiveLocation().getCamera() //TODO не с активной, а из position
                    .toRelativePosition(new Vector2<>((int) x, (int) y));
            nickname.setPosition(relativePosition.x, relativePosition.y);
        }
    }

    public void exploded() {
        alive = false;
        death++;

        if (armor != null) {
            armor.getComponent(Movement.class).speed = 0;
            ((AnimationRender) armor.getComponent(Rendering.class)).setFrameSpeed(0);

            setColor(explodedTankColor);

            GameObject explosion = GameObjectFactory.create(getLocation(), armor.getComponent(Position.class).x, armor.getComponent(Position.class).y, 3000);
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
                if (entry.getValue().camera != null && entry.getValue().alive) {
                    locationManager.getActiveLocation().getCamera().setFollowObject(entry.getValue().camera);
                    break;
                }
            }
        }

        locationManager.getActiveLocation().getGuiLocationFrame().getGuiFrame().getContainer().remove(nickname);
        Context.getService(AudioService.class).playSoundEffect(Context.getService(AudioStorage.class).getAudio("explosion"), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
    }

    public void replaceArmor(GameObject newArmor) {
        //Устанавливаем новой броне параметры как у текущий брони танка
        newArmor.getComponent(Movement.class).setDirection(armor.getComponent(Movement.class).getDirection());

        armor.destroy();
        armor = newArmor;

        setColorArmor(color);
        camera.addComponent(new Follower(armor));
    }

    public void replaceGun(GameObject newGun) {
        newGun.getComponent(Position.class).setDirectionDraw(gun.getComponent(Position.class).getDirectionDraw());

        gun.destroy();
        gun = newGun;
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

    public void setName(String name) {
        this.name = name;
        nickname.getTextState().setText(name);
    }

    public String getName() {
        return name;
    }
}
