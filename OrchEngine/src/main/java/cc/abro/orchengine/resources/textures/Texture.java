package cc.abro.orchengine.resources.textures;

import cc.abro.orchengine.context.Context;
import lombok.Getter;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Texture {
    private final TextureService textureService;

    @Getter
    private final int id;
    private final BufferedImage image;

    public Texture(BufferedImage image) {
        this.textureService = Context.getService(TextureService.class);

        this.image = image;
        id = textureService.genTextureId();
    }

    public void bind() {
        textureService.bindTexture(id);
    }

    public void unbind() {
        textureService.unbindTexture();
    }

    public void delete() {
        textureService.deleteTexture(id);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public BufferedImage getImage() {
        ColorModel colorModel = image.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Texture texture = (Texture) o;
        return id == texture.id;
    }
}
