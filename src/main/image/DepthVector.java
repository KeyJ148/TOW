package main.image;

import java.util.ArrayList;

public class DepthVector {
	
	public int depth;
	public ArrayList<Long> number; //int: все id объектов с глубиной depth
	
	public DepthVector(int depth, long id){
		this.number = new ArrayList<Long>();
		this.depth = depth;
		this.number.add(id);
	}
	
	public DepthVector(int depth){
		this.number = new ArrayList<Long>();
		this.depth = depth;
	}
	
	public void add(long id){
		this.number.add(id);
	}
	
	public void delete(long id){
		this.number.remove(id);
	}
}

