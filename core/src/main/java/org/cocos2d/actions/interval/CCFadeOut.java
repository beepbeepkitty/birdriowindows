package org.cocos2d.actions.interval;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;

public class CCFadeOut extends CCAction {
    public static CCFadeOut action(float duration) {
        CCFadeOut a = new CCFadeOut();
        a.gdxAction = Actions.fadeOut(duration);
        return a;
    }
}
