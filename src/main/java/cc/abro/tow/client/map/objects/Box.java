package cc.abro.tow.client.map.objects;


import cc.abro.orchengine.Manager;
import cc.abro.orchengine.audio.AudioPlayer;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.audios.AudioStorage;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.tanks.equipment.EquipManager;
import cc.abro.tow.client.tanks.player.Player;

public class Box extends GameObject {

	public int idBox;
	public int typeBox;

	public Box(double x, double y, int typeBox, int idBox) {
		this.idBox = idBox;
		this.typeBox = typeBox;

		String nameBox;
		switch (typeBox) {
			case 0:
				nameBox = "box_armor";
				break;
			case 1:
				nameBox = "box_gun";
				break;
			case 2:
				nameBox = "box_bullet";
				break;
			case 3:
				nameBox = "box_health";
				break;
			case 4:
				nameBox = "box_healthfull";
				break;
			default:
				nameBox = "error";
				break;
		}

		Sprite sprite = Manager.getService(SpriteStorage.class).getSprite(nameBox);
		setComponent(new Position(x, y, 1000));
		setComponent(new SpriteRender(sprite.getTexture()));
		setComponent(new Collision(sprite.getMask()));
	}

	public void collisionPlayer(Player player) {
		destroy();

		String soundName = "";
		switch (typeBox) {
			case 0:
				if (player.takeArmor) EquipManager.newArmor(player);
				soundName = "armor";
				break;
			case 1:
				if (player.takeGun) EquipManager.newGun(player);
				soundName = "gun";
				break;
			case 2:
				if (player.takeBullet) EquipManager.newBullet(player);
				soundName = "bullet";
				break;
			case 3:
				if (player.takeHealth) player.hp = player.hp + player.stats.hpMax * 0.4;
				soundName = "heal";
				break;
			case 4:
				if (player.takeHealth) player.hp = player.stats.hpMax;
				soundName = "heal";
				break;
		}

		Manager.getService(TCPControl.class).send(21, String.valueOf(idBox));

		Manager.getService(AudioPlayer.class).playSoundEffect(Manager.getService(AudioStorage.class).getAudio(soundName), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
		Manager.getService(TCPControl.class).send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + soundName);
	}
}
