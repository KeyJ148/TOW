package cc.abro.orchengine.gameobject.components.collision;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.components.interfaces.Drawable;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.services.GuiService;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.function.Consumer;

public abstract class DrawableCollidableComponent extends CollidableComponent implements Drawable {

    private final GuiService guiService;

    public DrawableCollidableComponent() {
        guiService = Context.getService(GuiService.class);
    }

    @Override
    public void draw() {
        if (!guiService.isMaskRendering()) return;

        List<Vector2<Double>> maskDrawPoints = getAbsoluteMaskPoints().stream()
                .map(getLocationManager().getActiveLocation().getCamera()::toRelativePosition)
                .toList();

        GL11.glLoadIdentity();
        Color.BLUE.bind();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (Vector2<Double> maskPoint : maskDrawPoints) {
            GL11.glVertex2f(maskPoint.x.floatValue(), maskPoint.y.floatValue());
        }
        GL11.glEnd();
    }

    @Override
    public int getZ() {
        return 5000;
    }

    //Not implemented methods because Z never changes
    @Override
    public void addChangeZListener(Consumer<Drawable> listener) {}
    @Override
    public void removeChangeZListener(Consumer<Drawable> listener) {}
    @Override
    public void notifyChangeZListeners() {}
}
