package cc.abro.tow;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.JsonContainerLoader;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.TextureService;
import cc.abro.tow.client.Game;
import cc.abro.tow.services.TestTextureService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class TextureTests {

    @Test
    @SneakyThrows
    public void checkInvisiblePixelsInTextures() {
        Context.addService(TestTextureService.class);
        SpriteStorage.SpriteContainer[] spriteContainers = JsonContainerLoader.loadInternalFile(
                SpriteStorage.SpriteContainer[].class, Game.SPRITE_CONFIG_PATH);
        boolean allSuccess = true;
        Set<String> brokenImages = new HashSet<>();
        for (SpriteStorage.SpriteContainer spriteContainer : spriteContainers) {
            BufferedImage image = Context.getService(TextureService.class)
                    .getTexture(spriteContainer.texturePath()).getImage();
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            imageLoop:
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[y * width + x];

                    int r = pixel & 0xFF;
                    int g = (pixel >> 8) & 0xFF;
                    int b = (pixel >> 16) & 0xFF;
                    int a = (pixel >> 24) & 0xFF;

                    if (a == 0 && (r != 0 || g != 0 || b != 0)) {
                        brokenImages.add(spriteContainer.texturePath());
                        break imageLoop;
                    }
                }
            }
        }
        if (!brokenImages.isEmpty()) {
            Assertions.fail("Images have not black invisible pixels: " + brokenImages);
        }
    }
}
