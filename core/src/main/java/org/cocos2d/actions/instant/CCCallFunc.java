package org.cocos2d.actions.instant;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;
import java.lang.reflect.Method;

public class CCCallFunc extends CCAction {
    public static CCCallFunc action(final Object target, final String selector) {
        CCCallFunc a = new CCCallFunc();
        a.gdxAction = Actions.run(new Runnable() {
            @Override
            public void run() {
                if (target == null || selector == null) return;
                try {
                    // Try method with Object parameter (sender)
                    try {
                        Method m = target.getClass().getMethod(selector, Object.class);
                        m.setAccessible(true);
                        m.invoke(target, target);
                    } catch (NoSuchMethodException e) {
                        // Try method without parameters
                        Method m = target.getClass().getMethod(selector);
                        m.setAccessible(true);
                        m.invoke(target);
                    }
                } catch (Exception e) {
                    System.err.println("CCCallFunc error: " + selector + " on " + target.getClass().getName());
                    // Don't print full stack trace to avoid cluttering, but log the error
                }
            }
        });
        return a;
    }
}
