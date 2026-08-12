package org.cocos2d.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.cocos2d.nodes.CCNode;

public class CCScene extends CCNode implements Screen {
    private Stage stage;

    public CCScene() {
        super();
        // Use FitViewport to maintain aspect ratio and show black bars if needed
        stage = new Stage(new FitViewport(1280, 800)); 
    }

    public static CCScene node() {
        return new CCScene();
    }

    @Override
    public void addChild(CCNode child) {
        if (child == null) return;
        if (child.getGroup().getParent() != null) {
            child.getGroup().remove();
        }
        child.parent = this;
        stage.addActor(child.getGroup());
    }
    
    @Override
    public void addChild(CCNode child, int z) {
        addChild(child);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Ensure stage coordinates match our virtual resolution
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
