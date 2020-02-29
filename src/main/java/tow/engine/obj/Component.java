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

    public void update(long time){
        if (updatedInThisStep) return;

        for(Class<? extends Component> componentClass : getComponentsExecutePreviously()){
            if (getObj().hasComponent(componentClass)){
                Component needUpdateComponent = getObj().getComponent(componentClass);
                needUpdateComponent.update(time);
            }
        }

        updatedInThisStep = true;
    }

    public void draw(){

    }

    public void destroy(){

    }


    public abstract Class getComponentClass();
    public abstract List<Class<? extends Component>> getComponentsExecutePreviously();

}
