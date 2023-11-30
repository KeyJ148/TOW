package cc.abro.tow.client.events;

import cc.abro.tow.client.tanks.tank.Tank;
import lombok.Data;

@Data
public class TankHitEvent {

    private final Tank tankAttacker;
    private final Tank tankReceiver;
    private final double damage;
}
