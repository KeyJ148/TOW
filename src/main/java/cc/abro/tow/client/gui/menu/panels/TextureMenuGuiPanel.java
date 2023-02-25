package cc.abro.tow.client.gui.menu.panels;

import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.image.FBOImage;

public class TextureMenuGuiPanel extends MenuGuiPanel {

    public TextureMenuGuiPanel(Texture logoTexture) {
        this(logoTexture, logoTexture.getWidth(), logoTexture.getHeight());
    }

    public TextureMenuGuiPanel(Texture logoTexture, float x, float y) {
        FBOImage logoFBOImage = new FBOImage(logoTexture.getId(), logoTexture.getWidth(), logoTexture.getHeight());
        setSize(x, y);
        setStyle(InterfaceStyles.createInvisibleStyle());

        ImageView imageView = new ImageView(logoFBOImage);
        imageView.setStyle(InterfaceStyles.createInvisibleStyle());
        imageView.setPosition(0, 0);
        imageView.setSize(x, y);

        add(imageView);
    }
}
