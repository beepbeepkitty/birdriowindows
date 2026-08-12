package android.media;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import android.app.Activity;

public class MediaPlayer {
    private Music music;

    public static MediaPlayer create(Activity activity, int resId) {
        String path = "";
        switch(resId) {
            case 1: path = "sound/menu.ogg"; break;
            case 2: path = "sound/game.ogg"; break;
            case 3: path = "sound/collide.ogg"; break;
            case 4: path = "sound/jump.ogg"; break;
            case 5: path = "sound/long_jump.ogg"; break;
            case 6: path = "sound/speed_down.ogg"; break;
            case 7: path = "sound/speed_up.ogg"; break;
            case 8: path = "sound/direction_sign.ogg"; break;
            case 9: path = "sound/menu_click.ogg"; break;
            case 10: path = "sound/collect.ogg"; break;
        }
        return create(activity, path);
    }
    
    public static MediaPlayer create(Activity activity, String path) {
        MediaPlayer mp = new MediaPlayer();
        try {
            if (path != null && !path.isEmpty()) {
                mp.music = Gdx.audio.newMusic(Gdx.files.internal(path));
            }
        } catch (Exception e) {
            System.err.println("Error loading sound: " + path);
        }
        return mp;
    }

    public void setLooping(boolean looping) {
        if (music != null) music.setLooping(looping);
    }

    public void start() {
        if (music != null) music.play();
    }

    public void pause() {
        if (music != null) music.pause();
    }

    public void stop() {
        if (music != null) music.stop();
    }
    
    public boolean isPlaying() {
        return music != null && music.isPlaying();
    }
}
