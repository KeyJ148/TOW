package tow.engine.obj;

public abstract class Component {

    private Obj obj;

    protected void addToObj(Obj obj){
        if (this.obj == null) this.obj = obj;
        else throw new IllegalArgumentException("Component already have Obj");
    }

    public Obj getObj(){
        return obj;
    }

    public abstract void update(long delta);
    public abstract void draw();
    public abstract void destroy();

    public abstract Class getComponentClass();
}
