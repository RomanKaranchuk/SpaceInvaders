package com.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.invaders.AudioManager;
import com.invaders.camera.OrthoCamera;
import com.invaders.entity.EntityManager;

/**
 * Created by NotePad.by on 28.05.2016.
 */
public class GameScreen extends Screen {

    private OrthoCamera camera;
    private static EntityManager entityManager;
    public static EntityManager getEntityManager(){
        return entityManager;
    }

    public GameScreen(){

    }
    public GameScreen(EntityManager eM, OrthoCamera camera){
        this.camera = camera;
        entityManager = eM;
    }

    @Override
    public void create() {
        if (camera == null || entityManager == null) {
            camera = new OrthoCamera();
            entityManager = new EntityManager(20, camera);
        }
    }

    @Override
    public void update() {
        camera.resize();
        camera.update();
        entityManager.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            AudioManager.getCurrentMusic().pause();
            ScreenManager.getCurrentScreen().setPaused(true);
            ScreenManager.setScreen(new MenuOrResumeScreen());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        entityManager.render(sb);
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
