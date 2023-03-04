package cc.abro.orchengine.cycle;

import cc.abro.orchengine.context.TestService;
import cc.abro.orchengine.gameobject.LocationManager;
import com.spinyowl.legui.component.Frame;

import static cc.abro.tow.TestUtils.Profiles.TEST_DISABLE_RENDER;

@TestService({TEST_DISABLE_RENDER})
public class TestLeguiRender extends LeguiRender {

    public TestLeguiRender(Render render, LocationManager locationManager) {
        super(render, locationManager);
    }

    @Override
    public void start() {}

    @Override
    public void render(Frame frame) {}

    @Override
    public void pollEvents(Frame frame) {}

    @Override
    public void setFrameFocused(Frame frame) {}

    @Override
    public void stop() {}
}
