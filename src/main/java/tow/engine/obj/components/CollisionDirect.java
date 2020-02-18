package tow.engine.obj.components;

import org.lwjgl.opengl.GL11;
import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.image.Mask;
import tow.engine.obj.Obj;
import tow.engine.image.Color;
import tow.engine.resources.settings.SettingsStorage;

import java.util.ArrayList;

public class CollisionDirect extends Collision {

	private int range; //Максимальная дальность движения объекта
	private ArrayList<Integer> dynamicId = new ArrayList();//Ид динамических объектов, с которыми надо проверять столкновения
	private Vector2<Integer> positionCollision; //Позиция столкновения
	private int start = 0;//В комнате была проверена коллизия со всеми статическими объектами у которых id<start
	private boolean nearCollision = false;//Находимся близко к позиции столкновения

	public CollisionDirect(Obj obj, Mask mask, int range) {
		super(obj, mask);
		this.range = range;
	}

	//Нельзя в конструкторе, т.к. для првоерки нужен массив collisionObject,
	//а он устанавливается только после создания класса
	public void init(){
		separationCollisions();
	}

	@Override
	public void update(long delta){
		calcInThisStep = false;

		separationCollisions();
		for (Integer id : dynamicId){ //Проверяем столкновения со всеми перемещающимися объектами
			Obj objectFromRoom = Global.location.objects.get(id);
			if (objectFromRoom != null && objectFromRoom.collision != null && checkCollision(objectFromRoom)){
				informListeners(objectFromRoom); //Информируем об этом всех слушателей
			}
		}

		//Если есть точка столкновения и она близко
		if (positionCollision != null) {
			double distantionToCollision = Math.sqrt(sqr(getObj().position.x - positionCollision.x) + sqr(getObj().position.y - positionCollision.y));
			if (distantionToCollision <= getObj().movement.speed * 3) nearCollision = true;
		}

		//Если максимальная близость к точке столкновения, то действуем как обычный объект
		if (nearCollision) super.update(delta);
	}

	//Отрисовка маски
	@Override
	public void draw(){
		if (!SettingsStorage.LOGGER.MASK_DRAW || positionCollision == null) return;

		GL11.glLoadIdentity();
		Color.BLUE.bind();

		Vector2<Integer> relativePosition = Global.location.camera.toRelativePosition(new Vector2(positionCollision.x-10, positionCollision.y-10));
		int x = relativePosition.x;
		int y = relativePosition.y;
		int w = 20;
		int h = 20;
		GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(x+w, y);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(x+w, y+h);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(x, y+h);
		GL11.glEnd();
	}

	//Поиск в общем массиве id, которые динамичны и сталкиваются с этим объектом
	//Также поиск статичных объектов для проверки столкновения при помощи траектории
	private void separationCollisions(){
		for(int i = start; i< Global.location.objects.size(); i++){//Цикл перебора объектов в комнате
			Obj objFromRoom = Global.location.objects.get(i);
			if (objFromRoom != null && objFromRoom.collision != null){//Если объект не был уничтожен и у него есть маска
				for (Class collisionObject : collisionObjects) {//Цикл перебора объектов с которыми надо проверять столкновения
					if (objFromRoom.getClass().equals(collisionObject)) {//Если с объектом из комнаты надо проверять столкновения
						if (objFromRoom.movement != null) dynamicId.add(i);//Если объект из комнаты динамичен
						else checkCollisionDirect(objFromRoom);//Если объект из комнаты статичен
					}
				}
			}
		}

		this.start = Global.location.objects.size();
	}

