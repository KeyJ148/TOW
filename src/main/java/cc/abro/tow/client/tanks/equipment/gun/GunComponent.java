package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.settings.GameSettingsService;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.equipment.bullet.Bullet;
import cc.abro.tow.client.tanks.equipment.bulletbehaviors.BulletBehavior;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class GunComponent extends Component<Tank> implements Updatable {
    @Getter
    private final String name; //Имя файла и техническое имя брони
    @Getter
    private final String title; //Название, отображаемое в игре
    @Getter
    private final Effect effect;
    @Getter
    private final Sprite sprite;
    @Getter
    private final String soundShot;
    @Getter
    private final List<TrunkInfo> trunksInfo;
    @Getter
    private final Map<String, BulletInfo> bulletMapping;
    @Getter
    private final int size;

    @Getter
    private long nanoSecToAttack = 0; //Кол-во времени до конца перезарядки в наносекундах

    @Override
    public void update(long delta) {
        //Уменьшаем время до выстрела
        nanoSecToAttack -= delta;
    }

    public void tryAttack() {
        if (nanoSecToAttack > 0 || getGameObject().getTankStatsComponent().getStats().getAttackSpeed() <= 0){
            return;
        }

        double attackSpeed = getGameObject().getTankStatsComponent().getStats().getAttackSpeed();
        nanoSecToAttack = (long) (Math.pow(10, 9) / attackSpeed); //Устанавливаем время перезарядки

        //По очереди стреляем из всех стволов
        for (TrunkInfo trunk : trunksInfo) {
            attackFromTrunk(trunk.bulletStartX, trunk.bulletStartY, trunk.bulletStartDir);
        }
    }

    private void attackFromTrunk(int trunkX, int trunkY, double bulletStartDir) {
        double gunDirection = Math.toRadians(getGameObject().getGunSpriteComponent().getDirection());
        double trunkXdx = trunkX * Math.cos(gunDirection - Math.PI / 2); //первый отступ "вперед"
        double trunkXdy = trunkX * Math.sin(gunDirection - Math.PI / 2); //в отличие от маски мы отнимаем от каждого по PI/2
        double trunkYdx = trunkY * Math.cos(gunDirection - Math.PI); //потому что изначально у теустуры измененное направление
        double trunkYdy = trunkY * Math.sin(gunDirection - Math.PI); //второй отступ "вбок"

        double bulletX = getGameObject().getX() + trunkXdx + trunkYdx;
        double bulletY = getGameObject().getY() - trunkXdy - trunkYdy;
        double bulletDirection = getGameObject().getGunSpriteComponent().getDirection() + bulletStartDir;

        Stats stats = getGameObject().getTankStatsComponent().getStats();
        BulletInfo bulletInfo = bulletMapping.get(getGameObject().getBulletComponent().getName());
        new Bullet(getGameObject().getLocation(), bulletX, bulletY, bulletDirection,
                bulletInfo.behavior, bulletInfo.spriteName, bulletInfo.soundHit,
                stats.getBulletSpeed(), stats.getRange(), stats.getDamage(), stats.getBulletExplosionPower());

        getAudioService().playSoundEffect(getAudioStorage().getAudio(soundShot), (int) bulletX, (int) bulletY,
                Context.getService(GameSettingsService.class).getGameSettings().getSoundRange()); //TODO задачу на создание прослойки с getSoundRange(). Или просто весь AL переделать. Или выписать в константы soundRange, т.к. после переделки AL это будет soundPower и он будет уникальный для каждого события
        Context.getService(TCPControl.class).send(25, (int) bulletX + " " + (int) bulletY + " " + soundShot);
    }

    public record TrunkInfo(int bulletStartX, int bulletStartY, double bulletStartDir) {}
    public record BulletInfo(BulletBehavior behavior, String spriteName, String soundHit) {}
}
