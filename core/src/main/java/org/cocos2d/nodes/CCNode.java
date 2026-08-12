package org.cocos2d.nodes;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.actions.base.CCAction;

public class CCNode {
    protected Group group;
    protected float width, height;
    protected CGPoint anchorPoint = new CGPoint(0, 0);
    public CCNode parent;
    protected float rawX, rawY;

    public CCNode() {
        group = new Group();
        group.setTransform(true);
        group.setUserObject(this);
    }

    public Group getGroup() { return group; }

    public void addChild(CCNode child) {
        addChild(child, 0);
    }

    public void addChild(CCNode child, int z) {
        if (child == null) return;
        if (child.getGroup().getParent() != null) {
            child.getGroup().remove();
        }
        child.parent = this;
        group.addActor(child.getGroup());
    }

    public void setPosition(float x, float y) {
        this.rawX = x;
        this.rawY = y;
        updateInternalPosition();
    }

    protected void updateInternalPosition() {
        group.setPosition(rawX - anchorPoint.x * width * group.getScaleX(), 
                         rawY - anchorPoint.y * height * group.getScaleY());
    }

    public void setPosition(CGPoint p) {
        if (p != null) setPosition(p.x, p.y);
    }

    public CGPoint getPosition() {
        return new CGPoint(rawX, rawY);
    }

    public void setAnchorPoint(float x, float y) {
        anchorPoint = new CGPoint(x, y);
        group.setOrigin(x * width, y * height);
        updateInternalPosition();
    }

    public void setScale(float s) {
        group.setScale(s);
        updateInternalPosition();
    }

    public void setScaleX(float s) {
        group.setScaleX(s);
        updateInternalPosition();
    }

    public void setScaleY(float s) {
        group.setScaleY(s);
        updateInternalPosition();
    }

    public void setVisible(boolean visible) {
        group.setVisible(visible);
    }

    public CGSize getContentSize() {
        return new CGSize(width, height);
    }

    public void setContentSize(float w, float h) {
        this.width = w;
        this.height = h;
        group.setSize(w, h);
        group.setOrigin(anchorPoint.x * width, anchorPoint.y * height);
        updateInternalPosition();
    }

    public void setOpacity(int opacity) {
        group.getColor().a = opacity / 255.0f;
    }
    
    public CCNode getParent() {
        return parent;
    }

    public void stopAllActions() {
        group.clearActions();
    }

    public void runAction(CCAction action) {
        if (action != null && action.getGdxAction() != null) {
            group.addAction(action.getGdxAction());
        }
    }

    public void stopAction(CCAction action) {
        if (action != null && action.getGdxAction() != null) {
            group.removeAction(action.getGdxAction());
        }
    }

    public org.cocos2d.types.CGPoint convertToWorldSpace(float x, float y) {
        com.badlogic.gdx.math.Vector2 v = new com.badlogic.gdx.math.Vector2(x, y);
        if (group.getStage() != null) {
            group.localToStageCoordinates(v);
        } else {
            CCNode p = parent;
            v.x = x + group.getX();
            v.y = y + group.getY();
            while (p != null) {
                v.x += p.getGroup().getX();
                v.y += p.getGroup().getY();
                p = p.parent;
            }
        }
        return new org.cocos2d.types.CGPoint(v.x, v.y);
    }

    public org.cocos2d.types.CGPoint convertToNodeSpace(org.cocos2d.types.CGPoint worldPoint) {
        if (worldPoint == null) return new org.cocos2d.types.CGPoint(0, 0);
        com.badlogic.gdx.math.Vector2 v = new com.badlogic.gdx.math.Vector2(worldPoint.x, worldPoint.y);
        if (group.getStage() != null) {
            group.stageToLocalCoordinates(v);
        } else {
            v.x -= rawX;
            v.y -= rawY;
        }
        return new org.cocos2d.types.CGPoint(v.x, v.y);
    }

    public void removeSelf() {
        if (parent != null) {
            parent.removeChild(this, true);
        } else {
            group.remove();
        }
    }
    
    public void removeChild(CCNode child, boolean cleanup) {
        if (child == null) return;
        if (child.parent == this) {
            child.parent = null;
        }
        child.getGroup().remove();
    }

    public static CCNode node() {
        return new CCNode();
    }
    
    public void schedule(String selector, float interval) {}
}
