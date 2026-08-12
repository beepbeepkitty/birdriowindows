package org.cocos2d.actions.interval;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import java.util.ArrayList;
import java.util.List;

public class CCAnimate extends CCAction {
    
    private static class TextureUpdateAction extends Action {
        private String texturePath;
        public TextureUpdateAction(String path) {
            this.texturePath = path;
        }
        @Override
        public boolean act(float delta) {
            if (getActor() == null) return true;
            Object userObj = getActor().getUserObject();
            if (userObj instanceof CCSprite) {
                ((CCSprite) userObj).setTexture(texturePath);
            }
            return true;
        }
    }

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
            if (current == null) { index++; return act(delta); }
            if (current.getActor() == null && getActor() != null) current.setActor(getActor());
            if (current.act(delta)) {
                index++;
                return index >= actions.size();
            }
            return false;
        }
        
        @Override
        public void restart() {
            index = 0;
            if (actions != null) { for (Action a : actions) if (a != null) a.restart(); }
        }
        
        @Override
        public void setActor(com.badlogic.gdx.scenes.scene2d.Actor actor) {
            super.setActor(actor);
            if (actions != null) { for (Action a : actions) if (a != null) a.setActor(actor); }
        }
    }

    public static CCAction action(CCAnimation animation) {
        if (animation == null || animation.getFrames() == null || animation.getFrames().isEmpty()) {
            CCAction empty = new CCAction();
            return empty;
        }
        
        List<Action> actions = new ArrayList<>();
        for (final String frame : animation.getFrames()) {
            if (frame != null && !frame.isEmpty()) {
                actions.add(new TextureUpdateAction(frame));
                actions.add(Actions.delay(animation.getDelay()));
            }
        }
        
        CCAction ccAction = new CCAction();
        ccAction.setGdxAction(new SafeSequenceAction(actions));
        return ccAction;
    }

    public static CCAction action(CCAnimation animation, boolean restore) {
        return action(animation);
    }
}
