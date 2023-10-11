package cc.abro.orchengine.events;

import lombok.Data;

@Data
public class UpdateEvent {
    private final long delta;
}
