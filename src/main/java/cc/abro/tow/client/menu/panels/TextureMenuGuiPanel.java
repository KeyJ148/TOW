package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.resources.textures.Texture;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.image.FBOImage;

import static cc.abro.tow.client.menu.InterfaceStyles.createInvisibleStyle;

public class TextureMenuGuiPanel extends MenuGuiPanel {

    public TextureMenuGuiPanel(Texture logoTexture) {
        FBOImage logoFBOImage = new FBOImage(logoTexture.getId(), logoTexture.getWidth(), logoTexture.getHeight());
        setSize(logoTexture.getWidth(), logoTexture.getHeight());
        setStyle(createInvisibleStyle());

        ImageView imageView = new ImageView(logoFBOImage);
        imageView.setStyle(createInvisibleStyle());
        imageView.setPosition(0, 0);
        imageView.setSize(logoTexture.getWidth(), logoTexture.getHeight());

        add(imageView);
    }
}
