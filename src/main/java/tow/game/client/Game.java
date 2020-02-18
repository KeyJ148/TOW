package tow.game.client;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.LayerContainer;
import org.liquidengine.legui.event.CharEvent;
import org.liquidengine.legui.event.FocusEvent;
import org.lwjgl.glfw.GLFW;
import tow.engine.Global;
import tow.engine.image.Color;
import tow.engine.image.TextureManager;
import tow.engine.implementation.GameInterface;
import tow.engine.logger.Logger;
import tow.engine.map.Room;
import tow.game.client.menu.MenuMainRoom;
import tow.game.client.tanks.player.PlayerController;
import tow.game.server.ServerLoader;

import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;


public class Game implements GameInterface {

	@Override
	public void init() {
		GameSetting.init();

        Room room = new Room(700, 500);
        room.activate();

        //new LoginWindow();


		ClientData.name = "Key_J";
		Random rand = new Random();
		ClientData.color = new Color(rand.nextInt(150)+100, rand.nextInt(150)+100, rand.nextInt(150)+100);
		//ServerLoader.mapPath = "maps/town100k.maptest";


		Global.mouse.setCaptureCursor(true);
		Global.mouse.setCursorTexture(TextureManager.getTexture("cursor_aim_1"));

        new MenuMainRoom();
	}

	@Override
	public void update(long delta){
		if (Global.keyboard.isKeyDown(GLFW_KEY_Y)){
			//Global.logger.println(Global.engine.render.getFrameLayer(), Logger.Type.DEBUG);
		}
		if (Global.keyboard.isKeyDown(GLFW_KEY_E)){
			//Global.engine.render.newFrame();
			Global.engine.render.getFrameContainer().setPressed(true);
		}
	}

	@Override
	public void render(){ }
}
