package main.map;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Global;
import main.image.DepthVector;
import main.obj.Obj;
import main.obj.ObjLight;

public class MapControl {

	private ArrayList<DepthVector> depthVectors = new ArrayList<DepthVector>(); //ћассив с DepthVector дл€ этого чанка
	public int numberWidth;// ол-во чанков
	public int numberHeight;
	public final int chunkSize = 100;
	private final int borderSize = 100;
	
	public void init(int width, int height){
		int addCell = (int) Math.ceil((double) borderSize/chunkSize)*2;//— двух сторон дл€ каждой оси
		numberWidth = (int) (Math.ceil((double) width/chunkSize)+addCell);//+2 чанка минимум потому что надо обрабатывать вылет за карту
		numberHeight = (int) (Math.ceil((double) height/chunkSize)+addCell);//∆елательно по 100-200 px на каждую сторону (»наче на большой скорости можно пролететь границу)
	}
	
	public void add(ObjLight obj){
		int depth = obj.getDepth();
		
		boolean find = false;
		for (int i=0; i<depthVectors.size(); i++){
			DepthVector dv = depthVectors.get(i);
			if (dv.getDepth() == depth){
				dv.add(obj);
				find = true;
				break;
			}
		}
		
		if (!find){//ћассив отсортирован в пор€дке убывани€ глубины
			boolean create = false;
			for (int i=0; i<depthVectors.size(); i++){
				if (depthVectors.get(i).getDepth() < depth){
					depthVectors.add(i, new DepthVector(depth, obj));
					create = true;
					break;
				}
			}
			
			if (!create){
				depthVectors.add(new DepthVector(depth, obj));
			}
		}
	}
	
	public void del(int id){
		ObjLight obj = Global.getObj(id);
		int depth = obj.getDepth();
		
		DepthVector dv;
		for (int i=0; i<depthVectors.size(); i++){
			dv = (DepthVector) depthVectors.get(i);
			if (dv.getDepth() == depth){
				dv.del(obj);
			}
		}
	}
	
	public void clear(){
		depthVectors.clear();
		depthVectors.trimToSize();
	}
	
	public void update(Obj obj){
		for (int i=0; i<depthVectors.size(); i++){
			if (depthVectors.get(i).getDepth() == obj.getDepth()){
				depthVectors.get(i).update(obj);
				break;
			}
		}
	}
	
	public void render(int x, int y, int width, int height, Graphics2D g){
		for (int i=0; i<depthVectors.size(); i++)
			depthVectors.get(i).render(x, y, width, height, g);
			
	}
}
