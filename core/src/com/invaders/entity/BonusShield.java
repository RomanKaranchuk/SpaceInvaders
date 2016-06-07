package com.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.invaders.TextureManager;

/**
 * Created by NotePad.by on 07.06.2016.
 */
public class BonusShield extends Entity{
    private static final int        FRAME_COLS = 5;         // #1
    private static final int        FRAME_ROWS = 4;         // #2

    private Animation animation;          // #3
    private Texture sheet;              // #4
    private TextureRegion[]                 frames;             // #5
    private TextureRegion currentFrame;           // #7

    public TextureRegion getCurrentFrame(){
        return this.currentFrame;
    }
    public void setCurrentFrame(TextureRegion currentFrame){
        this.currentFrame = currentFrame;
    }
    public Animation getAnimation(){
        return this.animation;
    }
    public void setAnimation(float freqFrames, TextureRegion[] frames){
        this.animation = new Animation(freqFrames, frames);
    }
    public BonusShield(Vector2 pos, Vector2 dir){
        super(TextureManager.BONUS_SHIELD, pos, dir);
        sheet = TextureManager.BONUS_SHIELD_SHEET; // #9
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth()/FRAME_COLS,
                sheet.getHeight()/FRAME_ROWS);              // #10
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(1f/30f, frames);      // #11
    }
    @Override
    public void update() {
        pos.add(direction);
    }
}
