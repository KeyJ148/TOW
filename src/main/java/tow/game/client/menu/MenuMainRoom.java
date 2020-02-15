package tow.game.client.menu;

import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.CursorEnterEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.listener.CursorEnterEventListener;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import tow.engine.Loader;
import tow.game.server.ServerLoader;

import java.util.ArrayList;
import java.util.List;

public class MenuMainRoom extends MenuRoom {

    public MenuMainRoom(){

        createButton("Play", -2, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) new MenuPlayRoom();});
        createButton("Profile", -1, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) return;});
        createButton("Settings", 0, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) return;});
        createButton("Exit", 1, event -> { if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) Loader.exit();});
    }

    private void createButton(String text, int pos, MouseClickEventListener event){
        Button button = new Button(text);
        SimpleLineBorder border = new SimpleLineBorder(ColorConstants.black(), 1);
        button.getStyle().setBorder(border);
        button.getListenerMap().addListener(MouseClickEvent.class, event);

        createComponent(button, width/2, height/2+pos*MENU_ELEMENT_HEIGHT, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);
    }
}
