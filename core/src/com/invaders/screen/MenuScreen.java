package com.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.camera.OrthoCamera;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class MenuScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font, titleFont;
    private String[] menuItems;
    private int currentItem = 0;


    @Override
    public void create() {
        camera = new OrthoCamera();
        font = new BitmapFont();
        titleFont = new BitmapFont();
        menuItems = new String[] {"PLAY","LOAD GAME", "HIGHSCORES", "ABOUT", "QUIT"};
        AudioManager.setMusic(AudioManager.menuTheme);
        AudioManager.getCurrentMusic().stop();// delete
    }

    @Override
    public void update() {
        if (!AudioManager.getCurrentMusic().isPlaying())
            AudioManager.setMusic(AudioManager.menuTheme);
        camera.resize();
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if (currentItem > 0) {
                AudioManager.setSound(AudioManager.selectMenu,true);
                currentItem--;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (currentItem < menuItems.length-1) {
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
            Gdx.app.exit();
        }
    }

    private void select(){
        if (currentItem == 0) {
            ScreenManager.setScreen( new GameScreen());
        } else if (currentItem == 1){
            ScreenManager.getCurrentScreen().setPaused(true);
            ScreenManager.setScreen(new SavesScreen());
        }
        else if (currentItem == 2){
            AudioManager.setMusic(AudioManager.highscoreTheme);
            AudioManager.getCurrentMusic().setLooping(true);
            ScreenManager.getCurrentScreen().setPaused(true);
            ScreenManager.setScreen(new HighscoreScreen());
        } else if (currentItem == 3){
            ScreenManager.getCurrentScreen().setPaused(true);
            ScreenManager.setScreen(new AboutScreen());
        } else if (currentItem == 4){
            Gdx.app.exit();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        for (int i = 0; i <menuItems.length; i++){
            if (currentItem == i) font.setColor(Color.RED);
            else font.setColor(Color.WHITE);
            font.draw(sb,menuItems[i],MainGame.WIDTH/2, 480-35*i);
        }

        titleFont.draw(sb,"SPACE INVADERS", MainGame.WIDTH/2, 525);
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
