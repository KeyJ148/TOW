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

    public abstract Class getComponentClass();
    public void update(long time){}
    public void draw(){}
    public void destroy(){}
}
