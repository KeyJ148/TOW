package cc.abro.orchengine.events;

import cc.abro.orchengine.gameobject.Location;
import lombok.Data;

@Data
public class UpdateLocationEvent {
    private final Location location;
    private final long delta;
}
