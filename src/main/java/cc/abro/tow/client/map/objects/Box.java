package cc.abro.tow.client.map.objects;


import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.audios.AudioService;
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

		String nameBox = switch (typeBox) {
			case 0 -> "box_armor";
			case 1 -> "box_gun";
			case 2 -> "box_bullet";
			case 3 -> "box_health";
			case 4 -> "box_healthfull";
			default -> "error";
		};

		Sprite sprite = Context.getService(SpriteStorage.class).getSprite(nameBox);
		setComponent(new Position(x, y, 1000));
		setComponent(new SpriteRender(sprite.texture()));
		setComponent(new Collision(sprite.mask()));
	}

	public void collisionPlayer(Player player) {
		destroy();

		String soundName = "";
		switch (typeBox) {
			case 0 -> {
				if (player.takeArmor) EquipManager.newArmor(player);
				soundName = "armor";
			}
			case 1 -> {
				if (player.takeGun) EquipManager.newGun(player);
				soundName = "gun";
			}
			case 2 -> {
				if (player.takeBullet) EquipManager.newBullet(player);
				soundName = "bullet";
			}
			case 3 -> {
				if (player.takeHealth) player.hp = (long) (player.hp + player.stats.hpMax * 0.4);
				soundName = "heal";
			}
			case 4 -> {
				if (player.takeHealth) player.hp = (long) player.stats.hpMax;
				soundName = "heal";
			}
		}

		Context.getService(TCPControl.class).send(21, String.valueOf(idBox));

		Context.getService(AudioService.class).playSoundEffect(Context.getService(AudioStorage.class).getAudio(soundName), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
		Context.getService(TCPControl.class).send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + soundName);
	}
}
