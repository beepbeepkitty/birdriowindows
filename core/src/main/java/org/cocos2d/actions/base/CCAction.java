package org.cocos2d.actions.base;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class CCAction {
    // A safe empty action that does nothing and completes immediately
    public static Action createSafeEmpty() {
        return Actions.delay(0);
    }
    
    protected Action gdxAction;

    public CCAction() {
        this.gdxAction = createSafeEmpty();
    }

    public Action getGdxAction() { 
        return gdxAction != null ? gdxAction : createSafeEmpty(); 
    }
    
    public void setGdxAction(Action action) { 
        this.gdxAction = (action != null) ? action : createSafeEmpty(); 
    }
    
    public boolean isDone() {
        if (gdxAction != null) {
            return gdxAction.getActor() == null;
        }
        return true;
    }
}
