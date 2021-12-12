package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.net.client.ConnectException;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.services.ConnectServerService;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;

public class ConnectByIPMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    protected final static int MAIN_PANEL_WIDTH = 4 * MENU_ELEMENT_WIDTH / 3 + 1;
    protected final static int MAIN_PANEL_HEIGHT = 5 * MENU_ELEMENT_HEIGHT / 3;

    public ConnectByIPMenuGuiPanel() {
        super();
        setSize(MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);

        add(createLabel("IP:", INDENT_X, INDENT_Y, LABEL_LENGTH_ID, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldIP =
                createTextAreaField(INDENT_X + LABEL_LENGTH_ID, INDENT_Y, TEXT_AREA_LENGTH_IP, MENU_TEXT_FIELD_HEIGHT);
        add(textAreaFieldIP);

        add(createLabel("Port:", MAIN_PANEL_WIDTH - LABEL_LENGTH_PORT - TEXT_AREA_LENGTH_PORT - INDENT_X, INDENT_Y, LABEL_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldPort =
                createTextAreaField(MAIN_PANEL_WIDTH - TEXT_AREA_LENGTH_PORT - INDENT_X, INDENT_Y, TEXT_AREA_LENGTH_PORT, MENU_TEXT_FIELD_HEIGHT, "25566");
        add(textAreaFieldPort);

        add(createButton("Back", INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class)));
        add(createButton("Connect", MAIN_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X, MAIN_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(() -> connectToServer(
                        textAreaFieldIP.getTextState().getText(),
                        textAreaFieldPort.getTextState().getText()
                ))));
    }

    private void connectToServer(String ip, String port) {
        Panel connectingGuiPanel = createLabelPanel("Connecting...",
                CONNECTING_ELEMENT_WIDTH, CONNECTING_ELEMENT_HEIGHT).panel();
        Manager.getService(GuiService.class).moveComponentToWindowCenter(connectingGuiPanel);
        BlockingGuiService.GuiBlock frameBlock = Manager.getService(BlockingGuiService.class)
                .createGuiBlock(getFrame().getContainer());
        getFrame().getContainer().add(connectingGuiPanel);

        try {
            Manager.getService(ConnectServerService.class).connect(
                    (!ip.isEmpty()) ? InetAddress.getByName(ip) : InetAddress.getLocalHost(),
                    Integer.parseInt(port));

            getFrame().getContainer().remove(connectingGuiPanel);
            Panel waitingGuiPanel = createLabelPanel("Waiting for players...",
                    WAITING_ELEMENT_WIDTH, CONNECTING_ELEMENT_HEIGHT).panel();
            Manager.getService(GuiService.class).moveComponentToWindowCenter(waitingGuiPanel);
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
        Panel panel = createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        Manager.getService(GuiService.class).moveComponentToWindowCenter(panel);
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
