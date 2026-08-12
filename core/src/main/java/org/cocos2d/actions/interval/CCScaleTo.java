package org.cocos2d.actions.interval;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;

public class CCScaleTo extends CCAction {
    public static CCScaleTo action(float duration, float scale) {
        CCScaleTo a = new CCScaleTo();
        a.gdxAction = Actions.scaleTo(scale, scale, duration);
        return a;
    }
}
