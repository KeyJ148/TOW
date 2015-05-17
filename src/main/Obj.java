package main;

import java.awt.Graphics2D;

import main.image.Animation;
import main.image.DepthVector;
import main.image.Mask;
import main.image.Rendering;
import main.image.Sprite;

public class Obj {
	
	public double x;
	public double y;
	public double xView = 0; //для движения камеры
	public double yView = 0; //коры объекта в окне
	
	public double direction; //0, 360 - в право, против часовой - движение
	public double directionDraw; //0, 360 - в право, против часовой - отрисовка
	
	public double speed; //На сколько пикселей объект смещается за 1 секунду
	public int depth;
	
	public String[] collObj;//список объектов с которыми надо проверять столкновения
	private boolean collHave = false;//есть ли столкновения
	
	private boolean maskDynamic;//обновление маски каждый тик (true = динамичный)
								//нужен для движущихся или поворачивающихся объектов
	private long id; //уникальный номер
	private boolean anim; //Объекту присвоен спрайт или анимация?(true = анимация)
	private boolean destroy = false;
	
	public double xPrevious;//коры объекта в предыдущем шаге
	public double yPrevious;//(для столкновения)
	public double directionPrevious;//директион объекта в предыдущем шаге (для столкновения)
	
	public Mask mask;
	private Sprite sprite;
	private Animation animation;
	private Rendering image;
	
	public Obj(double x, double y, double speed, double direction, int depth, boolean maskDynamic, Sprite sprite){
		try{	
			this.sprite = sprite;
			this.image = this.sprite;
			this.anim = false;
			this.mask = sprite.getMask().clone();
		} catch (CloneNotSupportedException e) {
			Global.error("Failed with clone object. Id = " + id);
		}
		
		init(x,y,speed,direction,depth,maskDynamic);
	}
	
	public Obj(double x, double y, double speed, double direction, int depth, boolean maskDynamic, Animation animation){
		try{	
			this.animation = animation.clone();
			this.image = this.animation;
			this.anim = true;
			this.mask = animation.getMask().clone();
		} catch (CloneNotSupportedException e) {
			Global.error("Failed with clone object. Id = " + id);
		}
		init(x,y,speed,direction,depth,maskDynamic);
	}
	
	private void init(double x, double y, double speed, double direction, int depth, boolean maskDynamic){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
		this.directionDraw = direction;
		this.depth = depth;
		this.id = Global.id;
		Global.id++;
		this.maskDynamic = maskDynamic;
		
		Global.obj.add(this);
		
		depthAddVector(depth, this.id); //Добавляем объект в массив в зависимости от его глубины
		mask.calc(this.x,this.y,this.directionDraw);//расчёт маски
		
		if (this.anim){
			if (Global.setting.DEBUG_CONSOLE) System.out.println("Object \"" + animation.path + "\" create. Id = " + id);
		} else {
			if (Global.setting.DEBUG_CONSOLE) System.out.println("Object \"" + sprite.path + "\" create. Id = " + id);
		}
		
	}
	
	private void depthAddVector(int depth, long id){
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
				dv.delete(id);
			}
		}
		this.destroy = true;
	}

	public void draw(Graphics2D g){
		//для отрисовки объекта с поворотом на direction
		directionDrawEqulas();
		
		//для движения камеры
		xView = Global.cameraXView - (Global.cameraX - x);
		yView = Global.cameraYView - (Global.cameraY - y);
	
		if (Global.game.render.needDraw((int) xView, (int) yView, image.getWidth(), image.getHeight())){
			image.draw(g,(int) Math.round(xView),(int) Math.round(yView), Math.toRadians(directionDraw));
			if (Global.setting.MASK_DRAW) mask.draw(g);
		}
	}
	
	public void update() {
		updateChildStart();
		
		//должен быть раньше updateChildMid, что бы танк не мог повернуть в стену
		this.xPrevious = this.x;
		this.yPrevious = this.y;
		this.directionPrevious = this.direction;
		
		updateChildMid();//step у дочерних объектов
		
		this.x = this.x + this.speed * Math.cos(Math.toRadians(direction));
		this.y = this.y - this.speed * Math.sin(Math.toRadians(direction));
		
		directionDrawEqulas();
		
		if (anim) {
			animation.update();
			if (maskDynamic){
				mask.calc(this.x,this.y,this.directionDraw);
			}
		} else {
			if (maskDynamic){
				mask.calc(this.x,this.y,this.directionDraw);
			}
		}
		
		if(this.collHave){
			mask.collCheck(collObj,this);
		}
		
		updateChildFinal();//step у дочерних объектов
		
		if (destroy){
			Global.delObj(id);
		}
	}
	
	public void directionDrawEqulas(){
		this.directionDraw = this.direction;
	}
	
	/*
	 * 
	 * ДАЛЬШЕ МЕТОДЫ SET, GET
	 * И МЕТОДЫ ПЕРЕОПРЕДЕЛЯЕМЫЕ У НАСЛЕДНИКОВ (updateChild, collReport);
	 * ТАК ЖЕ МЕТОДЫ P(ВЫВОД СООБЩЕНИЙ В КОНСОЛЬ)
	 * 
	 */
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setXcenter(double x){
		this.x=x-this.mask.width/2;
	}
	
	public void setYcenter(double y){
		this.y=y-this.mask.height/2;
	}
	
	public void setXView(double xView){
		this.xView = xView;
	}
	
	public void setYView(double yView){
		this.yView = yView;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public void setDirection(double direction){
		this.direction = direction;
	}
	
	public void setDirectionDraw(double direction){
		this.directionDraw = direction;
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
	
	public void setCollObj(String[] collObj){
		this.collObj = collObj;
		this.collHave = true;
	}
	
	public void setMaskDynamic(boolean maskDynamic){
		this.maskDynamic = maskDynamic;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getXcenter(){
		return x+this.mask.width/2;
	}
	
	public double getYcenter(){
		return y+this.mask.height/2;
	}
	
	public double getXView(){
		return xView;
	}
	
	public double getYView(){
		return yView;
	}
	
	public double getXViewCenter(){
		return xView+this.mask.width/2;
	}
	
	public double getYViewCenter(){
		return yView+this.mask.height/2;
	}
	
	public double getDirection(){
		if (direction%360 >= 0){
			return direction%360;
		} else {
			return 360-Math.abs(direction%360);
		}
	}
	
	public double getDirectionDraw(){
		if (directionDraw%360 >= 0){
			return directionDraw%360;
		} else {
			return 360-Math.abs(directionDraw%360);
		}
	}
	
	public double getXPrevious(){
		return xPrevious;
	}
	
	public double getYPrevious(){
		return yPrevious;
	}
	
	public double getDirectionPrevious(){
		return directionPrevious;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public int getDepth(){
		return depth;
	}
	
	public long getId(){
		return id;
	}
	
	public boolean getDestroy(){
		return destroy;
	}
	
	public Animation getAnimation(){
		return animation;
	}
	
	public Rendering getImage(){
		return image;
	}
	
	public void p(String s){
		System.out.println(s);
	}
	
	public void p(int x){
		System.out.println(x);
	}
	
	public void p(){
		System.out.println("ALARM!!!");
	}
	
	public void updateChildStart(){}
	public void updateChildMid(){}
	public void updateChildFinal(){}
	public void collReport(Obj obj){}

}