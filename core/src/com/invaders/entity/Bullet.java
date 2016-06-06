package com.invaders.entity;

import com.badlogic.gdx.math.Vector2;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.TextureManager;

/**
 * Created by NotePad.by on 05.06.2016.
 */
public class Bullet extends Entity implements java.io.Serializable{
    public Bullet(Vector2 pos, Vector2 dir) {
        super(TextureManager.BULLET, pos, dir);
        AudioManager.setSound(AudioManager.shotGun, false);
    }

    @Override
    public void update() {
        pos.add(direction);
    }

    public boolean checkEnd(){
        return pos.y >= MainGame.HEIGHT;
    }
}
