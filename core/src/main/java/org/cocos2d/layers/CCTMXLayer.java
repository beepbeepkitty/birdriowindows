package org.cocos2d.layers;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class CCTMXLayer extends CCNode {
    private MapLayer layer;
    public CGSize layerSize;

    public CCTMXLayer(MapLayer layer, CCTMXTiledMap map) {
        this.layer = layer;
        if (layer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tLayer = (TiledMapTileLayer) layer;
            this.layerSize = new CGSize(tLayer.getWidth(), tLayer.getHeight());
            this.width = tLayer.getWidth() * tLayer.getTileWidth();
            this.height = tLayer.getHeight() * tLayer.getTileHeight();
        }
    }
    
    public int tileGIDAt(CGPoint p) {
        if (layer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tLayer = (TiledMapTileLayer) layer;
            // Original logic uses top-down Y (0 at top)
            // LibGDX uses bottom-up Y (0 at bottom)
            int x = (int) p.x;
            int y = (int) (tLayer.getHeight() - 1 - p.y);
            if (x < 0 || x >= tLayer.getWidth() || y < 0 || y >= tLayer.getHeight()) return 0;
            
            TiledMapTileLayer.Cell cell = tLayer.getCell(x, y);
            if (cell != null && cell.getTile() != null) {
                // Cocos2d-android expects GID in the top byte
                return cell.getTile().getId() << 24;
            }
        }
        return 0;
    }

    public CCSprite tileAt(CGPoint p) {
        if (layer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tLayer = (TiledMapTileLayer) layer;
            int x = (int) p.x;
            int y = (int) (tLayer.getHeight() - 1 - p.y);
            if (x < 0 || x >= tLayer.getWidth() || y < 0 || y >= tLayer.getHeight()) return null;
            
            TiledMapTileLayer.Cell cell = tLayer.getCell(x, y);
            if (cell != null) {
                // Return a proxy sprite or a real one? 
                // The original game uses it to get position and run actions.
                CCSprite sprite = CCSprite.sprite("game/score.png");
                // Set position in world coordinates (bottom-up)
                sprite.setPosition((x + 0.5f) * tLayer.getTileWidth(), (y + 0.5f) * tLayer.getTileHeight());
                return sprite;
            }
        }
        return null;
    }

    public void removeTileAt(CGPoint p) {
        if (layer instanceof TiledMapTileLayer) {
            TiledMapTileLayer tLayer = (TiledMapTileLayer) layer;
            int x = (int) p.x;
            int y = (int) (tLayer.getHeight() - 1 - p.y);
            if (x >= 0 && x < tLayer.getWidth() && y >= 0 && y < tLayer.getHeight()) {
                tLayer.setCell(x, y, null);
            }
        }
    }
}
