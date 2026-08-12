package org.cocos2d.actions.interval;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;

public class CCFadeIn extends CCAction {
    public static CCFadeIn action(float duration) {
        CCFadeIn a = new CCFadeIn();
        a.gdxAction = Actions.fadeIn(duration);
        return a;
    }
}
