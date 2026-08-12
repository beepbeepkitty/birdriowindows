package bb.hoppingbird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

public class BirdRioGame extends Game {
    
    @Override
    public void create() {
        CCDirector director = CCDirector.sharedDirector();
        director.setGdxGame(this);
        
        // set display info
        G.display_w = 1280;
        G.display_h = 800;
        G.scale = 1.0f;
        G.width = 1280;
        G.height = 800;
        
        // get data
        SharedPreferences sp = director.getActivity().getSharedPreferences("GameInfo", 0);
        G.music = sp.getBoolean("music", true);
        G.sound = sp.getBoolean("sound", true);
        
        // create sound
        Activity activity = director.getActivity();
        G.soundMenu = MediaPlayer.create(activity, R.raw.menu);
        G.soundMenu.setLooping(true);
        G.soundGame = MediaPlayer.create(activity, R.raw.game);
        G.soundGame.setLooping(true);
        G.soundCollide = MediaPlayer.create(activity, R.raw.collide);
        G.soundJump = MediaPlayer.create(activity, R.raw.jump);
        G.soundLongJump = MediaPlayer.create(activity, R.raw.long_jump);
        G.soundSpeedDown = MediaPlayer.create(activity, R.raw.speed_down);
        G.soundSpeedUp = MediaPlayer.create(activity, R.raw.speed_up);
        G.soundDirection = MediaPlayer.create(activity, R.raw.direction_sign);
        G.soundClick = MediaPlayer.create(activity, R.raw.menu_click);
        G.soundCollect = MediaPlayer.create(activity, R.raw.collect);
        G.bgSound = G.soundMenu;
        
        if (G.music) G.bgSound.start();
             
        // show menu
        CCScene scene = CCScene.node();
        scene.addChild(new MenuLayer(true));
        director.runWithScene(scene);
    }

    @Override
    public void render() {
        super.render();
    }
}