	//Расчёт столкновения прямолинейно перемещающегося объекта с статичным объектом obj
	private void checkCollisionDirect(Obj obj){
		double startX = getObj().position.x;
		double startY = getObj().position.y;
		double directionDraw = getObj().position.getDirectionDraw();

		double gipMe = Math.sqrt(sqr(getMask().getWidth()) + sqr(getMask().getHeight())); //Гипотенуза объекта
		double gipOther = Math.sqrt(sqr(obj.collision.getMask().getWidth()) + sqr(obj.collision.getMask().getHeight())); //Гипотинуза объекта, с которым сравниваем
		double disMeToOther = Math.sqrt(sqr(startX-obj.position.x) + sqr(startY-obj.position.y)); //Расстояние от центра до центра
		
		if (disMeToOther < gipOther/2+gipMe/2+30){//Если объект находится близко к другому объекту
			nearCollision = true ;//В дальнейшем обрабатываем как обычный объект
		} else if (disMeToOther < range+gipOther/2+gipMe/2+30){
			double k, b, x0, y0, r;
			if ((directionDraw == 90.0) || (directionDraw == 270.0) || (directionDraw == 0.0) || (directionDraw == 180.0)){//КОСТЫЛЬ, ЛЮТЫЙ КОСТЫЛЬ
				directionDraw += Math.pow(0.1, 10);
			}
			k = Math.tan(Math.toRadians(directionDraw));
			b = -(startY+k*startX);
			x0 = obj.position.x;
			y0 = -obj.position.y;
			r = gipOther/2;
			
			double aSqr, bSqr, cSqr, D;//Дискрименант и члены квадратного уровнения
			aSqr = 1+sqr(k);
			bSqr = 2*k*b-2*x0-2*k*y0;
			cSqr = sqr(x0)+sqr(b)-2*b*y0+sqr(y0)-sqr(r);
			D = sqr(bSqr) - 4*aSqr*cSqr;
			
			if (D >= 0){//Столкновение есть
				double collX=0, collY=0;//Точки пересечения в итоге
				double x1Coll, x2Coll, y1Coll, y2Coll;//Корни уровнения (Точки столкновения)
				x1Coll = (-bSqr+Math.sqrt(D))/(2*aSqr);
				x2Coll = (-bSqr-Math.sqrt(D))/(2*aSqr);
				y1Coll = k*x1Coll + b;
				y2Coll = k*x2Coll + b;
				
				//Прямая двигается в двух направлениях, а нам нужно только одно (Луч)
				if (	((directionDraw >= 0) && (directionDraw < 90) && (x1Coll >= startX) && (y1Coll > -startY)) ||
						((directionDraw > 90) && (directionDraw < 180) && (x1Coll <= startX) && (y1Coll > -startY)) ||
						((directionDraw >= 180) && (directionDraw < 270) && (x1Coll <= startX) && (y1Coll < -startY)) ||
						((directionDraw > 270) && (directionDraw < 360) && (x1Coll >= startX) && (y1Coll < -startY))	){
					
					//Какой из корней находится ближе к старту
					double coll1Gip = Math.sqrt(sqr(x1Coll-startX) + sqr(-y1Coll-startY));//-y1Coll перевод из мат. сис. кор в игровую
					double coll2Gip = Math.sqrt(sqr(x2Coll-startX) + sqr(-y2Coll-startY));
					if (coll1Gip < coll2Gip){
						collX = x1Coll;
						collY = y1Coll;
					} else {
						collX = x2Coll;
						collY = y2Coll;
					}
				}
				
				//Эти точки ближе чем уже имеющаяся?
				if (positionCollision != null){
					double collOld = Math.sqrt(sqr(positionCollision.x-startX) + sqr(positionCollision.y-startY));
					double collNew = Math.sqrt(sqr(collX-startX) + sqr(-collY-startY));//-collY перевод из мат. сис. кор в игровую
					if (collNew < collOld){
						positionCollision.x = (int) collX;
						positionCollision.y = (int) -collY;
					}
				} else {
					positionCollision = new Vector2<>();
					positionCollision.x = (int) collX;
					positionCollision.y = (int) -collY;//Перевод из математических кординат в игровые
				}
			}
		}
	}
}