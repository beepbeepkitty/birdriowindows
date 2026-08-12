package org.cocos2d.layers;

import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import android.view.MotionEvent;

public class CCLayer extends CCNode {
    public CCLayer() {
        super();
        group.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.enabled);
        group.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // LibGDX coordinates (x, y) are local to the group.
                // We pass them to ccTouchesBegan.
                return ccTouchesBegan(new MotionEvent(MotionEvent.ACTION_DOWN, x, y));
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ccTouchesEnded(new MotionEvent(MotionEvent.ACTION_UP, x, y));
            }
        });
    }
    
    public boolean ccTouchesBegan(MotionEvent event) { return false; }
    public boolean ccTouchesEnded(MotionEvent event) { return false; }
    
    public void setIsTouchEnabled(boolean enabled) {}
    public void setIsKeyEnabled(boolean enabled) {}
    public void scheduleUpdate() {
        group.addAction(new com.badlogic.gdx.scenes.scene2d.Action() {
            @Override
            public boolean act(float delta) {
                update(delta);
                return false;
            }
        });
    }
    public void update(float dt) {}
}
