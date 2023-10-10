package cc.abro.tow.client.events;

import cc.abro.tow.client.tanks.Tank;
import lombok.Data;

@Data
public class TankExplodedEvent {

    private final Tank tank;
}
