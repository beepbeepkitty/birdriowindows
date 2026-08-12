package android.view;

public class MotionEvent {
    public static final int ACTION_DOWN = 0;
    public static final int ACTION_MOVE = 1;
    public static final int ACTION_UP = 2;
    
    private float x, y;
    private int action;
    
    public MotionEvent(int action, float x, float y) {
        this.action = action;
        this.x = x;
        this.y = y;
    }
    
    public int getAction() { return action; }
    public float getX() { return x; }
    public float getY() { return y; }
}
