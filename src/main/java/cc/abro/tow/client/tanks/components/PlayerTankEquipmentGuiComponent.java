package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.image.Color;
import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.style.Background;
import com.spinyowl.legui.style.border.SimpleLineBorder;
import com.spinyowl.legui.style.color.ColorConstants;

import java.util.ArrayList;
import java.util.List;

public class PlayerTankEquipmentGuiComponent extends Component<GameObject> {

    private final List<Button> playerCanTakeBoxButtons = new ArrayList<>();

    public PlayerTankEquipmentGuiComponent() {
        //Создание кнопок для отключения подбора снаряжения
        List<Background> buttonsBackground = new ArrayList<>();
        buttonsBackground.add(createButtonBackground(Color.GREEN));
        buttonsBackground.add(createButtonBackground(Color.RED));
        buttonsBackground.add(createButtonBackground(Color.BLUE));
        buttonsBackground.add(createButtonBackground(Color.WHITE));

        for (int i = 0; i < buttonsBackground.size(); i++) {
            Button button = createButton(buttonsBackground.get(i));
            button.setPosition(17 * i, getRender().getHeight() - 15);

            playerCanTakeBoxButtons.add(button);
        }
    }

    @Override
    public void initialize() {
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().addAll(playerCanTakeBoxButtons);
    }

    @Override
    public void destroy() {
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer()
                .removeAll(playerCanTakeBoxButtons);
    }

    public void changeButtonState(boolean active, int index) {
        playerCanTakeBoxButtons.get(index).getTextState().setText(active ? "" : "x");
    }

    private Button createButton(Background background) { //TODO куда-нибудь в сервис интерфейса?
        Button button = new Button("");
        SimpleLineBorder buttonTakeBorder = new SimpleLineBorder(ColorConstants.black(), 1);
        button.getStyle().setBorder(buttonTakeBorder);
        button.getStyle().setBackground(background);
        button.setSize(15, 15);
        return button;
    }

    private Background createButtonBackground(Color color) {
        Background background = new Background();
        background.setColor(color.getVector4f());
        return background;
    }

    private Background createButtonBackground(float r, float g, float b) {
        return createButtonBackground(new Color(r, g, b));
    }
}
