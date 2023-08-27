package cc.abro.tow.client.tanks;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.Constants;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.services.BattleStatisticService;
import cc.abro.tow.client.settings.GameSettingsService;
import cc.abro.tow.client.tanks.components.AnimationOnMovementComponent;
import cc.abro.tow.client.tanks.components.TankNicknameComponent;
import cc.abro.tow.client.tanks.components.TankStatsComponent;
import cc.abro.tow.client.tanks.components.TankVampireComponent;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Tank extends GameObject {

    private static final Color EXPLODED_TANK_COLOR = new Color(110, 15, 0);

    private final AudioService audioService;
    private final GameSettingsService gameSettingsService;

    private final PositionableComponent<GameObject> cameraComponent;
    private final TankNicknameComponent tankNicknameComponent;
    @Getter
    private final TankVampireComponent tankVampireComponent;
    @Getter
    private final Movement <GameObject>movementComponent;
    @Getter
    private final TankStatsComponent tankStatsComponent;
    @Getter
    private AnimationOnMovementComponent armorAnimationComponent;
    @Getter
    private SpriteRender<GameObject> gunSpriteComponent;
    @Getter
    private ArmorComponent armorComponent;
    @Getter
    private GunComponent gunComponent;

    @Getter @Setter(AccessLevel.PROTECTED)
    private boolean alive = true;
    @Getter
    private Color color = Color.WHITE;

    public Tank(Location location, double x, double y, double direction,
                ArmorComponent armorComponent, GunComponent gunComponent) {
        super(location);
        audioService = getAudioService();
        gameSettingsService = Context.getService(GameSettingsService.class);

        setX(x);
        setY(y);
        setDirection(direction);

        cameraComponent = new PositionableComponent<>();
        addComponent(cameraComponent);

        tankNicknameComponent = new TankNicknameComponent();
        addComponent(tankNicknameComponent);

        tankVampireComponent = new TankVampireComponent();
        addComponent(tankVampireComponent);

        movementComponent = new Movement<>();
        addComponent(movementComponent);

        armorAnimationComponent = new AnimationOnMovementComponent(armorComponent.getAnimation().textures(), Constants.armorAnimationComponentZ,
                armorComponent.getAnimationSpeedCoefficient());
        addComponent(armorAnimationComponent);

        gunSpriteComponent = new SpriteRender<>(gunComponent.getSprite().texture(), Constants.gunSpriteComponentZ);
        addComponent(gunSpriteComponent);

        tankStatsComponent = new TankStatsComponent();
        addComponent(tankStatsComponent);

        this.armorComponent = armorComponent;
        addComponent(armorComponent);
        tankStatsComponent.addEffect(armorComponent.getEffect());
        tankStatsComponent.setCurrentHp(tankStatsComponent.getStats().getHpMax());

        this.gunComponent = gunComponent;
        addComponent(gunComponent);
        tankStatsComponent.addEffect(gunComponent.getEffect());
    }

    public void exploded() {
        setAlive(false);

        tankNicknameComponent.destroy();
        removeComponent(tankNicknameComponent);
        movementComponent.destroy();
        removeComponent(movementComponent);
        armorAnimationComponent.setFrameSpeed(0);

        setColor(EXPLODED_TANK_COLOR);

        Explosion explosionParticlesComponent = new Explosion(100);
        explosionParticlesComponent.activate();
        addComponent(explosionParticlesComponent);

        audioService.playSoundEffect(getAudioStorage().getAudio("explosion"), (int) getX(), (int) getY(),
                gameSettingsService.getGameSettings().getSoundRange());

        //TODO Этому здесь не место. Вынести в сетевой компонент или типа того. Снова было бы удобно использовать шину ивентов.
        ClientData clientData = Context.getService(ClientData.class);
        if (clientData.lastDamageDealerEnemyId != -1) {
            Context.getService(TCPControl.class).send(23, String.valueOf(clientData.lastDamageDealerEnemyId));
            Context.getService(BattleStatisticService.class).getEnemyStatistic(clientData.lastDamageDealerEnemyId)
                    .incrementKill();
        }
        Context.getService(TCPControl.class).send(12, "");
    }

    public void changeArmor(ArmorComponent newArmorComponent) {
        double lastMaxHp = tankStatsComponent.getStats().getHpMax();
        double lastSpeedUp = tankStatsComponent.getStats().getSpeedUp();
        double lastSpeedDown = tankStatsComponent.getStats().getSpeedDown();
        tankStatsComponent.removeEffect(armorComponent.getEffect());

        armorComponent.destroy();
        removeComponent(armorComponent);
        armorAnimationComponent.destroy();
        removeComponent(armorAnimationComponent);

        armorComponent = newArmorComponent;
        addComponent(armorComponent);
        armorAnimationComponent = new AnimationOnMovementComponent(newArmorComponent.getAnimation().textures(),
                Constants.armorAnimationComponentZ, newArmorComponent.getAnimationSpeedCoefficient());
        armorAnimationComponent.setColor(color);
        addComponent(armorAnimationComponent);

        tankStatsComponent.addEffect(armorComponent.getEffect());

        //Устанавливаем эквивалентное здоровье в процентах
        double lastCurrentHp = tankStatsComponent.getCurrentHp();
        double newMaxHp = tankStatsComponent.getStats().getHpMax();
        double newCurrentHp = lastMaxHp != 0 ?
                lastCurrentHp / lastMaxHp * newMaxHp :
                newMaxHp;
        tankStatsComponent.setCurrentHp(newCurrentHp);

        //Устанавливаем новую скорость
        if (movementComponent.getSpeed() == lastSpeedUp) {
            movementComponent.setSpeed(tankStatsComponent.getStats().getSpeedUp());
        } else if (movementComponent.getSpeed() == lastSpeedDown) {
            movementComponent.setSpeed(tankStatsComponent.getStats().getSpeedDown());
        }
    }

    public void changeGun(GunComponent newGunComponent) {
        tankStatsComponent.removeEffect(gunComponent.getEffect());

        gunComponent.destroy();
        removeComponent(gunComponent);
        gunSpriteComponent.destroy();
        removeComponent(gunSpriteComponent);

        gunComponent = newGunComponent;
        addComponent(armorComponent);
        gunSpriteComponent = new SpriteRender<>(newGunComponent.getSprite().texture(), Constants.gunSpriteComponentZ);
        gunSpriteComponent.setColor(color);
        addComponent(gunSpriteComponent);

        tankStatsComponent.addEffect(gunComponent.getEffect());
    }

    public void changeHp(double delta) {
        tankStatsComponent.setCurrentHp(tankStatsComponent.getCurrentHp() + delta);
    }

    public void setColor(Color color) {
        this.color = color;
        armorAnimationComponent.setColor(color);
        gunSpriteComponent.setColor(color);
    }

    public void setNickname(String nickname) {
        tankNicknameComponent.setNickname(nickname);
    }

    public String getNickname() {
        return tankNicknameComponent.getNickname();
    }

    public void setLocationCameraToThisObject() {
        getLocation().getCamera().setFollowObject(cameraComponent);
    }

    public boolean locationCameraIsThisObject() {
        return getLocation().getCamera().getFollowObject()
                .filter(locationCamera -> locationCamera == cameraComponent).isPresent();
    }
}
