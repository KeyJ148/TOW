package tow.engine.obj;

import java.util.List;

public abstract class QueueComponent extends Component {

    private boolean updatedInThisStep;
    private boolean drawInThisStep;

    @Override
    protected void addToObj(Obj obj){
        super.addToObj(obj);
        startNewStep();
    }

    public void startNewStep(){
        updatedInThisStep = false;
        drawInThisStep = false;
    }

    @Override
    public void update(long delta){
        if (updatedInThisStep) return;

        for(Class<? extends QueueComponent> componentClass : getComponentsUpdatePreviously()){
            if (getObj().hasComponent(componentClass)){
                Component needUpdateComponent = getObj().getComponent(componentClass);
                needUpdateComponent.update(delta);
            }
        }

        updateComponent(delta);
        updatedInThisStep = true;
    }

    @Override
    public void draw(){
        if (drawInThisStep) return;

        for(Class<? extends QueueComponent> componentClass : getComponentsDrawPreviously()){
            if (getObj().hasComponent(componentClass)){
                Component needDrawComponent = getObj().getComponent(componentClass);
                needDrawComponent.draw();
            }
        }

        drawComponent();
        drawInThisStep = true;
    }

    protected abstract void updateComponent(long delta);
    protected abstract void drawComponent();

    public abstract List<Class<? extends QueueComponent>> getComponentsUpdatePreviously();
    public abstract List<Class<? extends QueueComponent>> getComponentsDrawPreviously();
}
