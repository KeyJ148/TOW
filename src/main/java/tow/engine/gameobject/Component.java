package tow.engine.gameobject;

public abstract class Component {

    private GameObject gameObject;

    protected void addToGameObject(GameObject gameObject){
        if (this.gameObject == null) this.gameObject = gameObject;
        else throw new IllegalArgumentException("Component already added to GameObject");
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public abstract void update(long delta);

    public abstract void draw();

    public void destroy() {
    }

    public void freeze() {
    }

    public void unfreeze() {
    }

    public abstract Class getComponentClass();
}
