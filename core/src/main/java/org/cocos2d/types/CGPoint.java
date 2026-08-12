package org.cocos2d.types;

public class CGPoint {
    public float x, y;
    public CGPoint() { x = 0; y = 0; }
    public CGPoint(float x, float y) { this.x = x; this.y = y; }
    public static CGPoint ccP(float x, float y) { return new CGPoint(x, y); }
    public static CGPoint ccp(float x, float y) { return new CGPoint(x, y); }
    public static CGPoint make(float x, float y) { return new CGPoint(x, y); }
    public static CGPoint zero() { return new CGPoint(0, 0); }
}
