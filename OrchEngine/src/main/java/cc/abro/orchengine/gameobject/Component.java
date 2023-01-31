package cc.abro.orchengine.gameobject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Component {

    @Getter
    @Setter(AccessLevel.PACKAGE) //TODO подумать надо ли ограничивать эту функцию единоразовым вызовом или нет (если уже gameObject != null, то exception). Вызывать из GameObject
    private GameObject gameObject;

    public void initialize() {}

    public void destroy() {}

    public Class<? extends Component> getComponentClass() {
        return getClass();
    }

}
