package com.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.invaders.TextureManager;

/**
 * Created by NotePad.by on 07.06.2016.
 */
public class Explosion extends Entity{
    private static final int        FRAME_COLS = 5;
    private static final int        FRAME_ROWS = 5;

    private transient Animation animation;
    private transient Texture sheet;
    private transient TextureRegion[] frames;
    private transient TextureRegion currentFrame;
    private float stateTime;

    public TextureRegion getCurrentFrame(){
        return this.currentFrame;
    }
    public void setCurrentFrame(TextureRegion currentFrame){
        this.currentFrame = currentFrame;
    }
    public Animation getAnimation(){
        if (this.animation == null) {
            this.sheet = TextureManager.EXPLOSION;
            TextureRegion[][] tmp = TextureRegion.split(sheet,
                    sheet.getWidth() / FRAME_COLS,
                    sheet.getHeight() / FRAME_ROWS);
            frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
            int index = 0;
            for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    frames[index++] = tmp[i][j];
                }
            }
            this.animation = new Animation(1f / 60f, frames);
        }
        return this.animation;
    }

    public Explosion(Vector2 pos, Vector2 dir){
        super(TextureManager.EXPLOSION, pos, dir);
        sheet = TextureManager.EXPLOSION;
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth()/FRAME_COLS,
                sheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(1f/60f, frames);
        stateTime = 0f;
    }
    public float getStateTime(){
        stateTime += Gdx.graphics.getDeltaTime();
        return this.stateTime;
    }
    public void render(SpriteBatch sb){
        this.setCurrentFrame(this.getAnimation().getKeyFrame(this.getStateTime(),false));
        sb.draw(currentFrame, this.getPosition().x, this.getPosition().y);
    }

    @Override
    public void update() {
        pos.add(direction);
    }
}
