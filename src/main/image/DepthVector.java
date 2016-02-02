package main.image;

import java.awt.Graphics2D;
import java.awt.Point;

import main.Global;
import main.map.Chunk;
import main.obj.Obj;
import main.obj.ObjLight;

public class DepthVector {
	
	private int depth;
	private Chunk[][] chunks;
	
	public DepthVector(int depth, ObjLight obj){
		this.depth = depth;
		chunks = new Chunk[Global.mapControl.numberWidth][Global.mapControl.numberHeight];
		
		for (int i=0; i<Global.mapControl.numberWidth; i++)
			for (int j=0; j<Global.mapControl.numberHeight; j++)
				chunks[i][j] = new Chunk(i, j);
		
		add(obj);
	}
	
	public void add(ObjLight obj){
		getChunk((int) obj.getX(), (int) obj.getY()).add((int) obj.getId());
	}
	
	public void del(ObjLight obj){
		getChunk((int) obj.getX(), (int) obj.getY()).del((int) obj.getId());
	}
	
	public int getDepth(){
		return depth;
	}
	
	public void update(Obj obj){
		Chunk chunkNow = getChunk((int) obj.getX(),(int) obj.getY());
		Chunk chunkLast = getChunk((int) obj.getXPrevious(),(int) obj.getYPrevious());
				
		if (!chunkLast.equals(chunkNow)){
			chunkLast.del((int) obj.getId());
			chunkNow.add((int) obj.getId());
		}
	}
	
	public void render(int x, int y, int width, int height, Graphics2D g){
		Chunk chunk = getChunk(x, y);
		int chunkPosX = chunk.getPosHeight();//Почему наоборот?
		int chunkPosY = chunk.getPosWidth();//Магия
		int rangeX = (int) Math.ceil((double) width/2/Global.mapControl.chunkSize);//В чанках
		int rangeY = (int) Math.ceil((double) height/2/Global.mapControl.chunkSize);//В чанках
		
		for (int i=chunkPosX-rangeX; i<=chunkPosX+rangeX; i++)
			for (int j=chunkPosY-rangeY; j<=chunkPosY+rangeY; j++)
				if ((i >= 0) && (i < Global.mapControl.numberWidth) && (j >= 0) && (j < Global.mapControl.numberHeight))
					chunks[i][j].render(g);	
	}
	
	private Chunk getChunk(int x, int y){
		return pointToChunk(getPoint(x, y));
	}
	
	private Chunk pointToChunk(Point p){
		return chunks[(int) p.getX()][(int) p.getY()];
	}
	
	private Point getPoint(int x, int y){
		int posWidth = (int) Math.ceil((double) x/Global.mapControl.chunkSize);//-1 т.к. нумерация в массиве с 0
		int posHeight = (int) Math.ceil((double) y/Global.mapControl.chunkSize);//+1 т.к. добавлена обводка карты толщиной в 1 чанк для обработки выхода за карту
		if (x == 0) posWidth = 0;
		if (y == 0) posHeight = 0;
		return new Point(posWidth, posHeight);
	}
}

