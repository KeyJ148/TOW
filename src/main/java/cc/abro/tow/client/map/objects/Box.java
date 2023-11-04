package cc.abro.tow.client.map.objects;


import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.DepthConstants;
import cc.abro.tow.client.settings.GameSettingsService;
import lombok.Getter;

public class Box extends GameObject {

	public enum Type {
		ARMOR("box_armor", "armor"),
		GUN("box_gun", "gun"),
		BULLET("box_bullet", "bullet"),
		HEALTH("box_health", "heal"),
		HEALTH_FULL("box_healthfull", "heal");

		@Getter
		private final String spriteName;
		@Getter
		private final String soundName;

		Type(String spriteName, String soundName) {
			this.spriteName = spriteName;
			this.soundName = soundName;
		}
	}

	@Getter
	private final int id;
	@Getter
	private final Type type;

	public Box(Location location, double x, double y, int typeId, int id) {
		super(location);
		this.id = id;
		this.type = Type.values()[typeId];
		setPosition(x, y);
		setDirection(90);

		Sprite sprite = getSpriteStorage().getSprite(type.spriteName);
		addComponent(new SpriteRender<>(sprite.texture(), DepthConstants.BOX_SPRITE_Z));
		addComponent(new Collision(sprite.mask(), CollidableObjectType.BOX));
	}

	public void collisionWithPlayer() {
		destroy();

		Context.getService(TCPControl.class).send(21, String.valueOf(id));

		getAudioService().playSoundEffect(getAudioStorage().getAudio(type.soundName), (int) getX(), (int) getY(),
				Context.getService(GameSettingsService.class).getGameSettings().getSoundRange());
		Context.getService(TCPControl.class).send(25, (int) getX() + " " + (int) getY() + " " + type.soundName);
	}
}
