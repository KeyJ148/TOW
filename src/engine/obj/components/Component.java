package engine.obj.components;

import engine.obj.Obj;

public abstract class Component {

    private Obj obj;

    public Component(Obj obj){
        this.obj = obj;
    }
    public void update(long time){};
    public void draw(){};
    public Obj getObj(){
        return obj;
    }
}
