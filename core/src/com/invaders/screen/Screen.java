package com.invaders.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by NotePad.by on 28.05.2016.
 */
public abstract class Screen {
    public void setPaused(boolean paused){
        this.paused = paused;
    }
    public boolean getPaused(){
        return this.paused;
    }
    private boolean paused = false;
    public abstract void create();
    public abstract void update();
    public abstract void render(SpriteBatch sb);
    public abstract void resize(int width, int height);
    public abstract void dispose();
    public abstract void pause();
    public abstract void resume();
}
