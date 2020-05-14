package tow.game.client.menu.locations;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.style.Background;
import tow.engine.image.Color;
import tow.engine.net.client.Connector;
import tow.game.client.ClientData;
import tow.game.client.menu.MenuLocation;
import tow.game.client.menu.StartServerListener;
import tow.game.server.ServerLoader;

import static tow.game.client.menu.InterfaceStyles.*;

public class PlayMenuLocation extends MenuLocation implements StartServerListener {

    private int portHosted;


    public PlayMenuLocation(){
        final TextAreaField textAreaFieldNick = new TextAreaField();

        addComponent(new Panel(), width/2, height/2, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);

        addComponent(new Label("Count players:"), width/2-50, height/2-20, 150, MENU_TEXT_FIELD_HEIGHT);

        TextAreaField textAreaFieldPlayers = new TextAreaField();
        textAreaFieldPlayers.getStyle().setBackground(createTextAreaFieldBackground());
        addComponent(textAreaFieldPlayers, width/2-25, height/2-20, 40, MENU_TEXT_FIELD_HEIGHT);

        addComponent(new Label("Port:"), width/2+105, height/2-20, 150, MENU_TEXT_FIELD_HEIGHT);

        TextAreaField textAreaFieldHostPort = new TextAreaField();
        textAreaFieldHostPort.getStyle().setBackground(createTextAreaFieldBackground());
        textAreaFieldHostPort.getTextState().setText("25566");
        addComponent(textAreaFieldHostPort, width/2+90, height/2-20, 60, MENU_TEXT_FIELD_HEIGHT);

        Button buttonHost = new Button("Host");
        buttonHost.setStyle(createButtonStyle());
        buttonHost.getListenerMap().addListener(MouseClickEvent.class,
                getMouseReleaseListener(event -> {
                    int port = Integer.parseInt(textAreaFieldHostPort.getTextState().getText());
                    int people = !textAreaFieldPlayers.getTextState().getText().isEmpty()?
                            Integer.parseInt(textAreaFieldPlayers.getTextState().getText()) : 1;

                    ServerLoader.startServerListener = this;
                    portHosted = port;
                    if (textAreaFieldNick.getTextState().getText().length() != 0) ClientData.name = textAreaFieldNick.getTextState().getText();
                    new ServerLoader(port, people, false);
                }));
        addComponent(buttonHost, width/2, height/2+15, MENU_ELEMENT_WIDTH-10, MENU_ELEMENT_HEIGHT-50);



        addComponent(new Panel(), width/2, height/2+1*MENU_ELEMENT_HEIGHT, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);

        addComponent(new Label("Nick:"), width/2-50, height/2+1*MENU_ELEMENT_HEIGHT-20, 150, MENU_TEXT_FIELD_HEIGHT);

        //Создание в самом верху, потмоу что надо обращатсья из лямбды в кнопках Host и Connect
        textAreaFieldNick.getStyle().setBackground(createTextAreaFieldBackground());
        addComponent(textAreaFieldNick, width/2-20, height/2+1*MENU_ELEMENT_HEIGHT-20, 150, MENU_TEXT_FIELD_HEIGHT);

        Panel panelColor = new Panel();
        Background panelColorBackground = new Background();
        panelColorBackground.setColor(new Vector4f((float) 1, (float) 1, (float) 1, 1));
        panelColor.getStyle().setBackground(panelColorBackground);
        addComponent(panelColor, width/2+90, height/2+1*MENU_ELEMENT_HEIGHT-20, 60, MENU_TEXT_FIELD_HEIGHT);

        addComponent(new Label("Color:"), width/2-50, height/2+1*MENU_ELEMENT_HEIGHT+15, 150, MENU_TEXT_FIELD_HEIGHT);

        Color[] colors = {
                new Color(255, 255, 255),
                new Color(255, 0, 0),
                new Color(0, 255, 0),
                new Color(0, 0, 255),
                new Color(255, 255, 0),
                new Color(255, 0, 255),
                new Color(0, 255, 255),
                new Color(0, 125, 255),
                new Color(255, 125, 0),
                new Color(125, 0, 255),
                new Color(125, 125, 0),
                new Color(125, 125, 125, 125)
        };
        for (int i = 0; i < 12; i++) {
            Button buttonColor = new Button("");
            buttonColor.getStyle().setBorder(createButtonBorder());
            Background buttonColorBackground = new Background();
            buttonColorBackground.setColor(new Vector4f(colors[i].getFloatRed(), colors[i].getFloatGreen(),
                    colors[i].getFloatBlue(), colors[i].getFloatAlpha()));
            buttonColor.getStyle().setBackground(buttonColorBackground);

            final int finalI = i;
            buttonColor.getListenerMap().addListener(MouseClickEvent.class,
                    getMouseReleaseListener(event -> {
                        ClientData.color = colors[finalI];
                        panelColorBackground.setColor(new Vector4f(colors[finalI].getFloatRed(), colors[finalI].getFloatGreen(),
                                colors[finalI].getFloatBlue(), colors[finalI].getFloatAlpha()));
                    }));
            addComponent(buttonColor, width/2-80 + 17*i, height/2+1*MENU_ELEMENT_HEIGHT+15, 15, 15);
        }




        addComponent(new Panel(), width/2, height/2+7*MENU_ELEMENT_HEIGHT/4, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT/2);

        Button buttonBack = new Button("Back to menu");
        buttonBack.setStyle(createButtonStyle());
        buttonBack.getListenerMap().addListener(MouseClickEvent.class, getActivateLocationMouseReleaseListener(MainMenuLocation.class));

        addComponent(buttonBack, width/2, height/2+7*MENU_ELEMENT_HEIGHT/4, 6*MENU_ELEMENT_WIDTH/13, MENU_ELEMENT_HEIGHT/3);

    }


    @Override
    public void serverStart() {
        new Connector().connect("127.0.0.1", portHosted);
    }
}
