package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.tanks.stats.Effect;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class GunComponent extends Component<GameObject> {
    @Getter
    private final String name; //Имя файла и техническое имя брони
    @Getter
    private final String title; //Название, отображаемое в игре
    @Getter
    private final Effect effect;
    @Getter
    private final Sprite sprite; //TODO переделать gun и armor на использование Render компонента, без привязки спрайт/текстура

    public record TrunkInfo(int bulletStartX, int bulletStartY, double bulletStartDir) {}
    @Getter
    private final List<TrunkInfo> trunksInfo;


    //TODO разобрать
    /*
    public long nanoSecFromAttack = 0;//Кол-во времени до конца перезарядки в наносекундах

    @Override
    public void update(long delta) {
        //Если мы мертвы, то ничего не делать
        if (!player.alive) return;

        //Уменьшаем время до выстрела
        nanoSecFromAttack -= delta;
    }

    public void attack() {
        nanoSecFromAttack = (long) ((double) 1 / player.stats.attackSpeed * Math.pow(10, 9)); //Устанавливаем время перезарядки

        //По очереди стреляем из всех стволов
        for (int i = 0; i < countTrunk; i++) {
            attackFromTrunk(trunksOffset[i].x, trunksOffset[i].y, directionTrunk[i]);
        }
    }

    private void attackFromTrunk(int trunkX, int trunkY, double direction) {
        double trunkXdx = trunkX * Math.cos(Math.toRadians(getDirection()) - Math.PI / 2);//первый отступ "вперед"
        double trunkXdy = trunkX * Math.sin(Math.toRadians(getDirection()) - Math.PI / 2);//в отличие от маски мы отнимаем от каждого по PI/2
        double trunkYdx = trunkY * Math.cos(Math.toRadians(getDirection()) - Math.PI);//потому что изначально у теустуры измененное направление
        double trunkYdy = trunkY * Math.sin(Math.toRadians(getDirection()) - Math.PI);//второй отступ "вбок"

        Bullet newBullet = player.bullet.create();
        newBullet.init(
                player,
                getX() + trunkXdx + trunkYdx,
                getY() - trunkXdy - trunkYdy,
                getDirection() + direction,
                player.stats.damage,
                player.stats.range,
                player.bullet.name
        );
    }*/

}
