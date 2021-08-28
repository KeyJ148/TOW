package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Widget;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.style.Style;

import static cc.abro.tow.client.menu.InterfaceStyles.INDENT_Y;
import static cc.abro.tow.client.menu.InterfaceStyles.createInvisibleStyle;

public class MainMenuLogoGuiPanel extends MenuGuiPanel {

    public MainMenuLogoGuiPanel(Texture logoTexture) {
        FBOImage logoFBOImage = new FBOImage(logoTexture.getId(), logoTexture.getWidth(), logoTexture.getHeight());
        setSize(logoTexture.getWidth(), logoTexture.getHeight());
        setStyle(createInvisibleStyle());

        ImageView imageView = new ImageView(logoFBOImage);
        imageView.setStyle(createInvisibleStyle());

        addComponent(imageView, 0, 0,
                logoTexture.getWidth(), logoTexture.getHeight());
    }
}
