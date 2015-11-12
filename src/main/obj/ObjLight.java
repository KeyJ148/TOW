package main.obj;

import java.awt.Graphics2D;

import main.Global;
import main.image.DepthVector;
import main.image.Rendering;

public class ObjLight {
	
	public double x;
	public double y;
	public double xView = 0; //для движения камеры
	public double yView = 0; //коры объекта в окне
	public int depth;
	public double directionDraw; //0, 360 - в право, против часовой - отрисовка
	
	public boolean anim; //Объекту присвоен спрайт или анимация?(true = анимация)
	public boolean destroy = false;
	
	public Rendering image;
	
	private long id; //уникальный номер
	
	public ObjLight(double x, double y, double directionDraw, int depth, Rendering image){
		try{	
			this.image = image.clone();
			this.anim = image.isAnim();
		} catch (CloneNotSupportedException e) {
			Global.error("Failed with clone light object. Id = " + getId());
		}
		
		this.x = x;
		this.y = y;
		this.directionDraw = directionDraw;
		this.depth = depth;
		setId(Global.id);
		Global.id++;
		
		Global.addObj(id, this);
		
		depthAddVector(depth, getId()); //Добавляем объект в массив в зависимости от его глубины
	}
	
	public void draw(Graphics2D g){
		//для движения камеры
		xView = Global.cameraXView - (Global.cameraX - x);
		yView = Global.cameraYView - (Global.cameraY - y);
	
		image.draw(g,(int) Math.round(xView),(int) Math.round(yView), Math.toRadians(directionDraw));
	}
	
	public void update() {
		image.update();
		
		if (destroy){
			Global.delObj(getId());
		}
	}
	
	public void depthAddVector(int depth, long id){
		boolean flag = false;
		DepthVector dv;
		for (int i=0; i<Global.depth.size(); i++){
			dv = (DepthVector) Global.depth.get(i);
			if (dv.depth == depth){
				dv.add(id);
				flag = true;
				break;
			}
		}
	
		if (!flag){
			Global.depth.add(new DepthVector(depth, id));
		}
	}
	
	public void destroy(){
		DepthVector dv;
		for (int i=0; i<Global.depth.size(); i++){
			dv = (DepthVector) Global.depth.get(i);
			if (dv.depth == depth){
				dv.delete(getId());
			}
		}
		this.destroy = true;
	}
	
	public boolean isLight(){
		return true;
	}
	
	/*
	 * ДАЛЬШЕ МЕТОДЫ GET
	 */
	
	public long getId(){
		return id;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getXView(){
		return xView;
	}
	
	public double getYView(){
		return yView;
	}
	
	public double getDirectionDraw(){
		if (directionDraw%360 >= 0){
			return directionDraw%360;
		} else {
			return 360-Math.abs(directionDraw%360);
		}
	}
	
	public int getDepth(){
		return depth;
	}
	
	public boolean getDestroy(){
		return destroy;
	}
	
	/*
	 * ДАЛЬШЕ МЕТОДЫ SET
	 */
	protected void setId(long id){
		this.id = id;
	}
	

	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setDirectionDraw(double direction){
		this.directionDraw = direction;
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
}
