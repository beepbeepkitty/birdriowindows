package org.cocos2d.layers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.cocos2d.types.ccColor4B;

public class CCColorLayer extends CCLayer {
    private static Texture whiteTexture;

    private static Texture getWhiteTexture() {
        if (whiteTexture == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();
            whiteTexture = new Texture(pixmap);
            pixmap.dispose();
        }
        return whiteTexture;
    }

    public CCColorLayer(ccColor4B color, float w, float h) {
        super();
        Image img = new Image(getWhiteTexture());
        img.setColor(new Color(color.r/255f, color.g/255f, color.b/255f, color.a/255f));
        img.setSize(w, h);
        group.addActor(img);
        setContentSize(w, h);
    }

    public static CCColorLayer node(ccColor4B color, float w, float h) {
        return new CCColorLayer(color, w, h);
    }
}
