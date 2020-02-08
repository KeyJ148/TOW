package tow.game.client;

import legui.ExampleGui;
import org.joml.Vector2i;
import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.Animator;
import org.liquidengine.legui.animation.AnimatorProvider;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.WindowSizeEvent;
import org.liquidengine.legui.listener.WindowSizeEventListener;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.liquidengine.legui.system.renderer.Renderer;
import org.liquidengine.legui.theme.Themes;
import org.liquidengine.legui.theme.colored.FlatColoredTheme;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL11;
import tow.engine.Global;
import tow.engine.image.TextureManager;
import tow.engine.implementation.GameInterface;
import tow.engine.map.Room;
import tow.engine.net.client.Connector;
import tow.game.client.lobby.StartServerListener;
import tow.game.server.ServerLoader;

import static org.liquidengine.legui.style.color.ColorUtil.fromInt;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game implements GameInterface, StartServerListener {



	@Override
	public void init() {
        Global.room = new Room(700, 500);
        //new LoginWindow();

		ClientData.name = "Key_J";
		ServerLoader.startServerListener = this;
		ServerLoader.mapPath = ClientData.map;
		new ServerLoader(25566, 1, false);

		GameSetting.init();

		Global.mouse.setCaptureCursor(true);
        Global.mouse.setCursorTexture(TextureManager.getTexture("cursor_aim_1"));
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }

	@Override
	public void serverStart() {
		new Connector().connect("127.0.0.1", 25566);
	}
}
