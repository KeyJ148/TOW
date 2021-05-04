package tow.game.client.map.objects;


import tow.engine.Global;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Collision;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.SpriteRender;
import tow.engine.resources.sprites.Sprite;
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

		Sprite sprite = Global.spriteStorage.getSprite(nameBox);
		setComponent(new Position(x, y, 1000));
		setComponent(new SpriteRender(sprite.getTexture()));
		setComponent(new Collision(sprite.getMask()));
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
