package main.map;

import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Global;
import main.obj.ObjLight;

public class Chunk {
	
	private ArrayList<Integer> number = new ArrayList<Integer>();
	private int posWidth;//Порядковый номер чанка
	private int posHeight;
	
	public Chunk(int posHeight, int posWidth){
		this.posHeight = posHeight;
		this.posWidth = posWidth;
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
	
	public void render(Graphics2D g){
		for (int j=0; j<number.size(); j++){
			if (Global.getObj(number.get(j)) != null){
				ObjLight obj = (ObjLight) Global.getObj((long) number.get(j));
				obj.draw(g);
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
