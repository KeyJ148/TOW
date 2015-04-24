package main;

import java.util.Vector;

public class DepthVector {
	
	public int depth;
	public Vector<Integer> number; //int: все id объектов с глубиной depth
	
	public DepthVector(int depth, int id){
		this.number = new Vector<Integer>();
		this.depth = depth;
		this.number.add(id);
	}
	
	public DepthVector(int depth){
		this.number = new Vector<Integer>();
		this.depth = depth;
	}
	
	public void add(int id){
		this.number.add(id);
	}
	
	public void delete(int id){
		for (int i=0; i<this.number.size(); i++){
			int a = (int) this.number.get(i);
			if (a == id){
				this.number.remove(i);
				break;
			}
		}
	}
}

