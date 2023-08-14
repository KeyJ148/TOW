package cc.abro.orchengine.gameobject.components.particles;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.gameobject.components.render.DrawableComponent;
import cc.abro.orchengine.services.ServiceConsumer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Particles extends DrawableComponent implements Updatable, ServiceConsumer {

    public Set<Part> parts = new HashSet<>();
    public boolean rotate = false;
    public boolean destroyObject = false;//Удалить объект использующий эту систему частиц после окончания системы частиц

    public Particles(int z) {
        setZ(z);
    }

    @Override
    public void update(long delta) {
        Iterator<Part> iterator = parts.iterator();
        while (iterator.hasNext()) {
            double deltaInSeconds = ((double) delta) / 1000000000;
            Part part = iterator.next();
            part.x = part.x + (part.speed * Math.cos(Math.toRadians(part.direction)) * deltaInSeconds);
            part.y = part.y - (part.speed * Math.sin(Math.toRadians(part.direction)) * deltaInSeconds);
            part.life -= deltaInSeconds; //Т.к. на вход подается в нано-секундах, а хранится в секундах
            if (part.life <= 0) iterator.remove();
            else updateChild(delta, part);
        }

        if (parts.size() == 0 && destroyObject) getGameObject().destroy();
    }

    //Метод переопределяется в наследниках для обработки каждый степ
    public void updateChild(long delta, Part part) {
    }

    @Override
    public Class<? extends Component> getComponentClass() {
        return Particles.class;
    }
}
