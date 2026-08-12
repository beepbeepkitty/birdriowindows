package org.cocos2d.menus;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCNode;

public class CCMenu extends CCLayer {
    public CCMenu() {
        super();
        // Cocos2d CCMenu usually covers the whole parent and items are positioned relative to it
        setContentSize(1280, 800);
        setPosition(0, 0);
        setAnchorPoint(0, 0);
    }

    public static CCMenu menu(CCNode... items) {
        CCMenu menu = new CCMenu();
        for (CCNode item : items) {
            menu.addChild(item);
        }
        return menu;
    }
}
