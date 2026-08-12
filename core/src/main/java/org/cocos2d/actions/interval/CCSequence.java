package org.cocos2d.actions.interval;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;
import java.util.ArrayList;
import java.util.List;

public class CCSequence extends CCAction {
    
    private static class SafeSequenceAction extends Action {
        private List<Action> actions;
        private int index = 0;
        
        public SafeSequenceAction(List<Action> actions) {
            this.actions = actions;
        }
        
        @Override
        public boolean act(float delta) {
            if (actions == null || index >= actions.size()) return true;
            
            Action current = actions.get(index);
            if (current == null) {
                index++;
                return act(delta);
            }
            
            if (current.getActor() == null && getActor() != null) {
                current.setActor(getActor());
            }
            
            if (current.act(delta)) {
                index++;
                if (index >= actions.size()) return true;
            }
            return false;
        }
        
        @Override
        public void restart() {
            index = 0;
            if (actions != null) {
                for (Action a : actions) {
                    if (a != null) a.restart();
                }
            }
        }
        
        @Override
        public void setActor(com.badlogic.gdx.scenes.scene2d.Actor actor) {
            super.setActor(actor);
            if (actions != null) {
                for (Action a : actions) {
                    if (a != null) a.setActor(actor);
                }
            }
        }
    }

    public static CCSequence actions(CCAction... actions) {
        CCSequence s = new CCSequence();
        List<Action> validActions = new ArrayList<>();
        if (actions != null) {
            for (CCAction cca : actions) {
                if (cca != null && cca.getGdxAction() != null) {
                    validActions.add(cca.getGdxAction());
                }
            }
        }
        
        if (!validActions.isEmpty()) {
            s.gdxAction = new SafeSequenceAction(validActions);
        } else {
            s.gdxAction = createSafeEmpty();
        }
        return s;
    }
}
