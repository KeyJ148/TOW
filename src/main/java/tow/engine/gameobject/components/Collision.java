package tow.engine.gameobject.components;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.image.Mask;
import tow.engine.gameobject.GameObject;
import org.lwjgl.opengl.GL11;
import tow.engine.image.Color;
import tow.engine.gameobject.QueueComponent;
import tow.engine.gameobject.components.render.Rendering;
import tow.engine.resources.settings.SettingsStorage;

import java.awt.Polygon;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collision extends QueueComponent {

	private Mask mask;//Маска для текстуры этого объекта
	private Vector2<Integer>[] maskAbsolute; //Абсолютные координаты маски от левого верхнего угла карты

	protected ArrayList<Class> collisionObjects = new ArrayList();//Список объектов с которыми надо проверять столкновения
	private ArrayList<CollisionListener> listeners = new ArrayList();//Список объектов которых нужно оповещать при коллизии
	protected boolean calcInThisStep = false;//Расчитывалась ли маска уже в этом степе?

	public Collision(Mask mask) {
		this.mask = mask;
	}

	@Override
	public void addToGameObject(GameObject gameObject){
		super.addToGameObject(gameObject);

		calc();
	}

	@Override
	public void updateComponent(long delta){
		calcInThisStep = false;
		if (getGameObject().hasComponent(Movement.class)) calc();
		checkCollisionFromRoom();
	}

	@Override
	protected void drawComponent() {
		if (!SettingsStorage.LOGGER.MASK_DRAW) return;

		Vector2<Integer>[] maskDrawView = new Vector2[maskAbsolute.length];
		for (int i=0;i<maskDrawView.length;i++)
			maskDrawView[i] = Global.location.camera.toRelativePosition(maskAbsolute[i].copy());

		GL11.glLoadIdentity();
		Color.BLUE.bind();

		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i=0; i<maskDrawView.length;i++) {
			GL11.glVertex2f(maskDrawView[i].x, maskDrawView[i].y);
		}
		GL11.glEnd();
	}

	@Override
	public void destroy() { }

	//Вызывает проверку столкновения с каждым объектом в комнате
	public void checkCollisionFromRoom(){
		if (collisionObjects.size() == 0) return;

		for(int i = 0; i< Global.location.objects.size(); i++){//Цикл перебора всех объектов в комнате
			GameObject objectFromRoom = Global.location.objects.get(i);
			if (objectFromRoom != null && objectFromRoom.hasComponent(Collision.class)){//Если объект не был уничтожен и у него есть маска
				for (Class collisionObject : collisionObjects){ //Цикл перебора объектов, с которыми надо проверять столкновение
					if ((objectFromRoom.getClass().equals(collisionObject)) //Если с эти объектом надо проверять столкновени
						&& (checkCollision(objectFromRoom))){ //И с этим объектом происходит столкновение
						informListeners(objectFromRoom); //Информируем об этом всех слушателей
					}
				}
			}
		}
	}

	//Проверка столкновения с объектом obj2
	public boolean checkCollision(GameObject gameObject2){
		GameObject gameObject1 = getGameObject();

		if (gameObject1.getComponent(Position.class) == null || !gameObject1.hasComponent(Collision.class) || gameObject2.getComponent(Position.class) == null || !gameObject2.hasComponent(Collision.class)) return false;
		Position pos1 = gameObject1.getComponent(Position.class);
		Position pos2 = gameObject2.getComponent(Position.class);
		Collision coll1 = gameObject1.getComponent(Collision.class);
		Collision coll2 = gameObject2.getComponent(Collision.class);

		//Проверка расстояния до объекта столкновения
		double gip1 = Math.sqrt(sqr(coll1.mask.getWidth()) + sqr(coll1.mask.getHeight())); //Гипотенуза объекта
		//Гипотинуза объекта, с которым сравниваем
		double gip2 = Math.sqrt(sqr(coll2.mask.getWidth()) + sqr(coll2.mask.getHeight()));
		//Расстояние от центра до центра
		double dis1To2 = Math.sqrt(sqr(pos1.x-pos2.x)+sqr(pos1.y-pos2.y));

		//Если до объекта слишком далеко, то столкновения нет
		if (dis1To2 > gip1/2 + gip2/2 + 30){
			return false;
		}

		//Если объекты движимы и в этом степе ещё не спросчитывали маску
		if (gameObject1.hasComponent(Movement.class) && !coll1.calcInThisStep) coll1.calc();
		if (gameObject2.hasComponent(Movement.class) && !coll2.calcInThisStep) coll2.calc();
		
		//Просчёт столкновения
		//Создание полигонов
		Polygon p1 = new Polygon();
		for (int i = 0; i < coll1.maskAbsolute.length; i++){
			p1.addPoint(coll1.maskAbsolute[i].x, coll1.maskAbsolute[i].y);
		}

		Polygon p2 = new Polygon();
		for (int i = 0; i < coll2.maskAbsolute.length; i++){
			p2.addPoint(coll2.maskAbsolute[i].x, coll2.maskAbsolute[i].y);
		}

		//Проверка на коллизию полигонов
		for (int i = 0; i<p2.npoints; i++){
			Point point = new Point(p2.xpoints[i], p2.ypoints[i]);
			if (p1.contains(point)) return true;
		}

		for (int i = 0; i<p1.npoints; i++){
			Point point = new Point(p1.xpoints[i], p1.ypoints[i]);
			if (p2.contains(point)) return true;
		}

		return false;
	}

	//Перерасчёт маски относительно начала координат карты
	public void calc(){
		if (!getGameObject().hasComponent(Position.class)) return;

		//Смещена начального угла с Востока на Север
		double direction = getGameObject().getComponent(Position.class).getDirectionDraw();
		direction = Math.toRadians(direction)-Math.PI/2;

		//Просчёт маски
		this.maskAbsolute = new Vector2[mask.getMaskCenter().length];
		double cos = Math.cos(direction);
		double sin = Math.sin(direction);
		for (int i=0;i<mask.getMaskCenter().length;i++){
			double XDouble = cos * mask.getMaskCenter()[i].x;//Первый отступ
			double YDouble = sin * mask.getMaskCenter()[i].x;//"Вперёд"
			double XDouble2 = sin * mask.getMaskCenter()[i].y;//Второй отступ //Math.cos(direction-Math.PI/2) * ...
			double YDouble2 = -cos * mask.getMaskCenter()[i].y;//"В бок" //Math.sin(direction-Math.PI/2) * ...

			this.maskAbsolute[i] = new Vector2<>();
			this.maskAbsolute[i].x = (int) (getGameObject().getComponent(Position.class).x + XDouble + XDouble2);
			this.maskAbsolute[i].y = (int) (getGameObject().getComponent(Position.class).y - YDouble - YDouble2);
		}

		calcInThisStep = true;
	}

	public void addCollisionObjects(Class[] collisionObjects){
		for (Class obj : collisionObjects)
			this.collisionObjects.add(obj);
	}

	public void deleteCollisionObject(Class obj){
		this.collisionObjects.remove(obj);
	}

	public void cleanCollisionObjects(){
		this.collisionObjects = new ArrayList();
	}

	public Mask getMask(){
		return mask;
	}

	public static int sqr(int x){
		return x*x;
	}

	public static double sqr(double x){
		return x*x;
	}

	//Интерфейс реализуют все слушатели
	//При коллизи с объектом из списка коллизий вызывается метод collision
	public interface CollisionListener{
		void collision(GameObject gameObject);
	}

	public void addListener(CollisionListener listener){
		this.listeners.add(listener);
	}

	public void removeListener(CollisionListener listener){
		this.listeners.remove(listener);
	}

	protected void informListeners(GameObject gameObject){
		for (CollisionListener listener : listeners)
			if (listener != null) listener.collision(gameObject);
	}

	@Override
	public Class getComponentClass() {
		return Collision.class;
	}

	@Override
	public List<Class<? extends QueueComponent>> getComponentsUpdatePreviously() {
		return Arrays.asList(Movement.class, Follower.class);
	}

	@Override
	public List<Class<? extends QueueComponent>> getComponentsDrawPreviously() {
		return Arrays.asList(Rendering.class);
	}

}