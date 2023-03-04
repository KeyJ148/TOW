package cc.abro.tow.client.gui.menu.panels;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.services.ConnectServerService;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.TextAreaField;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectByIPMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    protected final static int MAIN_PANEL_WIDTH = 4 * InterfaceStyles.MENU_ELEMENT_WIDTH / 3 + 1;
    protected final static int MAIN_PANEL_HEIGHT = 5 * InterfaceStyles.MENU_ELEMENT_HEIGHT / 3;

    public ConnectByIPMenuGuiPanel() {
        super();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(MenuGuiComponents.createLabel("IP:", InterfaceStyles.INDENT_X, InterfaceStyles.INDENT_Y, InterfaceStyles.LABEL_LENGTH_ID, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldIP =
                MenuGuiComponents.createTextAreaField(InterfaceStyles.INDENT_X + InterfaceStyles.LABEL_LENGTH_ID, InterfaceStyles.INDENT_Y, InterfaceStyles.TEXT_AREA_LENGTH_IP, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT);
        add(textAreaFieldIP);

        add(MenuGuiComponents.createLabel("Port:", MAIN_PANEL_WIDTH - InterfaceStyles.LABEL_LENGTH_PORT - InterfaceStyles.TEXT_AREA_LENGTH_PORT - InterfaceStyles.INDENT_X, InterfaceStyles.INDENT_Y, InterfaceStyles.LABEL_LENGTH_PORT, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldPort =
                MenuGuiComponents.createTextAreaField(MAIN_PANEL_WIDTH - InterfaceStyles.TEXT_AREA_LENGTH_PORT - InterfaceStyles.INDENT_X, InterfaceStyles.INDENT_Y, InterfaceStyles.TEXT_AREA_LENGTH_PORT, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT, "25566");
        add(textAreaFieldPort);

        add(MenuGuiComponents.createButton("Back", InterfaceStyles.INDENT_X, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y, InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(MenuGuiComponents.createButton("Connect", MAIN_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH - InterfaceStyles.INDENT_X, MAIN_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y, InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getMouseReleaseListener(() -> connectToServer(
                        textAreaFieldIP.getTextState().getText(),
                        textAreaFieldPort.getTextState().getText()
                ))));
    }

    private void connectToServer(String ip, String port) {
        Panel connectingGuiPanel = MenuGuiComponents.createLabelPanel("Connecting...",
                InterfaceStyles.CONNECTING_ELEMENT_WIDTH, InterfaceStyles.CONNECTING_ELEMENT_HEIGHT).panel();
        getGuiService().moveComponentToWindowCenter(connectingGuiPanel);
        BlockingGuiService.GuiBlock frameBlock = getBlockingGuiService()
                .createGuiBlock(getFrame().getContainer());
        getFrame().getContainer().add(connectingGuiPanel);

        try {
            Context.getService(ConnectServerService.class).connect(
                    (!ip.isEmpty()) ? InetAddress.getByName(ip) : InetAddress.getLocalHost(),
                    Integer.parseInt(port));

            getFrame().getContainer().remove(connectingGuiPanel);
            Panel waitingGuiPanel = MenuGuiComponents.createLabelPanel("Waiting for players...",
                    InterfaceStyles.WAITING_ELEMENT_WIDTH, InterfaceStyles.CONNECTING_ELEMENT_HEIGHT).panel();
            getGuiService().moveComponentToWindowCenter(waitingGuiPanel);
            getFrame().getContainer().add(waitingGuiPanel);
        } catch (UnknownHostException e) {
            getFrame().getContainer().remove(connectingGuiPanel);
            addButtonGuiPanelWithUnblock(Error.WRONG_IP.getText(), frameBlock);
        } catch (NumberFormatException | ConnectServerService.WrongPortException e) {
            getFrame().getContainer().remove(connectingGuiPanel);
            addButtonGuiPanelWithUnblock(Error.WRONG_PORT.getText(), frameBlock);
        } catch (ConnectException e) {
            getFrame().getContainer().remove(connectingGuiPanel);
            addButtonGuiPanelWithUnblock(Error.SERVER_NOT_FOUND.getText(), frameBlock);
        }
    }

    private void addButtonGuiPanelWithUnblock(String text, BlockingGuiService.GuiBlock guiBlock) {
        Panel panel = MenuGuiComponents.createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        getGuiService().moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    //TODO
    public enum Error {
        WRONG_IP("ERROR: IP address written incorrectly"),
        WRONG_PORT("ERROR: Port must be an integer 1024-65535"),
        SERVER_NOT_FOUND("ERROR: Server not found"),
        UNKNOWN("ERROR: Something went wrong");

        private final String text;

        Error(String text) {
            this.text = text;
        }

        public String getText(){
            return text;
        }
    }
}
