package org.cocos2d.actions.ease;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.cocos2d.actions.base.CCAction;

public class CCEaseElasticOut extends CCAction {
    public static CCEaseElasticOut action(CCAction action, float period) {
        CCEaseElasticOut a = new CCEaseElasticOut();
        if (action.getGdxAction() instanceof TemporalAction) {
            ((TemporalAction)action.getGdxAction()).setInterpolation(Interpolation.elasticOut);
        }
        a.gdxAction = action.getGdxAction();
        return a;
    }
}
