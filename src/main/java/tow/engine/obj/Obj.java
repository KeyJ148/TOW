package tow.engine.obj;

import java.util.*;

public class Obj {

	private Map<Class<? extends Component>, Component> components = new HashMap<>();
	private boolean destroy = false;

	public Obj(){
		this(new ArrayList<>());
	}

	public Obj(Collection<Component> initComponents){
		for (Component component : initComponents){
			setComponent(component);
		}
	}


	public void setComponent(Component component){
		component.addToObj(this);
		components.put(component.getComponentClass(), component);
	}

	public <T extends Component> T getComponent(Class<T> classComponent){
		return (T) components.get(classComponent);
	}

	public boolean hasComponent(Class<? extends Component> classComponent){
		return getComponent(classComponent) != null;
	}

	public void removeComponent(Class<? extends Component> classComponent){
		components.remove(classComponent);
	}

	public void update(long delta){
		for (Component component : components.values()){
			if (!isDestroy()) component.update(delta);
			else component.destroy();
		}
	}

	public void draw(){
		for (Component component : components.values()){
			component.draw();
		}
	}

	public void destroy(){
		destroy = true;
	}

	public boolean isDestroy(){
		return destroy;
	}

	//TODO: del
	public Component getComponentOLD(Class<? extends Component> classComponent){
		return components.get(classComponent);
	}

	//TODO: приотизация и требования других компонент

	/*
	public void update2(long delta) {
		if (position != null) position.update(delta);
		if (movement != null) movement.update(delta);
		if (rendering != null) rendering.update(delta);
		if (collision != null) collision.update(delta);
		if (particles != null) particles.update(delta);
		if (follower != null) follower.update(delta);

		if (destroy) position.location.objDel(position.id);
	}

	public void draw2(){
		if (position != null) position.draw();
		if (movement != null) movement.draw();
		if (rendering != null) rendering.draw();
		if (collision != null) collision.draw();
		if (particles != null) particles.draw();
		if (follower != null) follower.draw();
	}*/


}