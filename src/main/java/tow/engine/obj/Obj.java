package tow.engine.obj;

import tow.engine.Global;
import tow.engine.image.TextureHandler;
import tow.engine.obj.components.Collision;
import tow.engine.obj.components.Follower;
import tow.engine.obj.components.Movement;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.particles.Particles;
import tow.engine.obj.components.render.Animation;
import tow.engine.obj.components.render.Rendering;
import tow.engine.obj.components.render.Sprite;

public class Obj {
	public Position position;
	public Movement movement;
	public Rendering rendering;
	public Collision collision;
	public Particles particles;
	public Follower follower;

	public boolean destroy = false;

	public Obj(){}

	public Obj(double x, double y, int depth){
		this();
		position = new Position(this, x, y, depth);
	}

	public Obj(double x, double y, int depth, double directionDraw){
		this(x, y, depth);
		position.setDirectionDraw(directionDraw);
	}

	public Obj(double x, double y, int depth, double directionDraw, TextureHandler textureHandler){
		this(x, y, depth, directionDraw);
		this.rendering = new Sprite(this, textureHandler);
	}

	public Obj(double x, double y, double directionDraw, TextureHandler textureHandler){
		this(x, y, textureHandler.depth, directionDraw, textureHandler);
	}
	
	public Obj(double x, double y, int depth, double directionDraw, TextureHandler[] textureHandler){
		this(x, y, depth, directionDraw);
		rendering = new Animation(this, textureHandler);
	}

	public Obj(double x, double y, double directionDraw, TextureHandler[] textureHandler){
		this(x, y, textureHandler[0].depth, directionDraw, textureHandler);
	}

	public void update(long delta) {
		if (position != null) position.update(delta);
		if (movement != null) movement.update(delta);
		if (rendering != null) rendering.update(delta);
		if (collision != null) collision.update(delta);
		if (particles != null) particles.update(delta);
		if (follower != null) follower.update(delta);

		if (destroy) position.location.objDel(position.id);
	}

	public void draw(){
		if (position != null) position.draw();
		if (movement != null) movement.draw();
		if (rendering != null) rendering.draw();
		if (collision != null) collision.draw();
		if (particles != null) particles.draw();
		if (follower != null) follower.draw();
	}

	public void destroy(){
		destroy = true;
	}
}