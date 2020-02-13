package tow.game.client;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import tow.engine.Global;
import tow.engine.image.TextureManager;
import tow.engine.implementation.GameInterface;
import tow.engine.map.Room;
import tow.engine.net.client.Connector;
import tow.game.client.lobby.StartServerListener;
import tow.game.server.ServerLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Game implements GameInterface, StartServerListener {



	@Override
	public void init() {
        Global.room = new Room(700, 500);
        //new LoginWindow();

		ClientData.name = "Key_J";
		ServerLoader.startServerListener = this;
		ServerLoader.mapPath = ClientData.map;
		new ServerLoader(25566, 1, false);

		//new Connector().connect("127.0.0.1", 25566);

		GameSetting.init();

		Global.mouse.setCaptureCursor(true);
        Global.mouse.setCursorTexture(TextureManager.getTexture("cursor_aim_1"));

        createFrameWithGUI(Global.engine.render.getFrameContainer());
	}

	@Override
	public void update(long delta){ }

	@Override
	public void render(){ }

	@Override
	public void serverStart() {
		new Connector().connect("127.0.0.1", 25566);
	}


	private void createFrameWithGUI(Component frameContainer) {
		// Set background color for frame
		frameContainer.getStyle().getBackground().setColor(ColorConstants.transparent());
		frameContainer.setFocusable(true);

		Button button = new Button("Add components", 20, 20, 160, 30);
		SimpleLineBorder border = new SimpleLineBorder(ColorConstants.black(), 1);
		button.getStyle().setBorder(border);

		boolean[] added = {false};
		button.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
			if (!added[0]) {
				added[0] = true;
				for (Component c : generateOnFly()) {
					frameContainer.add(c);
				}
			}

		});

		button.getListenerMap().addListener(CursorEnterEvent.class, (CursorEnterEventListener) System.out::println);

		frameContainer.add(button);
	}

	private List<Component> generateOnFly() {
		List<Component> list = new ArrayList<>();

		Label label = new Label(20, 60, 200, 20);
		label.getTextState().setText("Generated on fly label");

		RadioButtonGroup group = new RadioButtonGroup();
		RadioButton radioButtonFirst = new RadioButton("First", 20, 90, 200, 20);
		RadioButton radioButtonSecond = new RadioButton("Second", 20, 110, 200, 20);

		radioButtonFirst.setRadioButtonGroup(group);
		radioButtonSecond.setRadioButtonGroup(group);

		list.add(label);
		list.add(radioButtonFirst);
		list.add(radioButtonSecond);

		return list;
	}
}
