package game.client.person.equipment.gun;

import engine.image.TextureManager;
import game.client.person.player.Gun;

public class GOsmos extends Gun {

	public GOsmos(){
		super(TextureManager.getTexture("g_osmos"));
	}

	@Override
	public void attack2(){
		//Т.к. ствол второй атаки расположен в обратную сторону, то на момент выстрела подменяем направление пушки
		position.setDirectionDraw(position.getDirectionDraw() + 180);

		//Совершаем выстрел
		super.attack2();

		//И возвращаем обратно
		position.setDirectionDraw(position.getDirectionDraw() - 180);

	}
}