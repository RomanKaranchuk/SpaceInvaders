package com.invaders.entity;

import com.badlogic.gdx.math.Vector2;
import com.invaders.AudioManager;
import com.invaders.TextureManager;

/**
 * Created by NotePad.by on 06.06.2016.
 */
public class BonusBullets extends Entity{
    private float lifeTime = 5f;
    private float bornTime;

    public float getBornTime(){return this.bornTime;}
    public void setBornTime(float bornTime){this.bornTime = bornTime;}
    public float getLifeTime(){return this.lifeTime;}
    public BonusBullets(Vector2 pos, Vector2 dir) {
        super(TextureManager.BONUS_BULLETS, pos, dir);
    }

    @Override
    public void update() {
        pos.add(direction);
    }
}
