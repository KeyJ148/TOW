package tow.game.client;

import tow.engine.Global;
import tow.engine.image.Color;
import tow.engine.image.TextureManager;
import tow.engine.implementation.GameInterface;
import tow.game.client.menu.MainMenuLocation;

import java.util.Random;

public class Game implements GameInterface {

	@Override
	public void init() {
		GameSetting.init();

        //new LoginWindow();

		ClientData.name = "Nick";
		Random rand = new Random();
		ClientData.color = new Color(rand.nextInt(150)+100, rand.nextInt(150)+100, rand.nextInt(150)+100);
		//ServerLoader.mapPath = "maps/town100k.maptest";


		Global.location.getMouse().getCursor().setCapture(true);
		Global.location.getMouse().getCursor().setTexture(TextureManager.getTexture("cursor_aim_1"));

        new MainMenuLocation();
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }
}
