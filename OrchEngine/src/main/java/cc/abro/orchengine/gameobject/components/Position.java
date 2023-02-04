package cc.abro.orchengine.gameobject.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.Location;

//TODO удалить класс. Location вынести в GameObject, а [x, y, z] и т.п. в каждый компонент контейнер.
//TODO directionDraw -- часть спрайта и анимации или это просто direction (не movement же)?
public class Position extends Component {

    private double directionDraw; //0, 360 - в право, против часовой - отрисовка
    public boolean absolute = true; //Позиция относительно угла карты? (Иначе относительно угла экрана)

    public Location location;//Локация, в которой находится объект

    public Position(double x, double y, int z) {
        this(x, y, z, 90);
    }

    public Position(double x, double y, int z, double directionDraw) {
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
}
