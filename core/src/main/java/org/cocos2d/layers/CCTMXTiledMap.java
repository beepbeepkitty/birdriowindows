package org.cocos2d.layers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import org.cocos2d.nodes.CCNode;

public class CCTMXTiledMap extends CCNode {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Matrix4 combined = new Matrix4();

    public CCTMXTiledMap(String filename) {
        super();
        try {
            map = new TmxMapLoader().load(filename);
            renderer = new OrthogonalTiledMapRenderer(map);
            
            if (map.getLayers().getCount() > 0) {
                TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
                this.width = layer.getWidth() * layer.getTileWidth();
                this.height = layer.getHeight() * layer.getTileHeight();
                group.setSize(width, height);
            }

            group.addActor(new com.badlogic.gdx.scenes.scene2d.Actor() {
                @Override
                public void draw(Batch batch, float parentAlpha) {
                    if (getStage() != null && renderer != null) {
                        batch.end();
                        
                        // Combine camera projection with current batch transform (which includes scrolling)
                        combined.set(batch.getProjectionMatrix()).mul(batch.getTransformMatrix());
                        renderer.setView(combined, 0, 0, width, height);
                        renderer.render();
                        
                        batch.begin();
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Error loading TMX map: " + filename);
        }
    }

    public static CCTMXTiledMap tiledMap(String filename) {
        return new CCTMXTiledMap(filename);
    }
    
    public CCTMXLayer layerNamed(String name) {
        if (map == null) return null;
        return new CCTMXLayer(map.getLayers().get(name), this);
    }

    public org.cocos2d.types.CGSize getTileSize() {
        if (map == null || map.getLayers().getCount() == 0) return new org.cocos2d.types.CGSize(80, 80);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return new org.cocos2d.types.CGSize(layer.getTileWidth(), layer.getTileHeight());
    }

    public Object propertyNamed(String name) {
        if (map == null) return null;
        return map.getProperties().get(name);
    }
}
