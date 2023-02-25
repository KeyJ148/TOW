package cc.abro.tow.client.map.objects;


import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.tanks.equipment.EquipManager;
import cc.abro.tow.client.tanks.player.Player;

public class Box extends GameObject {

	public int idBox;
	public int typeBox;

	public Box(Location location, double x, double y, int typeBox, int idBox) {
		super(location);
		this.idBox = idBox;
		this.typeBox = typeBox;
		setPosition(x, y);

		String nameBox = switch (typeBox) {
			case 0 -> "box_armor";
			case 1 -> "box_gun";
			case 2 -> "box_bullet";
			case 3 -> "box_health";
			case 4 -> "box_healthfull";
			default -> "error";
		};

		Sprite sprite = getSpriteStorage().getSprite(nameBox);
		addComponent(new SpriteRender(sprite.texture(), 1000));
		addComponent(new Collision(sprite.mask()));
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

		getAudioService().playSoundEffect(getAudioStorage().getAudio(soundName), (int) getX(), (int) getY(), GameSetting.SOUND_RANGE);
		Context.getService(TCPControl.class).send(25, (int) getX() + " " + (int) getY() + " " + soundName);
	}
}
