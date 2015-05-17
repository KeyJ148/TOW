package main;

public class Update {
	
	public void loop(){
		for (int i=0; i<Global.obj.size(); i++){
			if (Global.obj.get(i) != null){
				Obj obj = (Obj) Global.obj.get(i);
				obj.update();
			}
		}
		
		Obj cameraShould = (Obj) Global.player;//Объект, за которым следует камера
		if (Global.player.getDestroy()){
			for(int i = 0;i<Global.enemy.length;i++){
				if (!Global.enemy[i].getDestroy()){
					cameraShould = (Obj) Global.enemy[i];
					break;
				}
			}
		}
		
		int width = Global.setting.WIDTH_SCREEN;
		int height = Global.setting.HEIGHT_SCREEN;
		
		Global.cameraXView = width/2;
		Global.cameraYView = height/2;
				
		Global.cameraX = cameraShould.getX();
		Global.cameraY = cameraShould.getY();
				
		if (cameraShould.getX() < width/2){
			Global.cameraX = width/2;
		}
		if (cameraShould.getY() < height/2){
			Global.cameraY = height/2;
		}
		if (cameraShould.getX() > Global.widthMap-width/2){
			Global.cameraX = Global.widthMap-width/2;
		}
		if (cameraShould.getY() >Global.heightMap-height/2){
			Global.cameraY = Global.heightMap-height/2;
		}
	}
}
