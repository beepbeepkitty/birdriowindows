package org.cocos2d.actions.interval;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.types.CGPoint;

public class CCMoveTo extends CCAction {
    public static CCMoveTo action(float duration, CGPoint position) {
        CCMoveTo a = new CCMoveTo();
        a.gdxAction = Actions.moveTo(position.x, position.y, duration);
        return a;
    }
}
