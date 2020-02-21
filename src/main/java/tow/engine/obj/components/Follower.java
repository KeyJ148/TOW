package tow.engine.obj.components;

import tow.engine.Global;
import tow.engine.obj.Obj;

public class Follower extends Component {

    public Obj followUpObj;
    public boolean followDirectionDraw;

    public Follower(Obj obj, Obj followUpObj) {
        this(obj, followUpObj, true);
    }

    public Follower(Obj obj, Obj followUpObj, boolean followDirectionDraw) {
        super(obj);
        this.followUpObj = followUpObj;
        this.followDirectionDraw = followDirectionDraw;
    }

    @Override
    public void update(long delta){
        if (followUpObj.follower != null && !followUpObj.follower.isUpdated()) followUpObj.follower.update(delta);

        getObj().position.x = followUpObj.position.x;
        getObj().position.y = followUpObj.position.y;
        if (followDirectionDraw) getObj().position.setDirectionDraw(followUpObj.position.getDirectionDraw());
        Global.location.mapControl.update(getObj());
    }

    //Квадратичная ассимптотика вместо линейной из-за отсутствия кеширования updated у родителей
    //и повторных запросов к ним при вызове update() для себя и каждого родителя (во время общего update())
    public boolean isUpdated() {
        boolean updated = true;

        if (followUpObj.follower != null) updated &= followUpObj.follower.isUpdated();
        updated &= (getObj().position.x == followUpObj.position.x);
        updated &= (getObj().position.y == followUpObj.position.y);
        updated &= (getObj().position.getDirectionDraw() == followUpObj.position.getDirectionDraw());

        return updated;
    }
}
