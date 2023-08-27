package cc.abro.tow.client.gui.menu;

import cc.abro.orchengine.image.Color;
import com.spinyowl.legui.component.*;
import com.spinyowl.legui.component.optional.align.HorizontalAlign;
import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;
import com.spinyowl.legui.style.font.FontRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.*;
import static java.lang.Math.max;

public final class MenuGuiComponents {

    public static Label createBigLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.setFocusable(false);
        label.setStyle(createBigLabelStyle());
        return label;
    }

    public static Label createLargerLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.setFocusable(false);
        label.setStyle(createLargerLabelStyle());
        return label;
    }

    public static Label createLabel(String text, int x, int y, int width, int height) {
        Label label = new Label(text, x, y, width, height);
        label.setFocusable(false);
        label.setStyle(createLabelStyle());
        return label;
    }

    public static Panel createMenuPanel(ButtonConfiguration... buttonConfigurations) {
        int countButtons = buttonConfigurations.length;

        final int INDENT = 5;
        final int MENU_PANEL_WIDTH = INDENT*2 + MENU_BUTTON_WIDTH;
        final int MENU_PANEL_HEIGHT = INDENT + countButtons * (INDENT + MENU_BUTTON_HEIGHT);

        Panel menu = new Panel();
        menu.setSize(MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT);
        menu.setStyle(createInvisibleStyle());
        for (int i = 0; i < countButtons; i++) {
            Button button = createMenuButton(buttonConfigurations[i].text,
                    (MENU_PANEL_WIDTH - MENU_BUTTON_WIDTH)/2,
                    (MENU_PANEL_HEIGHT - (countButtons * (INDENT + MENU_BUTTON_HEIGHT) - INDENT))/2 + i * (INDENT + MENU_BUTTON_HEIGHT),
                    MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT,
                    buttonConfigurations[i].eventListener);
            menu.add(button);
        }
        return menu;
    }

    public static Button createMenuButton(String text, int x, int y, int width, int height,
                                          MouseClickEventListener mouseClickEventListener) {
        Button button = new Button(text);
        button.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        button.setStyle(createMenuButtonStyle());
        button.getHoveredStyle().setBackground(createHoveredMenuButtonBackground());
        button.getPressedStyle().setBackground(createPressedMenuButtonBackground());
        button.getHoveredStyle().setBorder(createButtonBorder());
        button.getPressedStyle().setBorder(createButtonBorder());
        button.setPosition(x, y);
        button.setSize(width, height);
        return button;
    }

    public static Button createButton(String text, int x, int y, int width, int height,
                                      MouseClickEventListener mouseClickEventListener) {
        Button button = new Button(text);
        button.getListenerMap().addListener(MouseClickEvent.class, mouseClickEventListener);
        button.setStyle(createButtonStyle());
        button.getHoveredStyle().setBackground(createHoveredButtonBackground());
        button.getPressedStyle().setBackground(createPressedButtonBackground());
        button.setPosition(x, y);
        button.setSize(width, height);
        return button;
    }

    public static Button createBoxButton(Color color) {
        Button button = new Button("");
        SimpleLineBorder buttonTakeBorder = new SimpleLineBorder(ColorConstants.black(), 1);
        button.getStyle().setBorder(buttonTakeBorder);
        button.getStyle().setBackground(createColoredBackground(color));
        button.setSize(BOX_BUTTON_SIZE, BOX_BUTTON_SIZE);
        return button;
    }

    public static Slider createSlider(int x, int y, int width, int height) {
        Slider slider = new Slider(x, y, width, height);
        slider.setSliderColor(DARK_GRAY_COLOR);
        slider.setSliderActiveColor(SLIGHTLY_DARK_GRAY_COLOR);
        slider.setSliderSize(15);
        //slider.setStyle(createSliderStyle());
        return slider;
    }

    public static TextAreaField createTextAreaField(int x, int y, int width, int height) {
        TextAreaField textAreaField = new TextAreaField();
        textAreaField.setStyle(createTextAreaFieldStyle());
        textAreaField.getFocusedStyle().setBackground(createFocusedTextAreaFieldBackground());
        textAreaField.getFocusedStyle().setBorder(createTextAreaFieldBorder());
        textAreaField.getStyle().setFont(FontRegistry.ROBOTO_REGULAR);
        textAreaField.getStyle().setFontSize(LABEL_FONT_SIZE);
        textAreaField.getStyle().setTextColor(BLACK_COLOR);
        textAreaField.setPosition(x, y);
        textAreaField.setSize(width, height);
        return textAreaField;
    }

    public static TextAreaField createTextAreaField(int x, int y, int width, int height, String text) {
        TextAreaField textAreaField = createTextAreaField(x, y, width, height);
        textAreaField.getTextState().setText(text);
        return textAreaField;
    }

    public static TextAreaField createTextAreaFieldWithANumber(int x, int y, int width, int height, String text) {
        TextAreaField textAreaField = createTextAreaField(x, y, width, height);
        textAreaField.getTextState().setText(text);
        textAreaField.getStyle().setHorizontalAlign(HorizontalAlign.CENTER);
        return textAreaField;
    }

    public static Panel createPanel(int width, int height) {
        Panel panel = new Panel();
        panel.setStyle(createPanelStyle());
        panel.setFocusable(false);
        panel.setSize(width, height);
        return panel;
    }


    public static LabelGuiPanel createLabelPanel(String message, int width, int height) {
        int widthOfMessage = (int) (LABEL_FONT_SIZE*10/24) * message.length();
        Panel panel = createPanel(max(widthOfMessage + INDENT_X*2, width), height);
        Label label = createLabel(message, (int) (panel.getSize().x - widthOfMessage)/2, BLOCKING_BUTTON_INDENT_Y,
                widthOfMessage, MENU_TEXT_FIELD_HEIGHT);
        panel.add(label);
        return new LabelGuiPanel(panel, label);
    }

    public static ButtonGuiPanel createButtonPanel(String labelText, String buttonText, MouseClickEventListener eventListener) {
        LabelGuiPanel labelGuiPanel = createLabelPanel(labelText,
                BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
        Button button = createButton(buttonText,
                (int) (labelGuiPanel.panel.getSize().x - SMALL_BUTTON_WIDTH) / 2,
                (int) labelGuiPanel.panel.getSize().y - BUTTON_HEIGHT - BLOCKING_BUTTON_INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, eventListener);
        labelGuiPanel.panel.add(button);
        return new ButtonGuiPanel(labelGuiPanel.panel, labelGuiPanel.label, button);
    }

    public static DialogGuiPanel createDialogPanel(String labelText, ButtonConfiguration... buttonConfigurations) {
        final int BUTTON_WIDTH = Arrays.stream(buttonConfigurations).max(Comparator.comparing(ButtonConfiguration::text)).get().text.length() * 11;
        LabelGuiPanel labelGuiPanel = createLabelPanel(labelText,
                buttonConfigurations.length * (BUTTON_WIDTH + INDENT_X) + INDENT_X, BLOCKING_BUTTON_ELEMENT_HEIGHT);
        ArrayList<Button> buttons = new ArrayList<>();
        int indent = (int) (labelGuiPanel.panel.getSize().x - BUTTON_WIDTH * buttonConfigurations.length) /
                (buttonConfigurations.length + 1);
        for(int i = 0; i < buttonConfigurations.length; i++) {
            ButtonConfiguration buttonConfiguration = buttonConfigurations[i];
            Button button = createButton(buttonConfiguration.text,
                    i * (BUTTON_WIDTH + indent) + indent,
                    (int) labelGuiPanel.panel.getSize().y - BUTTON_HEIGHT - BLOCKING_BUTTON_INDENT_Y,
                    BUTTON_WIDTH, BUTTON_HEIGHT, buttonConfiguration.eventListener);
            labelGuiPanel.panel.add(button);
            buttons.add(button);
        }
        return new DialogGuiPanel(labelGuiPanel.panel, labelGuiPanel.label, buttons);
    }

    public static ScrollablePanel createScrollablePanel(int x, int y, int width, int height) {
        ScrollablePanel panel = new ScrollablePanel(x, y, width, height);
        panel.setStyle(createScrollablePanelStyle());
        ScrollBar scrollBar = panel.getVerticalScrollBar();
        scrollBar.setStyle(createScrollBarStyle());
        scrollBar.setScrollColor(DARK_GRAY_COLOR);
        panel.getContainer().setStyle(createScrollablePanelContainerStyle());
        panel.getContainer().setSize(panel.getSize());
        panel.setHorizontalScrollBarVisible(false);
        panel.setFocusable(false);
        return panel;
    }

    public static record ButtonConfiguration(String text, MouseClickEventListener eventListener) { }
    public static record LabelGuiPanel(Panel panel, Label label) { }
    public static record ButtonGuiPanel(Panel panel, Label label, Button button) { }
    public static record DialogGuiPanel(Panel panel, Label label, List<Button> buttons) { }
}
