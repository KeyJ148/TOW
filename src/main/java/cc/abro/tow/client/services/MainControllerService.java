package cc.abro.tow.client.services;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.events.input.KeyPressEvent;
import cc.abro.tow.client.ClientData;
import com.google.common.eventbus.Subscribe;
import lombok.RequiredArgsConstructor;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;

@GameService
@RequiredArgsConstructor
public class MainControllerService {

    private final ClientData clientData;
    private final Engine engine;

    @Subscribe
    public void onKeyPressEvent(KeyPressEvent keyPressEvent) {
        if (keyPressEvent.getKey() == GLFW_KEY_F3) {
            clientData.printAnalyzerInfo = !clientData.printAnalyzerInfo;
        }
        if (keyPressEvent.getKey() == GLFW_KEY_ESCAPE) {
            engine.stop();
        }
    }
}
