package cc.abro.tow.client.gui.menu.panels;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.util.GameObjectFactory;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.services.CreateServerService;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;

public class CreateGameMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    protected final static int MAIN_PANEL_WIDTH = 4 * InterfaceStyles.MENU_ELEMENT_WIDTH / 3 + 1;
    protected final static int MAIN_PANEL_HEIGHT = 10 * InterfaceStyles.MENU_ELEMENT_HEIGHT / 3 + 1;
    protected final static int INDENT_Y_LAYER2 = + InterfaceStyles.INDENT_Y + InterfaceStyles.MENU_TEXT_FIELD_HEIGHT + 20;

    public CreateGameMenuGuiPanel() {
        super();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(MenuGuiComponents.createLabel("Server name:", InterfaceStyles.INDENT_X, InterfaceStyles.INDENT_Y, InterfaceStyles.LABEL_LENGTH_SERVER_NAME, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldServerName = MenuGuiComponents.createTextAreaField(InterfaceStyles.INDENT_X + InterfaceStyles.LABEL_LENGTH_SERVER_NAME, InterfaceStyles.INDENT_Y,
                InterfaceStyles.TEXT_AREA_LENGTH_SERVER_NAME, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT, "Server");
        add(textAreaFieldServerName);

        add(MenuGuiComponents.createLabel("Port:", InterfaceStyles.INDENT_X, INDENT_Y_LAYER2, InterfaceStyles.LABEL_LENGTH_PORT, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldPort = MenuGuiComponents.createTextAreaField(InterfaceStyles.INDENT_X + InterfaceStyles.LABEL_LENGTH_PORT, INDENT_Y_LAYER2,
                InterfaceStyles.TEXT_AREA_LENGTH_PORT, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT, "25566");
        add(textAreaFieldPort);

        add(MenuGuiComponents.createLabel("Maximum people:", InterfaceStyles.INDENT_X + InterfaceStyles.LABEL_LENGTH_PORT + InterfaceStyles.TEXT_AREA_LENGTH_PORT + 10, INDENT_Y_LAYER2,
                InterfaceStyles.LABEL_LENGTH_MAX_PEOPLE, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldMaxPeople = MenuGuiComponents.createTextAreaFieldWithANumber(InterfaceStyles.INDENT_X + InterfaceStyles.LABEL_LENGTH_PORT + InterfaceStyles.TEXT_AREA_LENGTH_PORT + InterfaceStyles.LABEL_LENGTH_MAX_PEOPLE + 10, INDENT_Y_LAYER2,
                InterfaceStyles.TEXT_AREA_LENGTH_MAX_PEOPLE, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT, "1");
        add(textAreaFieldMaxPeople);

        add(MenuGuiComponents.createButton("Back to menu", InterfaceStyles.INDENT_X, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT, getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(MenuGuiComponents.createButton("Create a game", MAIN_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH - InterfaceStyles.INDENT_X, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT, getMouseReleaseListener(() -> connectToServer(
                        textAreaFieldPort.getTextState().getText(),
                        textAreaFieldMaxPeople.getTextState().getText())
                )));
    }

    private void connectToServer(String port, String maxPeople) {
        MenuGuiComponents.LabelGuiPanel connectedGuiPanel = MenuGuiComponents.createLabelPanel("Connected players: ",
                InterfaceStyles.CONNECTED_PLAYERS_ELEMENT_WIDTH, InterfaceStyles.CONNECTING_ELEMENT_HEIGHT);
        BlockingGuiService.GuiBlock frameBlock = getBlockingGuiService()
                .createGuiBlock(getFrame().getContainer());
        getGuiService().moveComponentToWindowCenter(connectedGuiPanel.panel());
        getFrame().getContainer().add(connectedGuiPanel.panel());

        ConnectedPlayersUpdater connectedPlayersUpdater = new ConnectedPlayersUpdater(connectedGuiPanel.label());
        GameObjectFactory.create(getLocationManager().getActiveLocation(), connectedPlayersUpdater);
        try {
            Context.getService(CreateServerService.class).createServer(port,
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
        Panel panel = MenuGuiComponents.createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        getGuiService().moveComponentToWindowCenter(panel);
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
    public static class ConnectedPlayersUpdater extends Component implements Updatable {

        private final Label label;

        public ConnectedPlayersUpdater(Label label) {
            this.label = label;
        }

        @Override
        public void update(long delta) {
            label.getTextState().setText("Connected players: " + GameServer.peopleNow + "/" + GameServer.peopleMax);
        }

        @Override
        public Class<? extends Component> getComponentClass() {
            return ConnectedPlayersUpdater.class;
        }
    }
}
