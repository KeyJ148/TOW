package tech.abro.orchengine.map;

import tech.abro.orchengine.Global;
import tech.abro.orchengine.gameobject.GameObject;

import java.util.ArrayList;

public class Chunk {

	private ArrayList<Integer> number = new ArrayList<Integer>();//Id объектов которые надо отрисовать
	private int posWidth;//Порядковый номер чанка
	private int posHeight;

	public Chunk(int posWidth, int posHeight){
		this.posWidth = posWidth;
		this.posHeight = posHeight;
	}

	public void add(int id){
		number.add(id);
	}

	public int get(int index){
		return number.get(index);
	}

	public void del(int id){
		for (int j=0; j<number.size(); j++){
			if (number.get(j) == id){
				number.remove(j);
				break;
			}
		}
	}

	public int size(){
		return number.size();
	}

	public void render(){
		for (int id : number){
			if (Global.location.objects.size() > id && Global.location.objects.get(id) != null){
				GameObject gameObject = Global.location.objects.get(id);
				gameObject.draw();
			}
		}
	}

	public int getPosHeight(){
		return posHeight;
	}

	public int getPosWidth(){
		return posWidth;
	}
}
