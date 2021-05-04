package tow.engine.gameobject.components;

import tow.engine.Global;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.QueueComponent;

import java.util.Arrays;
import java.util.List;

public class Follower extends QueueComponent {

    public GameObject followUpGameObject;
    public boolean followDirectionDraw;

    public Follower(GameObject followUpGameObject) {
        this(followUpGameObject, true);
    }

    public Follower(GameObject followUpGameObject, boolean followDirectionDraw) {
        this.followUpGameObject = followUpGameObject;
        this.followDirectionDraw = followDirectionDraw;
    }

    @Override
    public void updateComponent(long delta){
        if (followUpGameObject.hasComponent(Follower.class) && !followUpGameObject.getComponent(Follower.class).isUpdated()) followUpGameObject.getComponent(Follower.class).update(delta);

        getGameObject().getComponent(Position.class).x = followUpGameObject.getComponent(Position.class).x;
        getGameObject().getComponent(Position.class).y = followUpGameObject.getComponent(Position.class).y;
        if (followDirectionDraw) getGameObject().getComponent(Position.class).setDirectionDraw(followUpGameObject.getComponent(Position.class).getDirectionDraw());
        Global.location.mapControl.update(getGameObject());
    }

    @Override
    protected void drawComponent() { }

    //Квадратичная ассимптотика вместо линейной из-за отсутствия кеширования updated у родителей
    //и повторных запросов к ним при вызове update() для себя и каждого родителя (во время общего update())
    public boolean isUpdated() {
        boolean updated = true;

        if (followUpGameObject.hasComponent(Follower.class)) updated &= followUpGameObject.getComponent(Follower.class).isUpdated();
        updated &= (getGameObject().getComponent(Position.class).x == followUpGameObject.getComponent(Position.class).x);
        updated &= (getGameObject().getComponent(Position.class).y == followUpGameObject.getComponent(Position.class).y);
        updated &= (getGameObject().getComponent(Position.class).getDirectionDraw() == followUpGameObject.getComponent(Position.class).getDirectionDraw());

        return updated;
    }

    @Override
    public Class getComponentClass() {
        return Follower.class;
    }

    @Override
    public List<Class<? extends QueueComponent>> getPreliminaryUpdateComponents() {
        return Arrays.asList(Movement.class);
    }

    @Override
    public List<Class<? extends QueueComponent>> getPreliminaryDrawComponents() {
        return Arrays.asList();
    }
}
