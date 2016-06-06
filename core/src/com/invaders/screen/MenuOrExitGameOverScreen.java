package com.invaders.screen;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.TextureManager;
import com.invaders.camera.OrthoCamera;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class MenuOrExitGameOverScreen extends Screen{

    private OrthoCamera camera;
    private Texture texture;
    private BitmapFont questFont,font;
    private String[] selectItems;
    private int currentItem = 0;

    public MenuOrExitGameOverScreen(boolean won){
        if (won)
            texture = TextureManager.GAME_WON;
        else
            texture = TextureManager.GAME_OVER;

    }

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();
        questFont = new BitmapFont();
        font = new BitmapFont();
        selectItems = new String[] {"OK","NO"};
        AudioManager.setMusic(AudioManager.menuTheme);

    }

    @Override
    public void update() {
        if (!AudioManager.getCurrentMusic().isPlaying())
            AudioManager.setMusic(AudioManager.menuTheme);
        camera.resize();
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if (currentItem > 0) {
                AudioManager.setSound(AudioManager.selectMenu, true);
                currentItem--;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (currentItem < selectItems.length-1) {
                AudioManager.setSound(AudioManager.selectMenu, true);
                currentItem++;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            select();
        }
    }
    private void select(){
        if (currentItem == 0) {
            AudioManager.getCurrentMusic().stop();
            ScreenManager.setScreen(new MenuScreen());
        } else if (currentItem == 1){

            Gdx.app.exit();
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        questFont.draw(sb,"Do you want go to menu Space Invaders?",MainGame.WIDTH/3, 650);
        sb.draw(texture, MainGame.WIDTH / 2 - texture.getWidth() / 2 , MainGame.HEIGHT / 2 - texture.getHeight()/2);
        for (int i = 0; i <selectItems.length; i++){
            if (currentItem == i)
                font.setColor(Color.RED);
            else
                font.setColor(Color.WHITE);
            font.draw(sb,selectItems[i], MainGame.WIDTH/2 + 70*i, 600);
        }
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        camera = new OrthoCamera();
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
