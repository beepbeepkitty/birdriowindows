package org.cocos2d.types;

public class CGSize {
    public float width, height;
    public CGSize() { width = 0; height = 0; }
    public CGSize(float w, float h) { this.width = w; this.height = h; }
    public static CGSize make(float w, float h) { return new CGSize(w, h); }
}
