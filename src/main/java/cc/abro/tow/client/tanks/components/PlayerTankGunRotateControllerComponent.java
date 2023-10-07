package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.util.Vector2;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.stats.Stats;

public class PlayerTankGunRotateControllerComponent extends Component<Tank> implements Updatable {

    @Override
    public void update(long delta) {
        SpriteRender<GameObject> gun = getGameObject().getGunSpriteComponent();
        Stats stats = getGameObject().getTankStatsComponent().getStats();

        /*
         * Поворот дула пушки (много костылей)
         */
        Vector2<Double> relativePosition = getGameObject().getLocation().getCamera()
                .toRelativePosition(gun.getPosition());
        double relativeX = relativePosition.x + 0.1;
        double relativeY = relativePosition.y + 0.1;

        double pointDir = -Math.toDegrees(Math.atan((relativeY - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().y) / (relativeX - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().x)));

        double speedRotateGun = ((double) delta / 1000000000) * (stats.getSpeedRotateGun());
        if ((relativeX - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().x) > 0) {
            pointDir += 180;
        } else if ((relativeY - getGameObject().getLocation().getGuiLocationFrame().getMouse().getCursor().getPosition().y) < 0) {
            pointDir += 360;
        }

        if ((pointDir - gun.getDirection()) > 0) {
            if ((pointDir - gun.getDirection()) > 180) {
                gun.setDirection(gun.getDirection() - speedRotateGun);
            } else {
                gun.setDirection(gun.getDirection() + speedRotateGun);
            }
        } else {
            if ((pointDir - gun.getDirection()) < -180) {
                gun.setDirection(gun.getDirection() + speedRotateGun);
            } else {
                gun.setDirection(gun.getDirection() - speedRotateGun);
            }
        }

        if ((Math.abs(pointDir - gun.getDirection()) < speedRotateGun * 1.5) ||
                (Math.abs(pointDir - gun.getDirection()) > 360 - speedRotateGun * 1.5)) {
            gun.setDirection(pointDir);
        }
    }
}
