package tow.engine.obj;

import java.util.List;

public abstract class Component {

    private Obj obj;
    private boolean updatedInThisStep = false;

    protected void addToObj(Obj obj){
        if (this.obj == null) this.obj = obj;
        else throw new IllegalArgumentException("Component already have Obj");
    }

    public Obj getObj(){
        return obj;
    }

    public void setNoUpdated(){
        updatedInThisStep = false;
    }

    public void update(long delta){
        if (updatedInThisStep) return;

        for(Class<? extends Component> componentClass : getComponentsExecutePreviously()){
            if (getObj().hasComponent(componentClass)){
                Component needUpdateComponent = getObj().getComponent(componentClass);
                needUpdateComponent.update(delta);
            }
        }

        updateComponent(delta);
        updatedInThisStep = true;
    }

    public void draw(){

    }

    public void destroy(){

    }

    protected abstract void updateComponent(long delta);

    public abstract Class getComponentClass();
    public abstract List<Class<? extends Component>> getComponentsExecutePreviously();

}
