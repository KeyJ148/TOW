package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Mask implements Cloneable{
	
	public int[] maskX; //маска относительно центра такнка
	public int[] maskY;
	public int[] maskXDraw; //маска относительно начала координат
	public int[] maskYDraw;
	public int width; //ширина и высота спрайта
	public int height;
	
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
				center();
				if (Global.setting.DEBUG_CONSOLE_MASK) System.out.println("Load mask \"" + path + "\" complited.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { //создание маски при отсутствие файла
			maskX = new int[] {0,this.width-1,this.width-1,0};
			maskY = new int[] {0,0,this.height-1,this.height-1};
			center();
			/* тестовая НЕпрямоугольная
			maskX = new int[] {-5,getWidth()+5,getWidth()+5,getWidth()/2,-5};
			maskY = new int[] {-5,-5,getHeight()+5,getHeight()+10,getHeight()+5};
			*//* тестовая прямоугольная
			maskX = new int[] {-5,getWidth()+5,getWidth()+5,-5};
			maskY = new int[] {-5,-5,getHeight()+5,getHeight()+5};
			*/
		}
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
	
	public void collCheck(String[] collObj, Obj obj){
		for(int vectorON=0;vectorON<Global.obj.size();vectorON++){			//Vector Object Number - цикл перебора объектов в глобале
			for (int stringON=0;stringON<collObj.length;stringON++){ 	//String Object Number - цикл перебора объектов во входном массиве
				if (Global.obj.get(vectorON) != null){
					if (Global.obj.get(vectorON).getClass().getName().equals(collObj[stringON])){
						if (collCheckConti(Global.obj.get(vectorON))){
							obj.collReport(Global.obj.get(vectorON));
						}
					}
				}
			}
		}
	}
	
	public boolean collCheckConti(Obj obj){
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
	
	public void calc(double x, double y, double direction){
		direction = Math.toRadians(direction)-Math.PI/2;//смещена начального угла с Востока на Север
		x+=this.width/2; //смещение начало кор в центр объекта
		y+=this.height/2; //так же необходимо для просчёта маски
			
		//просчёт маски
		this.maskXDraw = new int[maskX.length];
		this.maskYDraw = new int[maskX.length];
		double XDouble;
		double YDouble; 
		double XDouble2;
		double YDouble2; 
		double maskXDrawDbl;
		double maskYDrawDbl;
		for (int i=0;i<maskX.length;i++){
			XDouble = Math.cos(direction) * maskX[i];//Первый отступ
			YDouble = Math.sin(direction) * maskX[i];//"Вперёд"
			XDouble2 = Math.cos(direction-Math.PI/2) * maskY[i];//Второй отступ
			YDouble2 = Math.sin(direction-Math.PI/2) * maskY[i];//"В бок"
			maskXDrawDbl = x + XDouble + XDouble2;
			maskYDrawDbl = y - YDouble - YDouble2;
			this.maskXDraw[i] = (int) maskXDrawDbl;
			this.maskYDraw[i] = (int) maskYDrawDbl;
		}
	}
	
	public void draw(Graphics2D g){
		AffineTransform at = new AffineTransform(); 
		g.setTransform(at);
		g.setColor(new Color(0,0,255));
		int maskXDrawView[] = new int[maskXDraw.length];
		int maskYDrawView[] = new int[maskYDraw.length];
		double xd,yd;
		for (int i=0;i<maskXDraw.length;i++){
			xd = Global.cameraXView - (Global.cameraX - maskXDraw[i]);
			yd = Global.cameraYView - (Global.cameraY - maskYDraw[i]);
			maskXDrawView[i] = (int) xd;
			maskYDrawView[i] = (int) yd;
		}
		g.drawPolygon(maskXDrawView,maskYDrawView,maskXDrawView.length);
	}
}