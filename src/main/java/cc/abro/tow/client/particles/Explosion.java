package cc.abro.tow.client.particles;

import cc.abro.orchengine.gameobject.components.particles.Part;
import cc.abro.orchengine.gameobject.components.particles.ParticlesTexture;
import cc.abro.orchengine.image.Color;

import java.util.Random;

public class Explosion extends ParticlesTexture {

    private final double size;

    public Explosion(double size) {
        this.size = size;
    }

    public void activate() {
        double count = size * 2;
        Random rand = new Random();
        if (size > 70) count = size * 3;
        for (int i = 0; i < count; i++) {
            Part part = new Part();
            part.texture = getSpriteStorage().getSprite("part_ball").texture();
            part.x = getGameObject().getX();
            part.y = getGameObject().getY();
            part.direction = getGameObject().getDirection() + rand.nextInt(360);
            part.speed = (size / 5 + rand.nextInt((int) (2 * size / 5)));
            if (size < 70) {
                part.width = size / 2 + rand.nextInt((int) size / 5);
                part.height = size / 2 + rand.nextInt((int) size / 5);
            } else {
                part.width = 70 / 2 + rand.nextInt((int) 70 / 5);
                part.height = 70 / 2 + rand.nextInt((int) 70 / 5);
            }
            if (part.speed > (2 * size / 5)) {
                part.color = new Color((int) 200 + rand.nextInt(55), (int) rand.nextInt(100), rand.nextInt(20));
                part.life = (size / 400);
                part.life += (float) rand.nextInt(50) / 75;
            } else {
                part.color = new Color((int) (200 + rand.nextInt(55)), (int) (200 + rand.nextInt(55)), (int) (255));
                part.life = (size / 200);
                part.life += (float) rand.nextInt(50) / 150;
            }
            part.type = Part.Type.FILL;
            parts.add(part);
        }
    }

    @Override
    public void updateChild(long delta, Part part) {
        if (part.speed > (2 * size / 5)) part.speed -= (float) (40 * delta) / 1000000000;
        float r = (float) (part.color.getFloatRed() - ((double) ((1 + part.speed / 150) * delta) / 1000000000 / 12));
        float g = (float) (part.color.getFloatGreen() - ((double) ((1 + part.speed / 150) * delta) / 1000000000 / 4));
        float b = (float) (part.color.getFloatBlue() - (((double) (4 * delta)) / 1000000000 / 2));
        float a = (float) part.life * 10;

        r = Math.min(Math.max(r, 0), 1);
        g = Math.min(Math.max(g, 0), 1);
        b = Math.min(Math.max(b, 0), 1);
        a = Math.min(Math.max(a, 0), 1);
        part.color = new Color(r, g, b, a);
    }
}