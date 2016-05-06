package tow.image;

import java.awt.Point;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.opengl.GL11;

import tow.Global;
import tow.obj.Obj;
import tow.obj.ObjLight;
import tow.player.Bullet;

public class Mask implements Cloneable{
	
	public int[] maskX; //маска относительно центра такнка
	public int[] maskY;
	public int[] maskXDraw; //маска относительно начала координат
	public int[] maskYDraw;
	
	public int width; //ширина и высота спрайта
	public int height;
	
	public boolean calcInThisStep = false;
	public boolean dynamic; //обновление маски каждый тик (true = динамичный)
							//нужен для движущихся или поворачивающихся объектов
	
	public boolean bullet; //Прямая тракетория движения
	public ArrayList<Integer> dynamicId;//Ид динамических объектов, с которыми надо проверять столкновения
	public double collX = -1;//-1 заместо null
	public double collY = -1;
	public int start = 0;//id з глоабала, с которго надо начинать перебор
	
	public Mask (String path, int width, int height) {
		this.width = width;
		this.height = height;
		//Загрузка маски из файла
		StringBuffer Bpath = new StringBuffer(path); 
		Bpath.delete(path.lastIndexOf('.'),path.length());
		path = new String(Bpath);
		path = path + ".txt";
		if (new File(path).exists()){
			try{
				BufferedReader fileReader = new BufferedReader(new FileReader(path));
				parser(fileReader);
				if (Global.setting.DEBUG_CONSOLE_MASK) Global.p("Load mask \"" + path + "\" complited.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { //создание маски при отсутствие файла
			maskX = new int[] {0,this.width-1,this.width-1,0};
			maskY = new int[] {0,0,this.height-1,this.height-1};
		}
		center();
	}
	
	//клонирование объекта
	public Mask clone() throws CloneNotSupportedException {
		return (Mask)super.clone();
	}
	
	//считывание маски из файла
    public void parser(BufferedReader fileReader) throws IOException{
		String s;
		int x;
		int y;
		int lengthArray = 0;
		Vector<Integer> maskXVec = new Vector<Integer>();
		Vector<Integer> maskYVec = new Vector<Integer>();
		while (true){ 
			s = fileReader.readLine();
			if (s == null){
				break;
			}
			x = Integer.parseInt(s.substring(0,s.indexOf(' ')));
			y = Integer.parseInt(s.substring(s.indexOf(' ')+1));
			maskXVec.add(x);
			maskYVec.add(y);
			lengthArray++;
		}
		maskX = new int[lengthArray];
		maskY = new int[lengthArray];
		for (int i=0;i<maskX.length;i++){
			maskX[i] = maskXVec.get(i);
			maskY[i] = maskYVec.get(i);
		}
	}
	
	//установка кор маски относительно центра танка
	public void center(){
		for (int i=0;i<maskX.length;i++){
			maskX[i] -= this.width/2;
			maskY[i] -= this.height/2;
		}
	}
	
	//Проверка: нужно ли делать с этим объектом проверку столкновений
	public void collCheck(double x, double y, double directionDraw, String[] collObj, Obj obj){
		ObjLight objGlobal; //Объект из глобального массива
		
		for(int vectorON=0;vectorON<Global.getSize();vectorON++){			//Vector Object Number - цикл перебора объектов в глобале
			objGlobal = Global.getObj(vectorON);
			
			if ((objGlobal != null) && (!objGlobal.isLight())){
				for (int stringON=0;stringON<collObj.length;stringON++){ 	//String Object Number - цикл перебора объектов во входном массиве
					if ((objGlobal.getClass().getName().equals(collObj[stringON])) && (collCheckConti(x, y, directionDraw, (Obj) objGlobal))){
						obj.collReport((Obj) objGlobal);
					}
				}
			}
		}
	}
	
	//Проверка столкновения
	public boolean collCheckConti(double x, double y, double directionDraw, Obj obj){
		//Проверка расстояния до объекта столкновения
		double xCenter = x + this.width/2; //смещение начало кор в центр объекта
		double yCenter = y + this.height/2;
		double gipMe = Math.sqrt(sqr(height) + sqr(width)); //Гипотенуза объекта
		double gipOther = Math.sqrt(sqr(obj.mask.height) + sqr(obj.mask.width)); //Гипотинуза объекта, с которым сравниваем
		double disMeToOther = Math.sqrt(sqr(xCenter-obj.getX())+sqr(yCenter-obj.getY())); //Расстояние от центра до центра
		
		if (disMeToOther > gipMe/2 + gipOther/2 + 30){//Если до объекта слишком далеко
			return false;
		} else {
			if ((dynamic) && (!calcInThisStep)){
				calc(x, y, directionDraw);
				calcInThisStep = true;
			}
		}
		
		//Просчёт столкновения
		Polygon pMe = new Polygon(maskXDraw,maskYDraw,maskXDraw.length);
		Polygon pOther = new Polygon(obj.mask.maskXDraw,obj.mask.maskYDraw,obj.mask.maskXDraw.length);
		for (int i = 0;i<obj.mask.maskXDraw.length;i++){
			Point point = new Point(obj.mask.maskXDraw[i],obj.mask.maskYDraw[i]);
			if (pMe.contains(point)){
				return true;
			}
		}
		for (int i = 0;i<maskXDraw.length;i++){
			Point point = new Point(maskXDraw[i],maskYDraw[i]);
			if (pOther.contains(point)){
				return true;
			}
		}
		return false;
	}
	
	public int sqr(int x){
		return x*x;
	}
	
	public double sqr(double x){
		return x*x;
	}
	
	//Расчёт маски относительно начало координат
	public void calc(double x, double y, double direction){
		direction = Math.toRadians(direction)-Math.PI/2;//смещена начального угла с Востока на Север
			
		//просчёт маски
		this.maskXDraw = new int[maskX.length];
		this.maskYDraw = new int[maskX.length];
		double XDouble;
		double YDouble; 
		double XDouble2;
		double YDouble2; 
		double cos = Math.cos(direction);
		double sin = Math.sin(direction);
		for (int i=0;i<maskX.length;i++){
			XDouble = cos * maskX[i];//Первый отступ
			YDouble = sin * maskX[i];//"Вперёд"
			XDouble2 = sin * maskY[i];//Второй отступ //Math.cos(direction-Math.PI/2) * ...
			YDouble2 = -cos * maskY[i];//"В бок" //Math.sin(direction-Math.PI/2) * ...
			this.maskXDraw[i] = (int) (x + XDouble + XDouble2);
			this.maskYDraw[i] = (int) (y - YDouble - YDouble2);
		}
	}
	
	public void draw(){
		int maskXDrawView[] = new int[maskXDraw.length];
		int maskYDrawView[] = new int[maskYDraw.length];
		double xd,yd;
		for (int i=0;i<maskXDraw.length;i++){
			xd = Global.cameraXView - (Global.cameraX - maskXDraw[i]);
			yd = Global.cameraYView - (Global.cameraY - maskYDraw[i]);
			maskXDrawView[i] = (int) xd;
			maskYDrawView[i] = (int) yd;
		}
		
		GL11.glLoadIdentity();     
	    GL11.glTranslatef(0, 0, 0);
	    GL11.glColor3d(0.0, 0.0, 1.0);
	    
	    GL11.glDisable(GL11.GL_TEXTURE_2D);
	    
	    GL11.glBegin(GL11.GL_LINE_LOOP);
	    	for (int i=0; i<maskXDrawView.length;i++)
	    		GL11.glVertex2f(maskXDrawView[i], maskYDrawView[i]); 
	    GL11.glEnd();
	    
		if (bullet){
			int x = (int) (Global.cameraXView - (Global.cameraX - (collX-10)));
			int y = (int) (Global.cameraYView - (Global.cameraY - (collY-10)));
			int w = 20;
			int h = 20;
			GL11.glBegin(GL11.GL_QUADS);
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
		
	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public void thisBullet(int range, String[] collObj, double x, double y, double directionDraw){
		bullet = true;
		dynamicId = new ArrayList<Integer>();
		findDynamicIdAndStaticColl(collObj, x, y, directionDraw, range);
	}
	
	//Поиск в общем массиве id, которые динамичны и сталкиваются с этим объектом
	//Также поиск статичных объектов для проверки столкновения при помощи траектории
	private void findDynamicIdAndStaticColl(String[] collObj, double x, double y, double directionDraw, int range){
		int start = this.start;
		ObjLight objGlobalLight;
		
		for(int vectorON=start;vectorON<Global.getSize();vectorON++){//Vector Object Number - цикл перебора объектов в глобале
			objGlobalLight = Global.getObj(vectorON);
			
			if ((objGlobalLight != null) && (!objGlobalLight.isLight())){
				Obj objGlobal = (Obj) objGlobalLight;
				if (objGlobal.mask.dynamic){//Если объект из глобала динамичен
					
					for (int stringON=0;stringON<collObj.length;stringON++){ 	//String Object Number - цикл перебора объектов во входном массиве
						if ((objGlobal.getClass().getName().equals(collObj[stringON]))){
							dynamicId.add(vectorON);
						}
					}
					
				} else {//Если объект из глобала статичен
					
					for (int stringON=0;stringON<collObj.length;stringON++){ 	//String Object Number - цикл перебора объектов во входном массиве
						if ((objGlobal.getClass().getName().equals(collObj[stringON]))){
							bulletColl(x, y, directionDraw, objGlobal, range);
						}
					}
					
				}
			}
		}
		this.start = Global.getSize();
	}
	
	//расчёт столкновения по прямой траетории
	private void bulletColl(double startX, double startY, double directionDraw, Obj obj, int range){
		double xCenter = startX + this.width/2; //смещение начало кор в центр объекта
		double yCenter = startY + this.height/2;
		double gipMe = Math.sqrt(sqr(height) + sqr(width)); //Гипотенуза объекта
		double gipOther = Math.sqrt(sqr(obj.getImage().getHeight()) + sqr(obj.getImage().getWidth())); //Гипотинуза объекта, с которым сравниваем
		double disMeToOther = Math.sqrt(sqr(xCenter-obj.getX()) + sqr(yCenter-obj.getY())); //Расстояние от центра до центра
		
		if (disMeToOther < gipOther/2+gipMe+30){//Если пушка находится внутри дома
			bullet = false;
		} else if (disMeToOther < range+gipOther/2+gipMe/2+30){
			double k, b, x0, y0, r;
			if ((directionDraw == 90.0) || (directionDraw == 270.0) || (directionDraw == 0.0) || (directionDraw == 180.0)){//КОСТЫЛЬ, ЛЮТЫЙ КОСТЫЛЬ
				directionDraw += Math.pow(0.1, 10);
			}
			k = Math.tan(Math.toRadians(directionDraw));
			b = -(startY+k*startX);
			x0 = obj.getX();
			y0 = -obj.getY();
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
				if ((this.collX != -1) && (this.collY != -1)){
					double collOld = Math.sqrt(sqr(this.collX-startX) + sqr(this.collY-startY));
					double collNew = Math.sqrt(sqr(collX-startX) + sqr(-collY-startY));//-collY перевод из мат. сис. кор в игровую
					if (collNew < collOld){
						this.collX = collX;
						this.collY = -collY;
					}
				} else {
					this.collX = collX;
					this.collY = -collY;//Перевод из математических кординат в игровые
				}
				
			}
		}
	}
	
	public void collCheckBullet(double x, double y, double directionDraw, String[] collObj, Obj obj){
		double range = ((Bullet) obj).getRange();
		findDynamicIdAndStaticColl(collObj, x, y, directionDraw, (int) range);
		
		Obj objGlobal;//С которым сталкиваемся
		for (int i=0; i<dynamicId.size(); i++){
			objGlobal = (Obj) Global.getObj(dynamicId.get(i));
			if ((objGlobal != null) && (collCheckConti(x, y, directionDraw, objGlobal))){
				obj.collReport(objGlobal);;
			}
		}
		
		if (Math.sqrt(sqr(x-this.collX) + sqr(y-this.collY)) <= obj.getSpeed()*3){//Если максимальная близость к точке пересечения, то столкновение
			bullet = false;
		}
	}
}