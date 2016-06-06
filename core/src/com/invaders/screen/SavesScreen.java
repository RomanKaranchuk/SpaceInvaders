package com.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.SerializationEntityManager;
import com.invaders.camera.OrthoCamera;
import com.invaders.entity.EntityManager;

import java.util.ArrayList;

/**
 * Created by NotePad.by on 03.06.2016.
 */
public class SavesScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font, titleFont;
    private ArrayList<String> menuItems;
    private int currentItem = 0;
    private ArrayList<EntityManager> entityManagers;

    @Override
    public void create() {
        camera = new OrthoCamera();
        font = new BitmapFont();
        titleFont = new BitmapFont();
        menuItems = new ArrayList<String>();
        entityManagers = SerializationEntityManager.Deserializate();
        for (EntityManager em : entityManagers){
            menuItems.add("Save "+Integer.toString(entityManagers.size()-menuItems.size()));
        }
    }

    @Override
    public void update() {
        camera.resize();
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if (currentItem > 0) {
                AudioManager.setSound(AudioManager.selectMenu, true);
                currentItem--;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (currentItem < menuItems.size()-1) {
                AudioManager.setSound(AudioManager.selectMenu, true);
                currentItem++;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            select();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            ScreenManager.getOldScreen().setPaused(false);
            ScreenManager.setScreen(ScreenManager.getOldScreen());
        }
    }
    private void select(){
        AudioManager.setMusic(AudioManager.mainTheme);
        ScreenManager.setOldScreen(null);
        ScreenManager.setScreen(new GameScreen(entityManagers.get(currentItem),
                entityManagers.get(currentItem).getCamera()));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        for (int i = menuItems.size()-1; i >=0; i--){
            if (currentItem == i) font.setColor(Color.RED);
            else font.setColor(Color.WHITE);
            font.draw(sb,menuItems.get(i),MainGame.WIDTH/2, 480-35*i);
        }
        titleFont.draw(sb,"SELECT SAVE", MainGame.WIDTH/3, 525);
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
