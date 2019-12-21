package tow.engine.map;

import tow.engine2.Vector2;
import tow.engine.obj.Obj;

import java.util.ArrayList;

public class DepthVector {

	private int depth;
	private MapControl mc;
	private ArrayList<ArrayList<Chunk>> chunks = new ArrayList<>();
	//Двумерный динамический массив хранит все Чанки
	//Внешний массив хранит сортировку массивов по координате y
	//Внутренний массив имеет чанки с одинаковой y, но разными x

	public DepthVector(MapControl mc, int depth, Obj obj){
		this.mc = mc;
		this.depth = depth;

		add(obj);
	}

	public void add(Obj obj){
		getChunk((int) obj.position.x, (int) obj.position.y).add(obj.position.id);
	}

	public void del(Obj obj){
		getChunk((int) obj.position.x, (int) obj.position.y).del(obj.position.id);
	}

	public int getDepth(){
		return depth;
	}

	//Обновление объекта при перемещение из чанка в чанк
	public void update(Obj obj){
		if (obj.movement == null) return;

		Chunk chunkNow = getChunk((int) obj.position.x,(int) obj.position.y);
		Chunk chunkLast = getChunk((int) obj.movement.getXPrevious(),(int) obj.movement.getYPrevious());

		if (!chunkLast.equals(chunkNow)){
			chunkLast.del(obj.position.id);
			chunkNow.add(obj.position.id);
		}
	}

	//Отрисовка чанков вокруг позиции x, y
	public void render(int x, int y, int width, int height) {
		Chunk chunk = getChunk(x, y);
		int chunkPosX = chunk.getPosWidth();
		int chunkPosY = chunk.getPosHeight();
		int rangeX = (int) Math.ceil((double) width / 2 / mc.chunkSize);//В чанках
		int rangeY = (int) Math.ceil((double) height / 2 / mc.chunkSize);//В чанках

		for (int i = chunkPosX - rangeX; i <= chunkPosX + rangeX; i++){
			for (int j = chunkPosY - rangeY; j <= chunkPosY + rangeY; j++) {
				if ((i >= 0) && (i < mc.numberWidth) && (j >= 0) && (j < mc.numberHeight)) {
					mc.chunkRender++;
					pointToChunk(new Vector2<>(i, j)).render();
				}
			}
		}
	}

	private Chunk getChunk(int x, int y){
		return pointToChunk(getPoint(x, y));
	}

	//Возвращает чанк из позиции чанка по x,y (Не координаты, а позиции чанка)
	private Chunk pointToChunk(Vector2<Integer> p){
		//Оптимальный бинарный поиск массива (внешнего), где Y равен входному Y
		int key = p.y;
		int left = 0;
		int right = chunks.size()-1;//После поиска здесь будет позиция найденного элемента

		while (left < right){
			int mid = (left+right)/2;
			if (key > chunks.get(mid).get(0).getPosHeight()){
				left = mid+1;
			} else {
				right = mid;
			}
		}
		int indexOnY = right;//Индекс нужного нам внутреннего массива во внешнем массиве

		//Если нужный массив не был найден, то создаем его
		//y = indexOnY, x = 0, Получить позицию по y
		if (chunks.size() == 0 || key != chunks.get(indexOnY).get(0).getPosHeight()){
			Chunk newChunk = new Chunk(p.x, p.y);
			ArrayList<Chunk> chunksOnX = new ArrayList<>();
			chunksOnX.add(newChunk);

			//Вставляем во внешнией массив внутренний массив в соответствие с сортировкой
			if (chunks.size() == 0){
				chunks.add(chunksOnX);
			} else {
				if (chunks.get(indexOnY).get(0).getPosHeight() < key) chunks.add(indexOnY+1, chunksOnX);
				else chunks.add(indexOnY, chunksOnX);
			}

			return newChunk;
		}

		//Оптимальный бинарный поиск массива (внутреннего) по внешнему массиву с индексом Mid, где X равен входному X
		key = p.x;
		left = 0;
		right = chunks.get(indexOnY).size()-1;//После поиска здесь будет позиция найденного элемента

		while (left < right){
			int mid = (left+right)/2;
			if (key > chunks.get(indexOnY).get(mid).getPosWidth()){
				left = mid+1;
			} else {
				right = mid;
			}
		}
		int indexOnX = right;//Индекс нужного нам чанка во внутреннем массиве

		//Если нужный внутренний массив не был найден, то создаем его
		//y = indexOnY, x = indexOnX, Получить позицию по x
		if (chunks.get(indexOnY).size() == 0 || key != chunks.get(indexOnY).get(indexOnX).getPosWidth()){
			Chunk newChunk = new Chunk(p.x, p.y);

			//Вставляем во внешнией массив внутренний массив в соответствие с сортировкой
			if (chunks.get(indexOnY).size() == 0){
				chunks.get(indexOnY).add(newChunk);
			} else{
				if (chunks.get(indexOnY).get(indexOnX).getPosWidth() < key) chunks.get(indexOnY).add(indexOnX+1, newChunk);
				else chunks.get(indexOnY).add(indexOnX, newChunk);
			}

			return newChunk;
		}

		return chunks.get(indexOnY).get(indexOnX);
	}

	private Vector2<Integer> getPoint(int x, int y){
		int delta = mc.borderSize/mc.chunkSize-1;//delta=0 (1-1)
		int posWidth = (int) Math.ceil((double) x/mc.chunkSize+delta);//-1 т.к. нумерация в массиве с 0
		int posHeight = (int) Math.ceil((double) y/mc.chunkSize+delta);//+1 т.к. добавлена обводка карты толщиной в 1 чанк для обработки выхода за карту
		return new Vector2<Integer>(posWidth, posHeight);
	}
}

