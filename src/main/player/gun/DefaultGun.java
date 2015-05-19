package main.player.gun;

import main.player.*;
import main.*;

public class DefaultGun extends Gun {
	
	public DefaultGun(Player player){
		super(player, Global.defaultgun);
	}
}