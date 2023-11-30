package cc.abro.tow.client.tanks.tank;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.events.TankHitEvent;
import cc.abro.tow.client.settings.GameSettings;
import cc.abro.tow.client.settings.GameSettingsService;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import lombok.Setter;

public class TankVampireComponent extends Component<Tank> implements Updatable {

    private final double vampireDownFromSec;
    private final double vampireUpFromOneDamage;

    @Getter @Setter
    private double vampire = 0.5; //Сколько набрано вампиризма в процентах (от 0 до 1)

    public TankVampireComponent() {
        GameSettings gameSettings = Context.getService(GameSettingsService.class).getGameSettings();
        vampireDownFromSec = gameSettings.getVampireDownFromSec();
        vampireUpFromOneDamage = gameSettings.getVampireUpFromOneDamage();
    }

    @Override
    public void update(long delta) {
        vampire -= vampireDownFromSec * ((double) delta / 1000000000);
        vampire = Math.max(vampire, 0.0);
    }

    @Subscribe
    public void onTankHitEvent(TankHitEvent tankHitEvent) {
        if (tankHitEvent.getTankAttacker() == getGameObject()) {
            vampire += tankHitEvent.getDamage() * vampireUpFromOneDamage;
            vampire = Math.min(vampire, 1.0);
        }
    }
}
