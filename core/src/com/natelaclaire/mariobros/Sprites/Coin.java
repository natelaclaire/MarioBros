package com.natelaclaire.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.natelaclaire.mariobros.Items.ItemDef;
import com.natelaclaire.mariobros.Items.Mushroom;
import com.natelaclaire.mariobros.MarioBros;
import com.natelaclaire.mariobros.Scenes.Hud;
import com.natelaclaire.mariobros.Screens.PlayScreen;

import java.util.Random;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    private boolean blankCoin;
    private boolean spawnsMushroom;
    private Random random;

    public boolean getRandomBoolean(float p){
        return random.nextFloat() < p;
    }

    public Coin(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioBros.COIN_BIT);
        random = new Random();
        blankCoin = getRandomBoolean(0.8f);
        spawnsMushroom = getRandomBoolean(0.6f);
    }

    @Override
    public void onHeadHit(Mario mario) {
        if (blankCoin) {
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        } else {
            if (spawnsMushroom) {
                screen.spawnItem(
                        new ItemDef(
                                new Vector2(
                                        body.getPosition().x,
                                        body.getPosition().y + 16 / MarioBros.PPM
                                ),
                                Mushroom.class
                        )
                );
                MarioBros.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            } else {
                MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();
            }
            Hud.addScore(100);
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));

    }
}
