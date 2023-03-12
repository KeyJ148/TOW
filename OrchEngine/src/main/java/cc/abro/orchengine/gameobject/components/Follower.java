package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;

import java.util.List;

public class Follower extends Component implements Updatable {

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
    public void update(long delta) {
        if (followUpGameObject.hasComponent(Follower.class) && !followUpGameObject.getComponent(Follower.class).isUpdated()){
            followUpGameObject.getComponent(Follower.class).update(delta);
        }

        if (!getGameObject().getPosition().equals(followUpGameObject.getPosition())) {
            getGameObject().setPosition(followUpGameObject.getPosition());
        }
        if (followDirectionDraw) {
            getGameObject().setDirection(followUpGameObject.getDirection()); //TODO тоже надо уведомлять листенер updatePosition
        }
    }

    //Квадратичная ассимптотика вместо линейной из-за отсутствия кеширования updated у родителей
    //и повторных запросов к ним при вызове update() для себя и каждого родителя (во время общего update())
    public boolean isUpdated() {
        boolean updated = true;

        if (followUpGameObject.hasComponent(Follower.class))
            updated &= followUpGameObject.getComponent(Follower.class).isUpdated();
        updated &= (getGameObject().getX() == followUpGameObject.getX());
        updated &= (getGameObject().getY() == followUpGameObject.getY());
        updated &= (getGameObject().getDirection() == followUpGameObject.getDirection());

        return updated;
    }

    @Override
    public List<Class<? extends Updatable>> getPreliminaryUpdateComponents() {
        return List.of(Movement.class);
    }
}
