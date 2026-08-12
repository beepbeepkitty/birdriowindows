package org.cocos2d.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.cocos2d.nodes.CCNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Method;

public class CCMenuItemToggle extends CCNode {
    private ArrayList<CCNode> items;
    private int selectedIndex = 0;
    private Object target;
    private String selector;

    public CCMenuItemToggle(Object target, String selector, CCNode... items) {
        super();
        this.target = target;
        this.selector = selector;
        this.items = new ArrayList<>(Arrays.asList(items));
        
        for (CCNode item : items) {
            item.setVisible(false);
            addChild(item);
        }
        
        if (this.items.size() > 0) {
            this.items.get(0).setVisible(true);
            this.width = this.items.get(0).getContentSize().width;
            this.height = this.items.get(0).getContentSize().height;
            group.setSize(width, height);
        }

        group.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (CCMenuItemToggle.this.items.isEmpty()) return;
                setSelectedIndex((selectedIndex + 1) % CCMenuItemToggle.this.items.size());
                invokeSelector();
            }
        });
    }

    public static CCMenuItemToggle item(Object target, String selector, CCNode... items) {
        return new CCMenuItemToggle(target, selector, items);
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < items.size()) {
            items.get(selectedIndex).setVisible(false);
            selectedIndex = index;
            items.get(selectedIndex).setVisible(true);
        }
    }

    private void invokeSelector() {
        if (target != null && selector != null) {
            try {
                try {
                    Method m = target.getClass().getDeclaredMethod(selector, Object.class);
                    m.setAccessible(true);
                    m.invoke(target, this);
                } catch (NoSuchMethodException e) {
                    Method m = target.getClass().getDeclaredMethod(selector);
                    m.setAccessible(true);
                    m.invoke(target);
                }
            } catch (Exception e) {
                System.err.println("Toggle menu item error: " + selector);
            }
        }
    }
}
