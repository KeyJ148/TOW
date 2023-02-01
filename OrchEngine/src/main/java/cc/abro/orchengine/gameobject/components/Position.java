package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.util.Vector2;

//TODO удалить класс. Location вынести в GameObject, а [x, y, z] и т.п. в каждый компонент контейнер.
//TODO directionDraw -- часть спрайта и анимации или это просто direction (не movement же)?
public class Position extends Component {

    public double x;
    public double y;
    public int z; //TODO вынести это свойство в Drawable интерфейс? И пробегаться не по игровым объектам, а по компонентам с drawable при отрисовке? Проблема в том, что глубины следующие: танк -> дом -> пушка, при текущей схеме танк и пушка должны быть разными игровыми объектами
    private double directionDraw; //0, 360 - в право, против часовой - отрисовка
    public boolean absolute = true; //Позиция относительно угла карты? (Иначе относительно угла экрана)

    public Location location;//Локация, в которой находится объект

    public Position(double x, double y, int z) {
        this(x, y, z, 90);
    }

    public Position(double x, double y, int z, double directionDraw) {
        this.x = x;
        this.y = y;
        this.z = z;
        setDirectionDraw(directionDraw);
    }

    public double getDirectionDraw() {
        if (directionDraw % 360 >= 0) {
            return directionDraw % 360;
        } else {
            return 360 - Math.abs(directionDraw % 360);
        }
    }

    public void setDirectionDraw(double directionDraw) {
        this.directionDraw = directionDraw;
    }

    public Vector2<Integer> getRelativePosition() {
        if (!absolute) {
            Vector2<Integer> relativePosition = new Vector2<>();
            relativePosition.x = (int) x;
            relativePosition.y = (int) y;
            return relativePosition;
        } else {
            return Context.getService(LocationManager.class).getActiveLocation().getCamera().toRelativePosition(new Vector2<>((int) x, (int) y));
        }
    }
}
