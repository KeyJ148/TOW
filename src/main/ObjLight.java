package main;

public class ObjLight {
	
	public double x;
	public double y;
	public double xView = 0; //для движения камеры
	public double yView = 0; //коры объекта в окне
	
	public int depth;

	public double directionDraw; //0, 360 - в право, против часовой - отрисовка
	
	private long id; //уникальный номер
	
	protected void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

}
