package cc.abro.tow.client.tanks.tank;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.PositionableIgnoreParentDirectionComponent;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.util.Vector2;
import com.spinyowl.legui.component.Label;

import java.util.List;

public class TankNicknameComponent extends PositionableIgnoreParentDirectionComponent<GameObject> implements Updatable {

    private final Label nickname;

    public TankNicknameComponent(String nicknameString) {
        this.nickname = new Label();
        this.nickname.setSize(500, 30);
        this.nickname.setFocusable(false); //Иначе событие мыши перехватывает надпись, и оно не поступает в игру
        setNickname(nicknameString);
        setRelativeY(-80);
    }

    public TankNicknameComponent() {
        this("");
    }

    @Override
    public void initialize() {
        super.initialize();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(nickname);
    }

    @Override
    public void update(long delta) {
        Vector2<Double> relativePosition = getGameObject().getLocation().getCamera().toRelativePosition(getPosition());
        nickname.setPosition(relativePosition.x.floatValue(), relativePosition.y.floatValue());
    }

    @Override
    public void destroy() {
        super.destroy();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().remove(nickname);
    }

    @Override
    public List<Class<? extends Updatable>> getPreliminaryUpdateComponents() {
        return List.of(Movement.class);
    }

    public void setNickname(String nicknameString) {
        nickname.getTextState().setText(nicknameString);
        setRelativeX(-nicknameString.length() * 3.45);
    }

    public String getNickname() {
        return nickname.getTextState().getText();
    }
}
