package tow.engine3.obj;

import tow.engine.Global;
import tow.engine3.image.TextureHandler;
import tow.engine3.obj.components.Collision;
import tow.engine3.obj.components.Movement;
import tow.engine3.obj.components.Position;
import tow.engine3.obj.components.particles.Particles;
import tow.engine3.obj.components.render.Animation;
import tow.engine3.obj.components.render.Rendering;
import tow.engine3.obj.components.render.Sprite;

public class Obj {
	public Position position;
	public Movement movement;
	public Rendering rendering;
	public Collision collision;
	public Particles particles;

	public boolean destroy = false;
	public Obj follow;

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

		if (destroy) position.room.objDel(position.id);
	}

	public void updateFollow(){
		if (follow != null && follow.position != null){
			position.x = follow.position.x;
			position.y = follow.position.y;
			position.setDirectionDraw(follow.position.getDirectionDraw());
			Global.room.mapControl.update(this);
		}
	}

	public void draw(){
		if (position != null) position.draw();
		if (movement != null) movement.draw();
		if (rendering != null) rendering.draw();
		if (collision != null) collision.draw();
		if (particles != null) particles.draw();
	}

	public void destroy(){
		destroy = true;
	}
}