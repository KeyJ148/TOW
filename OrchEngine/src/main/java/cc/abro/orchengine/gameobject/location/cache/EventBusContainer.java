package cc.abro.orchengine.gameobject.location.cache;

import cc.abro.orchengine.context.Context;
import com.google.common.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

public class EventBusContainer {

    private final EventBus eventBus = new EventBus();
    private final Set<Object> listeners = new HashSet<>();

    public EventBusContainer() {
        Context.getAllServices().forEach(this::addEventListener);
    }

    public void addEventListener(Object listener) {
        eventBus.register(listener);
        listeners.add(listener);
    }

    public void removeEventListener(Object listener) {
        eventBus.unregister(listener);
        listeners.remove(listener);
    }

    public void destroy() {
        listeners.forEach(eventBus::unregister);
        listeners.clear();
    }

    public void postEvent(Object event) {
        eventBus.post(event);
    }
}
