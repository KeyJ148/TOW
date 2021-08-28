package cc.abro.tow.client.menu.panels.controllers.settings;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.menu.panels.controllers.main.CloseChildPanelController;
import cc.abro.tow.client.menu.panels.events.settings.ClickConfirmGuiEvent;
import cc.abro.tow.client.menu.panels.gui.PrintErrorGuiPanel;

import java.util.Set;

public class ClickConfirmController extends GuiElementController<ClickConfirmGuiEvent> {

    @Override
    protected Class<ClickConfirmGuiEvent> getProcessedEventClass() {
        return ClickConfirmGuiEvent.class;
    }

    @Override
    public void processEvent(ClickConfirmGuiEvent event) {
        if (!event.getNickname().isEmpty()) {
            ClientData.name = event.getNickname();
        } else {
            createErrorPanel(Error.NICKNAME_IS_EMPTY);
        }
        ClientData.color = event.getTankColor();
    }

    private void createErrorPanel(Error error) {
        PrintErrorGuiPanel guiPanel = new PrintErrorGuiPanel(error.getText(),
                getGuiElement().getComponent());
        EventableGuiPanelElement<EventableGuiPanel> guiElement = new EventableGuiPanelElement<>(
                guiPanel, Set.of(new CloseChildPanelController()));
        Vector2<Double> position = getGuiElement().getPosition();
        Manager.getService(GuiElementService.class).addGuiElementToLocationShiftedToCenter(guiElement,
                position.x.intValue(), position.y.intValue(),
                getGuiElement().getGameObject().getComponent(Position.class).location);
    }

    public enum Error {
        NICKNAME_IS_EMPTY {
            @Override
            public String getText() {
                return "ERROR: Nickname is empty";
            }
        },
        WRONG_LETTERS_IN_NICKNAME {
            @Override
            public String getText() {
                return "ERROR: Wrong letters in nickname";
            }
        },
        UNKNOWN {
            @Override
            public String getText() {
                return "ERROR: Something went wrong";
            }
        };

        public abstract String getText();
    }
}
