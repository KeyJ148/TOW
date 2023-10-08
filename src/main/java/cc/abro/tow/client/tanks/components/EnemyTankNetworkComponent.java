package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.tanks.Tank;
import lombok.Getter;
import lombok.Setter;

public class EnemyTankNetworkComponent extends Component<Tank> implements Updatable {

    private static final long REQUEST_DATA_EVERY_TIME = (long) (0.5 * Math.pow(10, 9));//Промежуток времени между запросами информации о враге
    @Getter @Setter
    private boolean valid = false; //Этот враг уже инициализирован? (Отправл свои данные: цвет, ник)
    @Getter
    private int id = -1;
    private long lastNumberPackage = -1; //Номер последнего пакета UDP
    private long timeLastRequestDelta = 0;

    public EnemyTankNetworkComponent(int id) {
        this.id = id;
    }

    @Override
    public void update(long delta) {
        if (!valid) {
            timeLastRequestDelta -= delta;
            if (timeLastRequestDelta <= 0) {
                timeLastRequestDelta = REQUEST_DATA_EVERY_TIME;
                Context.getService(TCPControl.class).send(16, String.valueOf(id));
            }
        }
    }

    public void setData(int x, int y, int direction, int directionGun, double speed, double moveDirection, long numberPackage) {
        if (!Context.getService(ClientData.class).battle) return;
        if (numberPackage < lastNumberPackage) return;
        lastNumberPackage = numberPackage;

        getGameObject().setX(x);
        getGameObject().setY(y);
        getGameObject().setDirection(direction);
        getGameObject().getGunSpriteComponent().setDirection(directionGun);

        //Для экстраполяции движения врага
        getGameObject().getMovementComponent().setSpeed(speed);
        getGameObject().getMovementComponent().setDirection(moveDirection);
    }
}
