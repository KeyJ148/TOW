package cc.abro.orchengine.gameobject;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

public abstract class Component {

    private GameObject gameObject;
    @Getter
    private ComponentsContainer parentContainer;
    private boolean updatedInThisStep = false;
    private boolean drawInThisStep = false;

    protected void notifyAboutAddToComponentsContainer(ComponentsContainer parentContainer) {
        if (this.parentContainer == null) this.parentContainer = parentContainer;
        else throw new IllegalArgumentException("Component already added to ComponentsContainer");
    }

    protected void notifyAboutAddToGameObject(GameObject gameObject) {
        if (this.gameObject == null) this.gameObject = gameObject;
        else throw new IllegalArgumentException("Component already added to GameObject");
    }

    public void startNewStep() {
        updatedInThisStep = false;
        drawInThisStep = false;
    }

    public final void updateIfNeed(long delta) {
        if (updatedInThisStep) return;
        update(delta);
        updatedInThisStep = true;
    }

    public final void drawIfNeed() {
        if (drawInThisStep) return;
        draw();
        drawInThisStep = true;
    }

    public abstract void update(long delta);

    public abstract void draw();

    public abstract Class<? extends Component> getComponentClass();

    /**
     * @return List of components that needed to {@link #update} before {@link #update} this component.
     */
    //TODO проверить, что там где надо используют дефолтную реализацию
    public List<Class<? extends Component>> getPreliminaryUpdateComponents() {
        return Collections.emptyList();
    }

    /**
     * @return List of components that needed to {@link #draw} before {@link #draw} this component.
     */
    //TODO проверить, что там где надо используют дефолтную реализацию
    public List<Class<? extends Component>> getPreliminaryDrawComponents() {
        return Collections.emptyList();
    }

    public void destroy() {
    }

    public GameObject getGameObject() {
        if (gameObject == null) {
            gameObject = getParentContainer().getGameObject();
        }
        return gameObject;
    }
}
