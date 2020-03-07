package tow.engine.map;

import tow.engine.Global;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;

import java.util.ArrayList;

public class MapControl {

	private ArrayList<DepthVector> depthVectors = new ArrayList<DepthVector>(); //Массив с DepthVector хранящий чанки
	public int numberWidth;//Кол-во чанков
	public int numberHeight;
	public final int chunkSize = 100;
	public final int borderSize = 100;
	public int chunkRender = 0;//Кол-во отрисованных чанков

	public MapControl(int width, int height){
		int addCell = (int) Math.ceil((double) borderSize/chunkSize)*2;//С двух сторон для каждой оси
		numberWidth = (int) (Math.ceil((double) width/chunkSize)+addCell);//+2 чанка минимум потому что надо обрабатывать вылет за карту
		numberHeight = (int) (Math.ceil((double) height/chunkSize)+addCell);//Желательно по 100-200 px на каждую сторону (Иначе на большой скорости можно пролететь границу)
	}

	public void add(GameObject gameObject){
		int depth = gameObject.getComponent(Position.class).depth;

		boolean find = false;
		for (int i=0; i<depthVectors.size(); i++){
			DepthVector dv = depthVectors.get(i);
			if (dv.getDepth() == depth){
				dv.add(gameObject);
				find = true;
				break;
			}
		}

		if (!find){//Массив отсортирован в порядке убывания глубины
			boolean create = false;
			for (int i=0; i<depthVectors.size(); i++){
				if (depthVectors.get(i).getDepth() < depth){
					depthVectors.add(i, new DepthVector(this, depth, gameObject));
					create = true;
					break;
				}
			}

			if (!create){
				depthVectors.add(new DepthVector(this, depth, gameObject));
			}
		}
	}

	public void del(int id){
		GameObject gameObject = Global.location.objects.get(id);
		int depth = gameObject.getComponent(Position.class).depth;

		DepthVector dv;
		for (int i=0; i<depthVectors.size(); i++){
			dv =  depthVectors.get(i);
			if (dv.getDepth() == depth){
				dv.del(gameObject);
			}
		}
	}

	public void clear(){
		depthVectors.clear();
		depthVectors.trimToSize();
	}

	public void update(GameObject gameObject){
		for (int i=0; i<depthVectors.size(); i++){
			if (depthVectors.get(i).getDepth() == gameObject.getComponent(Position.class).depth){
				depthVectors.get(i).update(gameObject);
				break;
			}
		}
	}

	public void render(int x, int y, int width, int height){
		chunkRender = 0;
		for (int i=0; i<depthVectors.size(); i++) {
			depthVectors.get(i).render(x, y, width, height);
		}
	}

	public int getCountDepthVectors(){
		return depthVectors.size();
	}
}
