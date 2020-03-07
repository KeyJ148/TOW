package tow.game.client.menu;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import tow.engine.image.Color;
import tow.engine.net.client.Connector;
import tow.game.client.ClientData;
import tow.game.server.ServerLoader;

public class PlayMenuLocation extends MenuLocation implements StartServerListener {

    private int portHosted;
    private boolean wasConnect = false;

    public PlayMenuLocation(){
        final TextAreaField textAreaFieldNick = new TextAreaField();

        createComponent(new Panel(), width/2, height/2-1*MENU_ELEMENT_HEIGHT, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);

        createComponent(new Label("IP:"), width/2-50, height/2-1*MENU_ELEMENT_HEIGHT-20, 150, MENU_TEXT_FIELD_HEIGHT);

        TextAreaField textAreaFieldIP = new TextAreaField();
        Background textAreaFieldIPBackground = new Background();
        textAreaFieldIPBackground.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        textAreaFieldIP.getStyle().setBackground(textAreaFieldIPBackground);
        createComponent(textAreaFieldIP, width/2-30, height/2-1*MENU_ELEMENT_HEIGHT-20, 150, MENU_TEXT_FIELD_HEIGHT);

        createComponent(new Label(":"), width/2+127, height/2-1*MENU_ELEMENT_HEIGHT-20, 150, MENU_TEXT_FIELD_HEIGHT);

        TextAreaField textAreaFieldPort = new TextAreaField();
        Background textAreaFieldPortBackground = new Background();
        textAreaFieldPortBackground.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        textAreaFieldPort.getStyle().setBackground(textAreaFieldPortBackground);
        textAreaFieldPort.getTextState().setText("25566");
        createComponent(textAreaFieldPort, width/2+90, height/2-1*MENU_ELEMENT_HEIGHT-20, 60, MENU_TEXT_FIELD_HEIGHT);

        Button buttonConnect = new Button("Connect");
        SimpleLineBorder buttonConnectBorder = new SimpleLineBorder(ColorConstants.black(), 1);
        buttonConnect.getStyle().setBorder(buttonConnectBorder);
        buttonConnect.getListenerMap().addListener(MouseClickEvent.class, event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE){
                if (wasConnect) return;
                wasConnect = true;

                String ip = (!textAreaFieldIP.getTextState().getText().isEmpty())? textAreaFieldIP.getTextState().getText() : "127.0.0.1";
                int port = Integer.parseInt(textAreaFieldPort.getTextState().getText());
                if (textAreaFieldNick.getTextState().getText().length() != 0) ClientData.name = textAreaFieldNick.getTextState().getText();
                new Connector().connect(ip, port);
            }
        });
        createComponent(buttonConnect, width/2, height/2-1*MENU_ELEMENT_HEIGHT+15, MENU_ELEMENT_WIDTH-10, MENU_ELEMENT_HEIGHT-50);




        createComponent(new Panel(), width/2, height/2, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);

        createComponent(new Label("Count players:"), width/2-50, height/2-20, 150, MENU_TEXT_FIELD_HEIGHT);

        TextAreaField textAreaFieldPlayers = new TextAreaField();
        Background textAreaFieldPlayersBackground = new Background();
        textAreaFieldPlayersBackground.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        textAreaFieldPlayers.getStyle().setBackground(textAreaFieldPlayersBackground);
        createComponent(textAreaFieldPlayers, width/2-25, height/2-20, 40, MENU_TEXT_FIELD_HEIGHT);

        createComponent(new Label("Port:"), width/2+105, height/2-20, 150, MENU_TEXT_FIELD_HEIGHT);

        TextAreaField textAreaFieldHostPort = new TextAreaField();
        Background textAreaFieldHostPortBackground = new Background();
        textAreaFieldHostPortBackground.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        textAreaFieldHostPort.getStyle().setBackground(textAreaFieldHostPortBackground);
        textAreaFieldHostPort.getTextState().setText("25566");
        createComponent(textAreaFieldHostPort, width/2+90, height/2-20, 60, MENU_TEXT_FIELD_HEIGHT);

        Button buttonHost = new Button("Host");
        SimpleLineBorder buttonHostBorder = new SimpleLineBorder(ColorConstants.black(), 1);
        buttonHost.getStyle().setBorder(buttonHostBorder);
        buttonHost.getListenerMap().addListener(MouseClickEvent.class, event -> {
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE){
                int port = Integer.parseInt(textAreaFieldHostPort.getTextState().getText());
                int people = !textAreaFieldPlayers.getTextState().getText().isEmpty()?
                        Integer.parseInt(textAreaFieldPlayers.getTextState().getText()) : 1;

                ServerLoader.startServerListener = this;
                portHosted = port;
                if (textAreaFieldNick.getTextState().getText().length() != 0) ClientData.name = textAreaFieldNick.getTextState().getText();
                new ServerLoader(port, people, false);
            }
        });
        createComponent(buttonHost, width/2, height/2+15, MENU_ELEMENT_WIDTH-10, MENU_ELEMENT_HEIGHT-50);





        createComponent(new Panel(), width/2, height/2+1*MENU_ELEMENT_HEIGHT, MENU_ELEMENT_WIDTH, MENU_ELEMENT_HEIGHT);

        createComponent(new Label("Nick:"), width/2-50, height/2+1*MENU_ELEMENT_HEIGHT-20, 150, MENU_TEXT_FIELD_HEIGHT);

        //Создание в самом верху, потмоу что надо обращатсья из лямбды в кнопках Host и Connect
        Background textAreaFieldNickBackground = new Background();
        textAreaFieldNickBackground.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        textAreaFieldNick.getStyle().setBackground(textAreaFieldNickBackground);
        createComponent(textAreaFieldNick, width/2-20, height/2+1*MENU_ELEMENT_HEIGHT-20, 150, MENU_TEXT_FIELD_HEIGHT);

        Panel panelColor = new Panel();
        Background panelColorBackground = new Background();
        panelColorBackground.setColor(new Vector4f((float) 1, (float) 1, (float) 1, 1));
        panelColor.getStyle().setBackground(panelColorBackground);
        createComponent(panelColor, width/2+90, height/2+1*MENU_ELEMENT_HEIGHT-20, 60, MENU_TEXT_FIELD_HEIGHT);

        createComponent(new Label("Color:"), width/2-50, height/2+1*MENU_ELEMENT_HEIGHT+15, 150, MENU_TEXT_FIELD_HEIGHT);

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
            SimpleLineBorder buttonColorBorder = new SimpleLineBorder(ColorConstants.black(), 1);
            buttonColor.getStyle().setBorder(buttonColorBorder);
            Background buttonColorBackground = new Background();
            buttonColorBackground.setColor(new Vector4f(colors[i].getFloatRed(), colors[i].getFloatGreen(),
                    colors[i].getFloatBlue(), colors[i].getFloatAlpha()));
            buttonColor.getStyle().setBackground(buttonColorBackground);

            final int finalI = i;
            buttonColor.getListenerMap().addListener(MouseClickEvent.class, event -> {
                if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE){
                    ClientData.color = colors[finalI];
                    panelColorBackground.setColor(new Vector4f(colors[finalI].getFloatRed(), colors[finalI].getFloatGreen(),
                            colors[finalI].getFloatBlue(), colors[finalI].getFloatAlpha()));
                }
            });
            createComponent(buttonColor, width/2-80 + 17*i, height/2+1*MENU_ELEMENT_HEIGHT+15, 15, 15);
        }
    }


    @Override
    public void serverStart() {
        new Connector().connect("127.0.0.1", portHosted);
    }
}
