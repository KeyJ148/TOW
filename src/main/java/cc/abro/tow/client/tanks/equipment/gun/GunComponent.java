package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.stats.Effect;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class GunComponent extends Component<Tank> implements Updatable {
    @Getter
    private final String name; //Имя файла и техническое имя брони
    @Getter
    private final String title; //Название, отображаемое в игре
    @Getter
    private final Effect effect;
    @Getter
    private final Sprite sprite; //TODO переделать gun и armor на использование Render компонента, без привязки спрайт/текстура
    @Getter
    private final List<TrunkInfo> trunksInfo;
    @Getter
    private long nanoSecToAttack = 0;//Кол-во времени до конца перезарядки в наносекундах

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

        //TODO создание пули
        /*
        Bullet newBullet = player.bullet.create();
        newBullet.init(
                player,
                getX() + trunkXdx + trunkYdx,
                getY() - trunkXdy - trunkYdy,
                getDirection() + bulletStartDir,
                player.stats.damage,
                player.stats.range,
                player.bullet.name
        );
        */
    }

    public record TrunkInfo(int bulletStartX, int bulletStartY, double bulletStartDir) {}
}
