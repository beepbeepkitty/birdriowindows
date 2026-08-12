package bb.hoppingbird;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXLayer;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class LevelMap extends CCTMXTiledMap {
    private static final int[] tileKinds = {0, 1, 2, 3, 4, 4, 4, 4, 5, 5, 5, 6, 6, 6, 6, 6, 6, 0, 7, 8, 0, 9, 10, 11, 12, 0, 7, 8, 0, 9, 10, 11, 12};
    private CCAction _aniFly;
    private CCAction _aniRun;
    private CCSprite _bird;
    private int _birdDir;
    private float _birdGravity;
    private float _birdVx;
    private float _birdVy;
    private GameLayer _game;
    private boolean _isJumping;
    private CCTMXLayer _layer;
    private CGSize _mapSize;
    private CGSize _tileSize;

    public LevelMap(GameLayer game, String tmxFile) {
        super(tmxFile);
        this._game = game;
        createBird();
        this._layer = layerNamed("Level");
        if (this._layer == null) return;
        this._mapSize = this._layer.layerSize;
        this._tileSize = getTileSize();
        
        for (int y = 0; y < (int)this._mapSize.height; y++) {
            for (int x = 0; x < (int)this._mapSize.width; x++) {
                int gid = this._layer.tileGIDAt(CGPoint.ccp(x, y));
                int kind = tileKinds[gid >> 24];
                if (kind == 3) {
                    CCSprite star = this._layer.tileAt(CGPoint.ccp(x, y));
                    if (star != null) {
                        star.setAnchorPoint(0.5f, 0.5f);
                        star.runAction(CCRepeatForever.action(CCSequence.actions(CCScaleTo.action(0.5f, 1.2f), CCScaleTo.action(0.5f, 1.0f))));
                    }
                } else if (kind != 6 && kind != 5 && kind == 1) {
                    this._layer.removeTileAt(CGPoint.ccp(x, y));
                    Object str = propertyNamed("StartDirection");
                    setBirdDir((str == null || !str.equals("1")) ? 1 : -1);
                    this._bird.setPosition(((x + 0.5f) - (this._birdDir * 1.3f)) * this._tileSize.width, ((this._mapSize.height - y) - 0.5f) * this._tileSize.height);
                }
            }
        }
        upadtePosition();
    }

    public void createBird() {
        CCAnimation animation = CCAnimation.animation("run", 0.02f);
        for (int i = 0; i < 16; i++) animation.addFrame(String.format("game/bird/run%d.png", i));
        this._aniRun = CCRepeatForever.action(CCAnimate.action(animation));
        CCAnimation animation2 = CCAnimation.animation("fly", 0.02f);
        for (int i2 = 0; i2 < 4; i2++) animation2.addFrame(String.format("game/bird/fly%d.png", i2));
        this._aniFly = CCRepeatForever.action(CCAnimate.action(animation2));
        this._bird = CCSprite.sprite("game/bird/run0.png");
        this._bird.setAnchorPoint(0.5f, 0.46f);
        addChild(this._bird, 2);
        this._bird.runAction(this._aniRun);
        this._isJumping = false;
        this._birdVx = G.normalVx;
        this._birdGravity = G.gravity;
        this._birdVy = 0.0f;
    }

    public CGPoint getTilePos(CGPoint pos) {
        int x = (int) (pos.x / this._tileSize.width);
        int y = (int) ((this._mapSize.height - ((int) (pos.y / this._tileSize.height))) - 1.0f);
        return CGPoint.make(x, y);
    }

    public int getTileKind(CGPoint pos) {
        if (pos.x < 0.0f || pos.x >= this._mapSize.width) {
            return (pos.x < -1.0f || pos.x > this._mapSize.width) ? 13 : 14;
        }
        if (pos.y < 0.0f) return 0;
        if (pos.y >= this._mapSize.height) return 15;
        return tileKinds[this._layer.tileGIDAt(pos) >> 24];
    }

    public void setBirdDir(int dir) {
        this._birdDir = dir;
        this._bird.setScaleX(this._birdDir);
    }

    public void birdJump() {
        if (this._birdVy == 0.0f) {
            CGPoint topTilePos = getTilePos(CGPoint.ccp(this._bird.getPosition().x, this._bird.getPosition().y + (this._tileSize.height * 0.5f)));
            if ((this._layer.tileGIDAt(topTilePos) >> 24) != 2) {
                this._birdVy = G.jumpVy;
                if (!this._isJumping) {
                    this._isJumping = true;
                    this._bird.stopAction(this._aniRun);
                    this._bird.runAction(this._aniFly);
                }
            }
        }
    }

    public void update(float dt) {
        if (this._game.state != G.gsRun) return;
        
        this._birdVy -= this._birdGravity;
        CGPoint birdPos = CGPoint.ccp(this._bird.getPosition().x + (this._birdDir * this._birdVx), this._bird.getPosition().y + this._birdVy);
        CGPoint bottomTilePos = getTilePos(CGPoint.ccp(birdPos.x, birdPos.y - (this._tileSize.height * 0.5f)));
        int bottomKind = getTileKind(bottomTilePos);
        
        if (bottomKind == 2 || bottomKind == 14 || bottomKind == 5) {
            // Precise snap to tile top
            birdPos.y = ((this._mapSize.height - bottomTilePos.y) + 0.5f) * this._tileSize.height;
            this._bird.setPosition(birdPos);
            
            if (bottomKind == 5) {
                if (G.sound) G.soundLongJump.start();
                this._birdVy = 2.0f * G.jumpVy;
                this._birdGravity = 2.0f * G.gravity;
                if (!this._isJumping) {
                    this._isJumping = true;
                    this._bird.stopAction(this._aniRun);
                    this._bird.runAction(this._aniFly);
                }
            } else {
                if (this._isJumping) {
                    this._isJumping = false;
                    this._bird.stopAction(this._aniFly);
                    this._bird.runAction(this._aniRun);
                    this._birdGravity = G.gravity;
                }
                this._birdVy = 0.0f;
            }
            CGPoint centerTilePos = getTilePos(birdPos);
            if (getTileKind(centerTilePos) == 3) gotCherry(centerTilePos);
        } else {
            this._bird.setPosition(birdPos);
            if (bottomKind == 6 || bottomKind == 4 || bottomKind == 15) {
                gameOver();
                return;
            } else if (bottomKind == 13) {
                this._game.gameCompleted();
                return;
            } else if (bottomKind == 3) {
                gotCherry(bottomTilePos);
            }
        }
        
        CGPoint frontTilePos = getTilePos(CGPoint.ccp(birdPos.x + (this._birdDir * this._tileSize.width * 0.5f), birdPos.y));
        int frontKind = getTileKind(frontTilePos);
        if (frontKind == 2 || frontKind == 4) {
            gameOver();
            return;
        }
        
        CGPoint tilePos = getTilePos(birdPos);
        int kind;
        while (true) {
            kind = getTileKind(tilePos);
            if (kind != 0) break;
            tilePos.y += 1.0f;
            if (tilePos.y >= this._mapSize.height) break;
        }
        
        switch (kind) {
            case 7: if (this._birdDir != -1) { if (G.sound) G.soundDirection.start(); setBirdDir(-1); } break;
            case 8: if (this._birdDir != 1) { if (G.sound) G.soundDirection.start(); setBirdDir(1); } break;
            case 9: if (this._birdDir == 1) { if (G.sound) G.soundSpeedDown.start(); this._birdVx = G.normalVx; } break;
            case 10: if (this._birdDir == 1) { if (G.sound) G.soundSpeedUp.start(); this._birdVx = G.fastVx; } break;
            case 11: if (this._birdDir == -1) { if (G.sound) G.soundSpeedDown.start(); this._birdVx = G.normalVx; } break;
            case 12: if (this._birdDir == -1) { if (G.sound) G.soundSpeedUp.start(); this._birdVx = G.fastVx; } break;
        }
        upadtePosition();
    }

    public void upadtePosition() {
        float x = (G.width * (0.5f - (this._birdDir * 0.25f))) - this._bird.getPosition().x;
        if (x > 0.0f) x = 0.0f;
        else if (width + x < G.width) x = G.width - width;
        float y = ((G.height * 0.5f) - (this._tileSize.height * 0.5f)) - this._bird.getPosition().y;
        setPosition(x, y);
    }

    public void gotCherry(CGPoint pos) {
        if (this._game.state != G.gsRun) return;
        if (G.sound) G.soundCollect.start();
        
        CCSprite star = this._layer.tileAt(pos);
        if (star == null) return;
        CGPoint cherryPos = star.getPosition();
        
        CCSprite cherry = new CCSprite("game/cherry.png");
        // Convert map local position to world space then to game layer space
        cherry.setPosition(this._game.convertToNodeSpace(convertToWorldSpace(cherryPos.x, cherryPos.y)));
        cherry.runAction(CCFadeOut.action(0.99f));
        cherry.runAction(CCSequence.actions(
            CCMoveTo.action(1.0f, CGPoint.ccp(60.0f, G.height - 50.0f)), 
            CCCallFunc.action(cherry, "removeSelf")
        ));
        this._game.addChild(cherry);
        this._layer.removeTileAt(pos);
        this._game.setScore(this._game.getScore() + 1);
    }

    public void gameOver() {
        if (this._game.state == G.gsRun) {
            if (G.sound) G.soundCollide.start();
            this._game.state = G.gsPause;
            this._bird.stopAllActions();
            this._game.gameOver();
        }
    }
}
