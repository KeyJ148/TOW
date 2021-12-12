package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.services.CreateServerService;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;

import java.util.Collections;
import java.util.List;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;

public class CreateGameMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3 + 1;
    protected final static int MAIN_PANEL_HEIGHT = 10 * MENU_ELEMENT_HEIGHT / 3 + 1;
    protected final static int INDENT_Y_LAYER2 = + INDENT_Y + MENU_TEXT_FIELD_HEIGHT + 20;

    public CreateGameMenuGuiPanel() {
        super();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(createLabel("Server name:", INDENT_X, INDENT_Y, LABEL_LENGTH_SERVER_NAME, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldServerName = createTextAreaField(INDENT_X + LABEL_LENGTH_SERVER_NAME, INDENT_Y,
                TEXT_AREA_LENGTH_SERVER_NAME, MENU_TEXT_FIELD_HEIGHT, "Server");
        add(textAreaFieldServerName);

        add(createLabel("Port:", INDENT_X, INDENT_Y_LAYER2, LABEL_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldPort = createTextAreaField(INDENT_X + LABEL_LENGTH_PORT, INDENT_Y_LAYER2,
                TEXT_AREA_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");
        add(textAreaFieldPort);

        add(createLabel("Maximum people:", INDENT_X + LABEL_LENGTH_PORT + TEXT_AREA_LENGTH_PORT + 10, INDENT_Y_LAYER2,
                LABEL_LENGTH_MAX_PEOPLE, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldMaxPeople = createTextAreaFieldWithANumber(INDENT_X + LABEL_LENGTH_PORT + TEXT_AREA_LENGTH_PORT + LABEL_LENGTH_MAX_PEOPLE + 10, INDENT_Y_LAYER2,
                TEXT_AREA_LENGTH_MAX_PEOPLE, MENU_TEXT_FIELD_HEIGHT, "1");
        add(textAreaFieldMaxPeople);

        add(createButton("Back to menu", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(createButton("Create a game", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, getMouseReleaseListener(() -> connectToServer(
                        textAreaFieldPort.getTextState().getText(),
                        textAreaFieldMaxPeople.getTextState().getText())
                )));
    }

    private void connectToServer(String port, String maxPeople) {
        LabelGuiPanel connectedGuiPanel = createLabelPanel("Connected players: ",
                CONNECTED_PLAYERS_ELEMENT_WIDTH, CONNECTING_ELEMENT_HEIGHT);
        BlockingGuiService.GuiBlock frameBlock = Manager.getService(BlockingGuiService.class)
                .createGuiBlock(getFrame().getContainer());
        Manager.getService(GuiService.class).moveComponentToWindowCenter(connectedGuiPanel.panel());
        getFrame().getContainer().add(connectedGuiPanel.panel());

        ConnectedPlayersUpdater connectedPlayersUpdater = new ConnectedPlayersUpdater(connectedGuiPanel.label());
        Manager.getService(LocationManager.class).getActiveLocation().getMap()
                .add(GameObjectFactory.create(connectedPlayersUpdater));

        try {
            Manager.getService(CreateServerService.class).createServer(port,
                    Integer.parseInt(maxPeople));
        } catch (CreateServerService.ServerIsLaunchingExeption e) {
            getFrame().getContainer().remove(connectedGuiPanel.panel());
            addButtonGuiPanelWithUnblock(Error.SERVER_LAUNCHING.getText(), frameBlock);
        } catch (NumberFormatException | CreateServerService.WrongPortException e){
            getFrame().getContainer().remove(connectedGuiPanel.panel());
            addButtonGuiPanelWithUnblock(Error.WRONG_PORT.getText(), frameBlock);
        } catch (RuntimeException e) {
            getFrame().getContainer().remove(connectedGuiPanel.panel());
            addButtonGuiPanelWithUnblock(Error.UNKNOWN.getText(), frameBlock);
        }
    }

    private void addButtonGuiPanelWithUnblock(String text, BlockingGuiService.GuiBlock guiBlock) {
        Panel panel = createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        Manager.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    //TODO
    public enum Error {
        WRONG_PORT("ERROR: Port must be an integer 1024-65535"),
        SERVER_LAUNCHING("ERROR: Server is launching"),
        UNKNOWN("ERROR: Something went wrong");

        private final String text;

        Error(String text) {
            this.text = text;
        }

        public String getText(){
            return text;
        }
    }

    //TODO упростить в системе компонент новой
    public class ConnectedPlayersUpdater extends Component {

        private final Label label;

        public ConnectedPlayersUpdater(Label label) {
            this.label = label;
        }
        @Override
        public Class getComponentClass() {
            return ConnectedPlayersUpdater.class;
        }

        @Override
        public void update(long delta) {
            label.getTextState().setText("Connected players: " + GameServer.peopleNow + "/" + GameServer.peopleMax);
        }

        @Override
        public void draw() {}

    }
}
