package org.cocos2d.menus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCTextureCache;
import java.lang.reflect.Method;

public class CCMenuItemImage extends CCNode {
    private ImageButton button;
    
    public void setIsEnabled(boolean enabled) {
        if (button != null) {
            button.setDisabled(!enabled);
            button.setTouchable(enabled ? com.badlogic.gdx.scenes.scene2d.Touchable.enabled : com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        }
    }
    
    public CCMenuItemImage(String normal, String selected, final Object target, final String selector) {
        super();
        Texture tNormal = CCTextureCache.sharedTextureCache().addImage(normal);
        Texture tSelected = CCTextureCache.sharedTextureCache().addImage(selected);
        
        button = new ImageButton(
            new TextureRegionDrawable(tNormal),
            new TextureRegionDrawable(tSelected)
        );
        
        if (target != null && selector != null) {
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        try {
                            Method m = target.getClass().getDeclaredMethod(selector, Object.class);
                            m.setAccessible(true);
                            m.invoke(target, CCMenuItemImage.this);
                        } catch (NoSuchMethodException nsme) {
                            Method m = target.getClass().getDeclaredMethod(selector);
                            m.setAccessible(true);
                            m.invoke(target);
                        }
                    } catch (Exception e) {
                        // Log error but don't crash
                        System.err.println("Menu item click error: " + selector);
                    }
                }
            });
        }
        
        this.width = tNormal.getWidth();
        this.height = tNormal.getHeight();
        group.addActor(button);
        group.setSize(width, height);
        button.setSize(width, height);
        button.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.enabled);
        setAnchorPoint(0.5f, 0.5f);
    }

    public static CCMenuItemImage item(String normal, String selected, Object target, String selector) {
        return new CCMenuItemImage(normal, selected, target, selector);
    }

    public static CCMenuItemImage item(String normal, String selected) {
        return new CCMenuItemImage(normal, selected, null, null);
    }
}
