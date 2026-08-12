package org.cocos2d.nodes;

import java.util.ArrayList;
import java.util.List;

public class CCAnimation {
    private String name;
    private float delay;
    private List<String> frames = new ArrayList<>();

    public CCAnimation() {}

    public static CCAnimation animation(String name, float delay) {
        CCAnimation anim = new CCAnimation();
        anim.name = name;
        anim.delay = delay;
        return anim;
    }

    public void addFrame(String filename) {
        frames.add(filename);
    }

    public List<String> getFrames() {
        return frames;
    }

    public float getDelay() {
        return delay;
    }
}
