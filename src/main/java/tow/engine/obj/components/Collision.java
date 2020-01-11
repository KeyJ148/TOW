package tow.engine.obj.components;

import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.image.Camera;
import tow.engine.image.Mask;
import tow.engine.obj.Obj;
import org.lwjgl.opengl.GL11;
import tow.engine.image.Color;
import tow.engine.resources.settings.SettingsStorage;

import java.awt.Polygon;
import java.awt.Point;
import java.util.ArrayList;

public class Collision extends Component {

	private Mask mask;//Маска для текстуры этого объекта
	private Vector2<Integer>[] maskAbsolute; //Абсолютные координаты маски от левого верхнего угла карты

	protected ArrayList<Class> collisionObjects = new ArrayList();//Список объектов с которыми надо проверять столкновения
	private ArrayList<CollisionListener> listeners = new ArrayList();//Список объектов которых нужно оповещать при коллизии
	protected boolean calcInThisStep = false;//Расчитывалась ли маска уже в этом степе?

	public Collision(Obj obj, Mask mask) {
		super(obj);
		this.mask = mask;

		calc();
	}

	@Override
	public void update(long delta){
		calcInThisStep = false;
		if (getObj().movement != null) calc();
		checkCollisionFromRoom();
	}

	@Override
	public void draw(){
		if (!SettingsStorage.LOGGER.MASK_DRAW) return;

		Vector2<Integer>[] maskDrawView = new Vector2[maskAbsolute.length];
		for (int i=0;i<maskDrawView.length;i++)
			maskDrawView[i] = Camera.toRelativePosition(maskAbsolute[i].copy());

		GL11.glLoadIdentity();
		Color.BLUE.bind();

		GL11.glBegin(GL11.GL_LINE_LOOP);
		for (int i=0; i<maskDrawView.length;i++) {
            GL11.glVertex2f(maskDrawView[i].x, maskDrawView[i].y);
        }
		GL11.glEnd();
	}

	//Вызывает проверку столкновения с каждым объектом в комнате
	public void checkCollisionFromRoom(){
		if (collisionObjects.size() == 0) return;

		for(int i = 0; i< Global.room.objects.size(); i++){//Цикл перебора всех объектов в комнате
			Obj objectFromRoom = Global.room.objects.get(i);
			if (objectFromRoom != null && objectFromRoom.collision != null){//Если объект не был уничтожен и у него есть маска
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
	public boolean checkCollision(Obj obj2){
		Obj obj1 = getObj();

		if (obj1.position == null || obj1.collision == null || obj2.position == null || obj2.collision == null) return false;
		Position pos1 = obj1.position;
		Position pos2 = obj2.position;
		Collision coll1 = obj1.collision;
		Collision coll2 = obj2.collision;

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
		if (obj1.movement != null && !coll1.calcInThisStep) coll1.calc();
		if (obj2.movement != null && !coll2.calcInThisStep) coll2.calc();
		
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
		if (getObj().position == null) return;

		//Смещена начального угла с Востока на Север
		double direction = getObj().position.getDirectionDraw();
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
			this.maskAbsolute[i].x = (int) (getObj().position.x + XDouble + XDouble2);
			this.maskAbsolute[i].y = (int) (getObj().position.y - YDouble - YDouble2);
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
		void collision(Obj obj);
	}

	public void addListener(CollisionListener listener){
		this.listeners.add(listener);
	}

	public void removeListener(CollisionListener listener){
		this.listeners.remove(listener);
	}

	protected void informListeners(Obj obj){
		for (CollisionListener listener : listeners)
			if (listener != null) listener.collision(obj);
	}

}