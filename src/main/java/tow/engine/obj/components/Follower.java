package tow.engine.obj.components;

import tow.engine.Global;
import tow.engine.obj.Component;
import tow.engine.obj.Obj;

import java.util.Arrays;
import java.util.List;

public class Follower extends Component {

    public Obj followUpObj;
    public boolean followDirectionDraw;

    public Follower(Obj followUpObj) {
        this(followUpObj, true);
    }

    public Follower(Obj followUpObj, boolean followDirectionDraw) {
        this.followUpObj = followUpObj;
        this.followDirectionDraw = followDirectionDraw;
    }

    @Override
    public void update(long delta){
        super.update(delta);

        if (followUpObj.hasComponent(Follower.class) && !followUpObj.getComponent(Follower.class).isUpdated()) followUpObj.getComponent(Follower.class).update(delta);

        getObj().getComponent(Position.class).x = followUpObj.getComponent(Position.class).x;
        getObj().getComponent(Position.class).y = followUpObj.getComponent(Position.class).y;
        if (followDirectionDraw) getObj().getComponent(Position.class).setDirectionDraw(followUpObj.getComponent(Position.class).getDirectionDraw());
        Global.location.mapControl.update(getObj());
    }

    //Квадратичная ассимптотика вместо линейной из-за отсутствия кеширования updated у родителей
    //и повторных запросов к ним при вызове update() для себя и каждого родителя (во время общего update())
    public boolean isUpdated() {
        boolean updated = true;

        if (followUpObj.hasComponent(Follower.class)) updated &= followUpObj.getComponent(Follower.class).isUpdated();
        updated &= (getObj().getComponent(Position.class).x == followUpObj.getComponent(Position.class).x);
        updated &= (getObj().getComponent(Position.class).y == followUpObj.getComponent(Position.class).y);
        updated &= (getObj().getComponent(Position.class).getDirectionDraw() == followUpObj.getComponent(Position.class).getDirectionDraw());

        return updated;
    }

    @Override
    public Class getComponentClass() {
        return Follower.class;
    }

    @Override
    public List<Class<? extends Component>> getComponentsExecutePreviously() {
        return Arrays.asList(Movement.class);
    }
}
