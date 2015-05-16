package main.player.gun;

import main.player.*;
import main.*;

public class DefaultGun extends Gun {
	
	private final int ATTACK_SPEED_1 = Global.setting.TPS;
	private final int ATTACK_SPEED_2 = -1;

	private final int TRUNK_1X = -2;
	private final int TRUNK_1Y = -27;
	private final int TRUNK_2X = 0;
	private final int TRUNK_2Y = 0;
	private final double DAMAGE_1 = 3.0;
	private final double DAMAGE_2 = 0;
	private final double DIRECTION_GUN_UP = 1.3;
	
	public DefaultGun(Player player, Game game){
		super(player, game, Global.defaultgun);
		setAttackSpeed1(this.ATTACK_SPEED_1);
		setAttackSpeed2(this.ATTACK_SPEED_2);
		setBullet1(player.getBullet());
		setTrunk1X(TRUNK_1X);
		setTrunk1Y(TRUNK_1Y);
		setTrunk2X(TRUNK_2X);
		setTrunk2Y(TRUNK_2Y);
		setDamage1(DAMAGE_1);
		setDamage2(DAMAGE_2);
		setDirectionGunUp(DIRECTION_GUN_UP);
	}
}