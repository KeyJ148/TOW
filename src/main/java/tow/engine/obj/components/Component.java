package tow.engine.obj.components;

import tow.engine.obj.Obj;

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
