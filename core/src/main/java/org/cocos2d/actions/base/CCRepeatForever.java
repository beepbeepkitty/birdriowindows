package org.cocos2d.actions.base;

import com.badlogic.gdx.scenes.scene2d.Action;

public class CCRepeatForever extends CCAction {
    
    private static class SafeRepeatAction extends Action {
        private Action inner;
        
        public SafeRepeatAction(Action inner) {
            this.inner = inner;
        }
        
        @Override
        public boolean act(float delta) {
            if (inner == null) return true; // Done if null
            
            // Set actor to inner action if needed
            if (inner.getActor() == null && getActor() != null) {
                inner.setActor(getActor());
            }
            
            if (inner.act(delta)) {
                inner.restart();
            }
            return false; // Never done
        }
        
        @Override
        public void setActor(com.badlogic.gdx.scenes.scene2d.Actor actor) {
            super.setActor(actor);
            if (inner != null) inner.setActor(actor);
        }
    }

    public static CCRepeatForever action(CCAction action) {
        CCRepeatForever a = new CCRepeatForever();
        if (action != null && action.getGdxAction() != null) {
            a.gdxAction = new SafeRepeatAction(action.getGdxAction());
        } else {
            a.gdxAction = createSafeEmpty();
        }
        return a;
    }
}
