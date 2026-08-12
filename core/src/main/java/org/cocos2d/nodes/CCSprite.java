package org.cocos2d.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CCSprite extends CCNode {
    private Image image;

    public CCSprite(String filename) {
        super();
        Texture texture = CCTextureCache.sharedTextureCache().addImage(filename);
        image = new Image(texture);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        group.addActor(image);
        group.setSize(width, height);
        image.setSize(width, height);
        image.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        setAnchorPoint(0.5f, 0.5f);
    }

    public static CCSprite sprite(String filename) {
        return new CCSprite(filename);
    }

    public void setTexture(String filename) {
        if (filename == null || filename.isEmpty()) return;
        Texture texture = CCTextureCache.sharedTextureCache().addImage(filename);
        if (texture != null) {
            image.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
        }
    }

    public void setTexture(Texture texture) {
        if (texture != null) {
            image.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
        }
    }
}
