package com.invaders.entity;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.TextureManager;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class Missile extends Entity implements java.io.Serializable{

    public Missile(Vector2 pos, Vector2 dir) {
        super(TextureManager.MISSILE, pos, dir);
        AudioManager.setSound(AudioManager.shotMissile, false);
    }

    @Override
    public void update() {
        pos.add(direction);
    }

    public boolean checkEnd(){
        return pos.y >= MainGame.HEIGHT;
    }
}
