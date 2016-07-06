package com.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.camera.OrthoCamera;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class AboutScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font;

    @Override
    public void create() {
        camera = new OrthoCamera();
        font = new BitmapFont();
    }

    @Override
    public void update() {
        camera.resize();
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            ScreenManager.getOldScreen().setPaused(false);
            ScreenManager.setScreen(ScreenManager.getOldScreen());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Matrix4 normalProjection = new Matrix4().setToOrtho2D(0,0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        sb.setProjectionMatrix(normalProjection);
        sb.begin();
        font.draw(sb,"Roman Karanchuk Production 2016",
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
