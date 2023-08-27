package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.net.client.udp.UDPControl;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.events.TankExplodedEvent;
import cc.abro.tow.client.services.BattleStatisticService;
import cc.abro.tow.client.settings.GameSettingsService;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import com.google.common.eventbus.Subscribe;

public class PlayerTankNetworkComponent extends Component<Tank> implements Updatable {

    private final ClientData clientData;
    private final GameSettingsService gameSettingsService;
    private final int messagePerSecond;

    private long sendDataLast = 0; //Как давно отправляли данные
    private long numberPackage = 0; //Номер пакета UDP

    public PlayerTankNetworkComponent() {
        clientData = Context.getService(ClientData.class);
        gameSettingsService = Context.getService(GameSettingsService.class);
        messagePerSecond = gameSettingsService.getGameSettings().getMessagePerSecond();
    }

    @Override
    public void update(long delta) {
        //Отправка данных об игроке
        sendDataLast += delta;
        if (Context.getService(ClientData.class).battle && sendDataLast >= Math.pow(10, 9) / messagePerSecond) {
            sendDataLast = 0;
            Context.getService(UDPControl.class).send(2, getData());
        }
    }

    public String getData() {
        Tank playerTank = getGameObject();

        return (int) Math.round(playerTank.getX())
                + " " + (int) Math.round(playerTank.getY())
                + " " + (int) Math.round(playerTank.getDirection())
                + " " + (int) Math.round(playerTank.getGunSpriteComponent().getDirection())
                + " " + playerTank.getMovementComponent().getSpeed()
                + " " + playerTank.getMovementComponent().getDirection()
                + " " + playerTank.getArmorAnimationComponent().getFrameSpeed()
                + " " + numberPackage++;
    }

    @Subscribe
    public void onTankExplodedEvent(TankExplodedEvent tankExplodedEvent) {
        if (tankExplodedEvent.getTank() != getGameObject()) {
            return;
        }

        if (clientData.lastDamageDealerEnemyId != -1) {
            Context.getService(TCPControl.class).send(23, String.valueOf(clientData.lastDamageDealerEnemyId));
            Context.getService(BattleStatisticService.class).getEnemyStatistic(clientData.lastDamageDealerEnemyId)
                    .incrementKill();
        }
        Context.getService(TCPControl.class).send(12, "");
    }

    public void sendInfoAboutNewArmor(ArmorComponent newArmorComponent) {
        //Отправляем сообщение о том, что мы сменили броню
        //TODO Context.getService(TCPControl.class).send(19, newArmorComponent.getAnimationName());
    }

    public void sendInfoAboutNewGun(GunComponent newGunComponent) {
        //Отправляем сообщение о том, что мы сменили оружие
        //TODO Context.getService(TCPControl.class).send(20, newGunComponent.getSpriteName());
    }
}
