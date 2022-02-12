package cc.abro.tow.services;

import cc.abro.orchengine.context.TestService;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.init.interfaces.GameInterface;
import cc.abro.orchengine.location.LocationManager;
import org.liquidengine.legui.DefaultInitializer;

import static cc.abro.tow.services.ServiceUtils.Profiles.TEST_DISABLE_RENDER;

@TestService({TEST_DISABLE_RENDER})
public class TestRender extends Render {

    private final int width;
    private final int height;
    private final int windowId;
    private final int monitorId;

    public TestRender(GameInterface game, LocationManager locationManager) {
        this(game, locationManager, 1920, 1080, 1, 1);
    }

    public TestRender(GameInterface game, LocationManager locationManager,
                      int width, int height, int windowId, int monitorId) {
        super(game, locationManager);
        this.width = width;
        this.height = height;
        this.windowId = windowId;
        this.monitorId = monitorId;
    }

    @Override
    public void start() {}

    @Override
    public void loop() {}

    @Override
    public void stop() {}

    @Override
    public void vsync() {}

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public long getWindowID() {
        return windowId;
    }

    @Override
    public long getMonitorID() {
        return monitorId;
    }

    @Override
    public DefaultInitializer getLeguiInitializer() {
        return null;
    }
}
