package main.player.armor;

import main.player.*;
import main.*;

public class DefaultArmor extends Armor {
	
	private final double HP_MAX = 20.0;
	private final double HP_REGEN = 0.5/Global.setting.TPS;
	private final double SPEED_TANK_UP = 0.8;
	private final double SPEED_TANK_DOWN = -0.4;
	private final double DIRECTION_GUN_UP = 0.7;
	private final double DIRECTION_TANK_UP = 1.3;
	private final int ANIM_SPEED = 10;
	
	public Player player;
	
	public DefaultArmor(Player player){
		super(player,Global.c_default);
		this.player = player;
		
		setHpMax(HP_MAX);
		setHp(HP_MAX);
		setHpRegen(HP_REGEN);
		setSpeedTankUp(SPEED_TANK_UP);
		setSpeedTankDown(SPEED_TANK_DOWN);
		setDirectionTankUp(DIRECTION_TANK_UP);
		setDirectionGunUp(DIRECTION_GUN_UP);
		setAnimSpeed(ANIM_SPEED);
	}
}
