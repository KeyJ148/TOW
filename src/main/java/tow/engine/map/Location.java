package tow.engine.map;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import tow.engine.Global;
import tow.engine.input.keyboard.KeyboardHandler;
import tow.engine.input.mouse.MouseHandler;
import tow.engine.obj.Obj;
import tow.engine.obj.components.Position;

import java.util.Vector;

public class Location {

	public final int width, height;
	public Background background = new Background(); //Фон карты (цвет и текстура)
	public Camera camera = new Camera(); //Положение камеры в этой локации

	//TODO: Сделать приватными и immutable getter / iterator
	public Vector<Obj> objects = new Vector<>(); //Массив со всеми объектами
	public MapControl mapControl; //Массив со всеми чанками и объектами

	private Frame guiFrame; //Объект хранящий все элементы gui в данной комнате
	private KeyboardHandler keyboard; //Объект хранящий события клавитуры
	private MouseHandler mouse; //Объект хранящий события мыши и рисующий курсор на экране

	public Location(int width, int height) {
		this.width = width;
		this.height = height;
		mapControl = new MapControl(width, height);

		guiFrame = Global.engine.gui.createFrame();
	}

	public void update(long delta){
		camera.update();//Расчёт положения камеры

		for (int i=0; i<objects.size(); i++){
			Obj obj = objects.get(i);
			if (obj != null) obj.update(delta);
		}
	}

	//Отрисовка комнаты с размерами width и height вокруг камеры
	public void render(int width, int height){
		render((int) camera.getX(), (int) camera.getY(), width, height);
	}

	//Отрисовка комнаты с размерами width и height вокруг координат (x;y)
	public void render(int x, int y, int width, int height){
		background.render(x, y, width, height, camera);
		mapControl.render(x, y, width, height);
	}

	public int objCount(){
		int count = 0;
		for (Obj obj : objects)
			if (obj != null)
				count++;

		return count;
	}

	//Добавление объекта в комнату
	public void objAdd(Obj obj){
		if (obj.isDestroy()) throw new IllegalArgumentException("Obj was destroy");

		obj.getComponent(Position.class).id = objects.size();
		obj.getComponent(Position.class).location = this;

		objects.add(obj);
		mapControl.add(obj);
	}

	//Удаление объекта из комнаты по id
	public void objDel(int id){
		mapControl.del(id);//Используется objects, так что должно быть раньше
		objects.set(id, null);
	}

	public void activate(){
		activate(true);
	}

	//Сделать комнату активной (update и render), одновременно может быть максимум одна активная комната
	public void activate(boolean saveInput){
		//Перенести нажатые клавиши и настройки мыши/курсора или нет
		if (saveInput){
			keyboard = new KeyboardHandler(guiFrame, Global.location.getKeyboard());
			mouse = new MouseHandler(guiFrame, Global.location.getMouse());
		} else {
			keyboard = new KeyboardHandler(guiFrame);
			mouse = new MouseHandler(guiFrame);
		}

		Global.location = this;
		Global.engine.gui.setFrameFocused(guiFrame);
	}

	//Удаление всех объектов в комнате
	public void destroy() {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i) != null) objects.get(i).destroy();
		}
	}

	public Frame getGuiFrame() {
		return guiFrame;
	}


	public KeyboardHandler getKeyboard() {
		return keyboard;
	}

	public MouseHandler getMouse() {
		return mouse;
	}

	//Добавление GUI элемента в комнату
	public void addGUIComponent(Component component){
		guiFrame.getContainer().add(component);
	}

	//Удаление GUI элемента из комнаты
	public void removeGUIComponent(Component component){
		guiFrame.getContainer().remove(component);
	}

}
