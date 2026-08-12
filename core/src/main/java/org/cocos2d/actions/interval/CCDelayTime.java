package org.cocos2d.actions.interval;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;

public class CCDelayTime extends CCAction {
    public static CCDelayTime action(float duration) {
        CCDelayTime a = new CCDelayTime();
        a.gdxAction = Actions.delay(duration);
        return a;
    }
}
