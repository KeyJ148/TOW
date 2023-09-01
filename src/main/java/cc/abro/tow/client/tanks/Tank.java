package cc.abro.tow.client.tanks;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.PositionableComponent;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.settings.GameSettingsService;
import cc.abro.tow.client.tanks.components.AnimationOnMovementComponent;
import cc.abro.tow.client.tanks.components.TankNicknameComponent;
import cc.abro.tow.client.tanks.components.TankStatsComponent;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import lombok.Getter;

public abstract class Tank extends GameObject {

    private static final Color EXPLODED_TANK_COLOR = new Color(110, 15, 0);

    private final AudioService audioService;
    private final GameSettingsService gameSettingsService;

    private final PositionableComponent cameraComponent;
    private final TankNicknameComponent tankNicknameComponent;
    @Getter
    private final Movement movementComponent;
    @Getter
    private final TankStatsComponent tankStatsComponent;
    @Getter
    private AnimationOnMovementComponent armorAnimationComponent;
    @Getter
    private SpriteRender gunSpriteComponent;
    @Getter
    private ArmorComponent armorComponent;
    @Getter
    private GunComponent gunComponent;

    @Getter
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

        cameraComponent = new PositionableComponent();
        addComponent(cameraComponent);

        tankNicknameComponent = new TankNicknameComponent();
        addComponent(tankNicknameComponent);

        movementComponent = new Movement();
        addComponent(movementComponent);

        this.armorComponent = armorComponent;
        addComponent(armorComponent);

        armorAnimationComponent = new AnimationOnMovementComponent(armorComponent.getAnimation().textures(), 1000,
                armorComponent.getAnimationSpeedCoefficient()); //TODO вынести глубины в константы
        addComponent(armorAnimationComponent);

        this.gunComponent = gunComponent;
        addComponent(gunComponent);

        gunSpriteComponent = new SpriteRender(gunComponent.getSprite().texture(), 2000); //TODO вынести глубины в константы
        addComponent(gunSpriteComponent);

        tankStatsComponent = new TankStatsComponent();
        addComponent(tankNicknameComponent);

        //TODO other components ...

        changeArmor(armorComponent);
    }

    public void exploded() {
        alive = false;

        tankNicknameComponent.destroy();
        movementComponent.destroy();
        armorAnimationComponent.setFrameSpeed(0);

        setColor(EXPLODED_TANK_COLOR);

        Explosion explosionParticlesComponent = new Explosion(100);
        explosionParticlesComponent.activate();
        addComponent(explosionParticlesComponent);

        audioService.playSoundEffect(getAudioStorage().getAudio("explosion"), (int) getX(), (int) getY(),
                gameSettingsService.getGameSettings().getSoundRange());
    }

    public void changeArmor(ArmorComponent newArmorComponent) {
        double lastMaxHp = tankStatsComponent.getStats().hpMax;
        tankStatsComponent.removeEffect(armorComponent.getEffect());

        armorComponent.destroy();
        removeComponent(armorComponent);
        armorAnimationComponent.destroy();
        removeComponent(armorAnimationComponent);

        armorComponent = newArmorComponent;
        addComponent(armorComponent);
        armorAnimationComponent = new AnimationOnMovementComponent(newArmorComponent.getAnimation().textures(),
                1000, newArmorComponent.getAnimationSpeedCoefficient()); //TODO вынести глубины в константы
        armorAnimationComponent.setColor(color);
        addComponent(armorAnimationComponent);

        tankStatsComponent.addEffect(armorComponent.getEffect());

        //Устанавливаем эквивалентное здоровье в процентах
        double lastCurrentHp = tankStatsComponent.getCurrentHp();
        double newMaxHp = tankStatsComponent.getStats().hpMax;
        double newCurrentHp = lastMaxHp != 0 ?
                lastCurrentHp / lastMaxHp * newMaxHp :
                newMaxHp;
        tankStatsComponent.setCurrentHp(newCurrentHp);
    }

    public void changeGun(GunComponent newGunComponent) {
        tankStatsComponent.removeEffect(gunComponent.getEffect());

        gunComponent.destroy();
        removeComponent(gunComponent);
        gunSpriteComponent.destroy();
        removeComponent(gunSpriteComponent);

        gunComponent = newGunComponent;
        addComponent(armorComponent);
        gunSpriteComponent = new SpriteRender(newGunComponent.getSprite().texture(), 2000); //TODO вынести глубины в константы
        gunSpriteComponent.setColor(color);
        addComponent(gunSpriteComponent);

        tankStatsComponent.addEffect(gunComponent.getEffect());
        gunSpriteComponent.setColor(color);
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
