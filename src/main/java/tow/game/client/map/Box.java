package tow.game.client.map;


import tow.engine.Global;
import tow.engine.image.TextureHandler;
import tow.engine.image.TextureManager;
import tow.engine.obj.GameObject;
import tow.engine.obj.components.Collision;
import tow.engine.obj.components.Position;
import tow.engine.obj.components.render.Sprite;
import tow.game.client.GameSetting;
import tow.game.client.tanks.equipment.EquipManager;
import tow.game.client.tanks.player.Player;

public class Box extends GameObject {
	
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
		setComponent(new Position(x, y, texture.depth));
		setComponent(new Sprite(texture));
		setComponent(new Collision(texture.mask));
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

		Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio(soundName), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
		Global.tcpControl.send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + soundName);
	}
}
