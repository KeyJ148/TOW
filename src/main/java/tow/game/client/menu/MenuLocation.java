package tow.game.client.menu;

import tow.engine.map.Location;

public abstract class MenuLocation extends Location {
    public MenuLocation() {
        super(0, 0);
    }

    /*protected static class ButtonConfiguration {

        public String text;
        public MouseClickEventListener event;

        public ButtonConfiguration(String text, MouseClickEventListener event) {
            this.text = text;
            this.event = event;
        }
    }

    public MenuLocation(){
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        background = new tow.engine.map.Background(Color.GRAY, Color.GRAY);
    }

    public void addComponentToParent(Location location, Component component, int x, int y, int width, int height, Component parent){
        x += parent.getPosition().x;
        y += parent.getPosition().y;
        addComponent(location, component, x, y, width, height);
    }

    public Button createButton(String text, int x, int y, int width, int height, MouseClickEventListener event, Component parent){
        Button button = new Button(text);
        button.setStyle(createButtonStyle());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        addComponentToParent(button, x, y, width, height, parent);
        return button;
    }

    public TextAreaField createTextAreaField(int x, int y, int width, int height, Component parent) {
        TextAreaField textAreaField = new TextAreaField();
        textAreaField.setStyle(createTextAreaFieldStyle());

        addComponentToParent(textAreaField, x, y, width, height, parent);
        return textAreaField;
    }

    public Panel createPanelToParent(int x, int y, int width, int height, Component parent) {
        Panel panel = new Panel();
        panel.setStyle(createPanelStyle());
        panel.setFocusable(false);

        addComponentToParent(panel, x, y, width, height, parent);
        panel.setPosition(x, y);
        return panel;
    }*/
}
