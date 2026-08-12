package org.cocos2d.types;

public class ccColor4B {
    public int r, g, b, a;
    public ccColor4B(int r, int g, int b, int a) {
        this.r = r; this.g = g; this.b = b; this.a = a;
    }
    public static ccColor4B ccc4(int r, int g, int b, int a) {
        return new ccColor4B(r, g, b, a);
    }
}
