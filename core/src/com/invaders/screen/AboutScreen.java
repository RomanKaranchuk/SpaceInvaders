package com.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb,"Roman Karanchuk Production 2016", MainGame.WIDTH/2, MainGame.HEIGHT/2);
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
