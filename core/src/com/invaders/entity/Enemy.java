package com.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.invaders.MainGame;
import com.invaders.TextureManager;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class Enemy extends Entity implements java.io.Serializable{
    private float stateTime = 0;
    private int damage = 100;

    public Enemy(Vector2 pos, Vector2 direction){
        super(TextureManager.ENEMY, pos, direction);
    }
    public int getDamage(){
        return this.damage;
    }
    public void setDamage(int damage){
        this.damage -= damage;
    }

    @Override
    public void render(SpriteBatch batch){
        batch.draw(texture,pos.x, pos.y);
    }
    @Override
    public void update() {
        pos.add(direction);

        if (pos.y <= -TextureManager.ENEMY.getHeight()){
            float x = MathUtils.random(0, Gdx.graphics.getWidth() - TextureManager.ENEMY.getWidth());
            pos.set(x, Gdx.graphics.getHeight());
        }
    }
}
