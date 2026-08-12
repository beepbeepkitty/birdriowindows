package org.cocos2d.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Group;

public class CCLabelAtlas extends CCNode {
    private String text;
    private String charMapFile;
    private int itemWidth, itemHeight;
    private char startChar;
    private Texture texture;

    public CCLabelAtlas(String text, String charMapFile, int itemWidth, int itemHeight, char startChar) {
        this.text = text;
        this.charMapFile = charMapFile;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.startChar = startChar;
        this.texture = new Texture(charMapFile);
        updateLabel();
    }

    public static CCLabelAtlas label(String text, String charMapFile, int itemWidth, int itemHeight, char startChar) {
        return new CCLabelAtlas(text, charMapFile, itemWidth, itemHeight, startChar);
    }

    public void setString(String text) {
        this.text = text;
        updateLabel();
    }

    private void updateLabel() {
        group.clearChildren();
        float x = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int idx = c - startChar;
            int row = idx / (texture.getWidth() / itemWidth);
            int col = idx % (texture.getWidth() / itemWidth);
            TextureRegion region = new TextureRegion(texture, col * itemWidth, row * itemHeight, itemWidth, itemHeight);
            Image img = new Image(region);
            img.setPosition(x, 0);
            group.addActor(img);
            x += itemWidth;
        }
        group.setSize(x, itemHeight);
    }
}
