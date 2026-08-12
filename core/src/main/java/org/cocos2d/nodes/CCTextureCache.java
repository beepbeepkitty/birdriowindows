package org.cocos2d.nodes;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class CCTextureCache {
    private static CCTextureCache instance;
    private HashMap<String, Texture> textures = new HashMap<>();

    public static CCTextureCache sharedTextureCache() {
        if (instance == null) instance = new CCTextureCache();
        return instance;
    }

    public Texture addImage(String filename) {
        if (filename == null || filename.isEmpty()) return null;
        if (!textures.containsKey(filename)) {
            try {
                textures.put(filename, new Texture(filename));
            } catch (Exception e) {
                System.err.println("Failed to load texture: " + filename);
                return null;
            }
        }
        return textures.get(filename);
    }
}
