package tow.player.gun;

import tow.*;
import tow.player.*;

public class DefaultGun extends Gun {
	
	public DefaultGun(Player player){
		super(player, Global.defaultgun);
	}
}