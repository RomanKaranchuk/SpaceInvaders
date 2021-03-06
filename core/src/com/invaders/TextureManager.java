package com.invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class TextureManager {

    public static Texture BONUS_SHIELD= new Texture(Gdx.files.internal("shield20x24.png"));
    public static Texture BONUS_SHIELD_SHEET = new Texture(Gdx.files.internal("shieldSheetS.png"));
    public static Texture EXPLOSION = new Texture(Gdx.files.internal("sprite-explosion.png"));
    public static Texture PLAYER = new Texture(Gdx.files.internal("playerS.png"));
    public static Texture MISSILE = new Texture(Gdx.files.internal("missile.png"));
    public static Texture ENEMY = new Texture(Gdx.files.internal("enemyS.png"));
    public static Texture GAME_OVER = new Texture(Gdx.files.internal("fail.png"));
    public static Texture GAME_WON = new Texture(Gdx.files.internal("gamewon.png"));
    public static Texture BULLET = new Texture(Gdx.files.internal("bullet8x18.png"));
    public static Texture BONUS_BULLETS = new Texture(Gdx.files.internal("bonus_patronsS.png"));
    public static Texture EMPTY = new Texture(Gdx.files.internal("empty.png"));
}
