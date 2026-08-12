package org.cocos2d.nodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.cocos2d.layers.CCScene;
import android.app.Activity;

public class CCDirector {
    private static CCDirector instance;
    private Game gdxGame;
    private Activity activity = new Activity();

    public static CCDirector sharedDirector() {
        if (instance == null) instance = new CCDirector();
        return instance;
    }

    public void setGdxGame(Game game) {
        this.gdxGame = game;
    }

    public Activity getActivity() {
        return activity;
    }

    public void runWithScene(CCScene scene) {
        gdxGame.setScreen(scene);
    }

    public void replaceScene(CCScene scene) {
        gdxGame.setScreen(scene);
    }
    
    public void end() {
        Gdx.app.exit();
    }
    
    public void onPause() {}
    public void onResume() {}
    
    public void attachInView(Object view) {}
    public void setAnimationInterval(double interval) {}
}
