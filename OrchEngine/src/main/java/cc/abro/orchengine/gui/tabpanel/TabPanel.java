package cc.abro.orchengine.gui.tabpanel;

import cc.abro.orchengine.gui.MouseReleaseListeners;
import cc.abro.orchengine.gui.tabpanel.modes.SimpleTabPanelButtonMode;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TabPanel extends Panel implements MouseReleaseListeners {

    private float xIndentVision = 0f;

    private float yIndentVision = 0f;

    private final List<TiedButtonPanel> content;

    private final Panel vision;

    private TiedButtonPanel activeTiedPair;

    private TabPanelButtonMode mode = new SimpleTabPanelButtonMode();

    private Style previousButtonStyle;

    private Style activeButtonStyle;

    public TabPanel() {
        super();
        vision = new Panel();
        content = new ArrayList<>();
        initialize();
    }

    public TabPanel(float x, float y, float width, float height) {
        super(x, y, width, height);
        content = new ArrayList<>();
        vision = new Panel(0, 0, width, height);
        initialize();
    }

    public TabPanel(float x, float y, float width, float height, List<TiedButtonPanel> inputContent) {
        super(x, y, width, height);
        content = new ArrayList<>(inputContent);
        vision = new Panel(0, 0, width, height);
        for (TiedButtonPanel pair : inputContent) {
            addNewTiedButtonPanel(pair);
        }
        initialize();
    }

    public void addNewTiedButtonPanel(Button button, Panel panel) {
        addNewTiedButtonPanel(new TiedButtonPanel(button, panel));
    }

    public void addNewTiedButtonPanel(Button button, Panel panel, Function<Panel, Boolean> canOut, Function<Panel, Boolean> canIn) {
        addNewTiedButtonPanel(new TiedButtonPanel(button, panel, canOut, canIn));
    }

    public void addNewTiedButtonPanel(TiedButtonPanel pair) {
        content.add(pair);
        if (content.size() == 1) {
            setActivePanelFromTiedPair(pair);
        }
        pair.button.getListenerMap().addListener(MouseClickEvent.class, getMouseReleaseListener(event -> {
            setActivePanelFromTiedPair(pair);
        }));
        add(pair.button);
        mode.updateButtons(content, getSize());
    }

    public void setMode(TabPanelButtonMode mode) {
        this.mode = mode;
    }

    public void setSize(Vector2f size) {
        setSize(size.x, size.y);
    }

    public void setSize(float width, float height) {
        super.setSize(width, height);
        vision.setSize(width - xIndentVision, height - yIndentVision);
        vision.setPosition(xIndentVision, yIndentVision);
    }

    public void setButtonsStyle(Style style) {
        for (TiedButtonPanel pair : content) {
            pair.button.setStyle(style);
        }
        previousButtonStyle = style;
        activeTiedPair.button.setStyle(activeButtonStyle);
        mode.updateButtons(content, getSize());
    }

    public void setActiveButtonStyle(Style style) {
        activeButtonStyle = style;
        activeTiedPair.button.setStyle(activeButtonStyle);
    }

    public void setXIndentVision(float newIndentVision) {
        xIndentVision = newIndentVision;
        if (newIndentVision <= getSize().x) {
            setSize(getSize());
        } else {
            setSize(newIndentVision, getSize().y);
        }
    }

    public void setYIndentVision(float newIndentVision) {
        yIndentVision = newIndentVision;
        if (newIndentVision <= getSize().y) {
            setSize(getSize());
        } else {
            setSize(getSize().x, newIndentVision);
        }
    }

    public TiedButtonPanel getTideButtonPanel(int index) {
        return content.get(index);
    }

    public TiedButtonPanel getTideButtonPanel(Panel panel) {
        return content.stream()
                .filter(tiedButtonPanel -> panel.equals(tiedButtonPanel.panel()))
                .findFirst()
                .orElse(null);
    }

    public TiedButtonPanel getTideButtonPanel(Button button) {
        return content.stream()
                .filter(tiedButtonPanel -> button.equals(tiedButtonPanel.button()))
                .findFirst()
                .orElse(null);
    }

    public void setActivePanelFromTiedPair(TiedButtonPanel pair) {
        if (activeTiedPair != null) {
            if (activeTiedPair.canOut.apply(pair.panel) && pair.canIn.apply(activeTiedPair.panel)) {
                activeTiedPair.button.setStyle(previousButtonStyle);
                activeTiedPair.button.setFocusable(true);
            } else {
                return;
            }
        }
        vision.clearChildComponents();
        activeTiedPair = pair;
        previousButtonStyle = activeTiedPair.button.getStyle();
        activeTiedPair.button.setStyle(activeButtonStyle);
        activeTiedPair.button.setFocusable(false);
        vision.add(activeTiedPair.panel);
    }

    private void initialize() {
        vision.getStyle().getBackground().setColor(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
        vision.getStyle().setBorder(new SimpleLineBorder(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f), 0));

        add(vision);
        if (!content.isEmpty()) {
            setActivePanelFromTiedPair(content.get(0));
        }
    }

    public static record TiedButtonPanel(Button button, Panel panel, Function<Panel, Boolean> canOut,
                                         Function<Panel, Boolean> canIn) {
        public TiedButtonPanel(Button button, Panel panel) {
            this(button, panel, (to) -> true, (from) -> true);
        }
    }
}
