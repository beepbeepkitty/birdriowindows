package org.cocos2d.actions.ease;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.cocos2d.actions.base.CCAction;

public class CCEaseElasticIn extends CCAction {
    public static CCEaseElasticIn action(CCAction action, float period) {
        CCEaseElasticIn a = new CCEaseElasticIn();
        if (action.getGdxAction() instanceof TemporalAction) {
            ((TemporalAction)action.getGdxAction()).setInterpolation(Interpolation.elasticIn);
        }
        a.gdxAction = action.getGdxAction();
        return a;
    }
}
