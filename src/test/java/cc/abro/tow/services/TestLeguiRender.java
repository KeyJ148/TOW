package cc.abro.tow.services;

import cc.abro.orchengine.context.TestService;
import cc.abro.orchengine.cycle.LeguiRender;
import cc.abro.orchengine.cycle.Render;
import org.liquidengine.legui.component.Frame;

import static cc.abro.tow.services.ServiceUtils.Profiles.TEST_DISABLE_RENDER;

@TestService({TEST_DISABLE_RENDER})
public class TestLeguiRender extends LeguiRender {

    public TestLeguiRender(Render render) {
        super(render);
    }

    @Override
    public void render(Frame frame) {}

    @Override
    public void pollEvents(Frame frame) {}

    @Override
    public void setFrameFocused(Frame frame) {}
}
