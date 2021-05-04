package tow.engine.gameobject;

import java.io.BufferedReader;
import java.util.List;

public abstract class QueueComponent extends Component {

    private boolean updatedInThisStep;
    private boolean drawInThisStep;

    @Override
    protected void addToGameObject(GameObject gameObject){
        super.addToGameObject(gameObject);
        startNewStep();
    }

    public void startNewStep(){
        updatedInThisStep = false;
        drawInThisStep = false;
    }

    /**
     * Method will call when game world will updating. All components from {@link #getPreliminaryUpdateComponents} will
     * update, and after that this component will be updated in {@link #updateComponent}.
     * @param delta The count of nanoseconds passed since last update
     */

    @Override
    public void update(long delta){
        if (updatedInThisStep) return;

        for(Class<? extends QueueComponent> componentClass : getPreliminaryUpdateComponents()){
            if (getGameObject().hasComponent(componentClass)){
                Component needUpdateComponent = getGameObject().getComponent(componentClass);
                needUpdateComponent.update(delta);
            }
        }

        updateComponent(delta);
        updatedInThisStep = true;
    }

    /**
     * Method will call when game world will drawing. All components from {@link #getPreliminaryDrawComponents} will
     * draw, and after that this component will be draw in {@link #drawComponent}.
     */
    @Override
    public void draw(){
        if (drawInThisStep) return;

        for(Class<? extends QueueComponent> componentClass : getPreliminaryDrawComponents()){
            if (getGameObject().hasComponent(componentClass)){
                Component needDrawComponent = getGameObject().getComponent(componentClass);
                needDrawComponent.draw();
            }
        }

        drawComponent();
        drawInThisStep = true;
    }

    protected abstract void updateComponent(long delta);
    protected abstract void drawComponent();

    /**
     * @return List of components that needed to {@link #update} before {@link #update} this component.
     */
    public abstract List<Class<? extends QueueComponent>> getPreliminaryUpdateComponents();

    /**
     * @return List of components that needed to {@link #draw} before {@link #draw} this component.
     */
    public abstract List<Class<? extends QueueComponent>> getPreliminaryDrawComponents();
}
