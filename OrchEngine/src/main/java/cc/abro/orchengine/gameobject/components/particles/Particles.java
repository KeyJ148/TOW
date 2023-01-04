package cc.abro.orchengine.gameobject.components.particles;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.render.Rendering;

import java.util.*;

public abstract class Particles extends Component {

    public Set<Part> parts = new HashSet<>();
    public boolean rotate = false;
    public boolean destroyObject = false;//Удалить объект использующий эту систему частиц после окончания системы частиц

    @Override
    public void update(long delta) {
        Iterator<Part> iterator = parts.iterator();
        while (iterator.hasNext()) {
            Part part = iterator.next();
            part.x = part.x + (part.speed * Math.cos(Math.toRadians(part.direction)) * ((double) delta / 1000000000));
            part.y = part.y - (part.speed * Math.sin(Math.toRadians(part.direction)) * ((double) delta / 1000000000));
            part.life -= ((double) delta) / 1000000000L;//Т.к. на вход подается в нано-секундах, а хранится в секундах
            if (part.life <= 0) iterator.remove();
            else updateChild(delta, part);
        }

        if (parts.size() == 0 && destroyObject) getGameObject().destroy();
    }

    //Метод переопределяется в наследниках для обработки каждый степ
    public void updateChild(long delta, Part part) {
    }

    @Override
    public Class getComponentClass() {
        return Particles.class;
    }

    @Override
    public List<Class<? extends Component>> getPreliminaryUpdateComponents() {
        return Arrays.asList();
    }

    @Override
    public List<Class<? extends Component>> getPreliminaryDrawComponents() {
        return Arrays.asList(Rendering.class);
    }
}
