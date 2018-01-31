package game.client.map;


import engine.AudioStorage;
import engine.Global;
import engine.image.TextureHandler;
import engine.image.TextureManager;
import engine.obj.Obj;
import engine.obj.components.Collision;
import engine.obj.components.Position;
import engine.obj.components.render.Sprite;
import game.client.GameSetting;
import game.client.tanks.equipment.EquipManager;
import game.client.tanks.player.Player;

public class Box extends Obj {
	
	public int idBox;
	public int typeBox;
	
	public Box(double x, double y, int typeBox, int idBox) {
		this.idBox = idBox;
		this.typeBox = typeBox;

		String nameBox;
		switch (typeBox){
			case 0: nameBox = "box_armor"; break;
			case 1: nameBox = "box_gun"; break;
			case 2: nameBox = "box_bullet"; break;
			case 3: nameBox = "box_health"; break;
			case 4: nameBox = "box_healthfull"; break;
			default: nameBox = "error"; break;
		}

		TextureHandler texture = TextureManager.getTexture(nameBox);
		position = new Position(this, x, y, texture.depth);
		rendering = new Sprite(this, texture);
		collision = new Collision(this, texture.mask);
	}

	public void collisionPlayer(Player player){
		destroy();

        String soundName = "";
        switch (typeBox){
			case 0: if (player.takeArmor) EquipManager.newArmor(player); soundName = "armor"; break;
			case 1: if (player.takeGun) EquipManager.newGun(player); soundName = "gun"; break;
			case 2: if (player.takeBullet) EquipManager.newBullet(player); soundName = "bullet"; break;
			case 3: if (player.takeHealth) player.hp = player.hp + player.stats.hpMax*0.4; soundName = "heal"; break;
			case 4: if (player.takeHealth) player.hp = player.stats.hpMax; soundName = "heal"; break;
		}

		Global.tcpControl.send(21, String.valueOf(idBox));

		AudioStorage.playSoundEffect(soundName, (int) position.x, (int) position.y, GameSetting.SOUND_RANGE);
		Global.tcpControl.send(25, (int) position.x + " " + (int) position.y + " " + soundName);
	}
}
