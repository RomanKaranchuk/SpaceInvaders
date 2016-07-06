package com.invaders.screen;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.SerializationEntityManager;
import com.invaders.SerializationScore;
import com.invaders.camera.OrthoCamera;
import com.invaders.entity.EntityManager;
import com.invaders.entity.Score;

import java.util.ArrayList;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class MenuOrResumeScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font, questFont;
    private String[] selectItems;
    private int currentItem = 1;
    private static int MAX_SAVES = 5;
    private ArrayList<EntityManager> entityManagers;
    @Override
    public void create() {
        camera = new OrthoCamera();
        font = new BitmapFont();
        questFont = new BitmapFont();
        selectItems = new String[] {"OK","NO","SAVE"};
        entityManagers = new ArrayList<EntityManager>();
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
            ScreenManager.setOldScreen(null);
            ScreenManager.setScreen(new MenuScreen());
        } else if (currentItem == 1){
            AudioManager.setMusic(AudioManager.mainTheme);
            ScreenManager.getOldScreen().setPaused(false);
            ScreenManager.setScreen(ScreenManager.getOldScreen());
        } else if (currentItem == 2){
            toSerializateEntityManager();
            ScreenManager.setOldScreen(null);
            ScreenManager.setScreen(new MenuScreen());
        }
    }
    public void toSerializateEntityManager(){
        entityManagers.clear();
        entityManagers.add(GameScreen.getEntityManager());
        if (SerializationEntityManager.Deserializate() != null) {
            for (EntityManager em : SerializationEntityManager.Deserializate()) {
                if (entityManagers.size() >= MAX_SAVES)
                    break;
                entityManagers.add(em);
            }
        }
        SerializationEntityManager.Serializate(entityManagers);
    }

    @Override
    public void render(SpriteBatch sb) {
        Matrix4 normalProjection = new Matrix4().setToOrtho2D(0,0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        sb.setProjectionMatrix(normalProjection);
        sb.begin();
        questFont.draw(sb,"Do you want go to menu Space Invaders?",
                Gdx.graphics.getWidth()/3,
                Gdx.graphics.getHeight()*0.75f);
        for (int i = 0; i <selectItems.length; i++){
            if (currentItem == i)
                font.setColor(Color.RED);
            else
                font.setColor(Color.WHITE);
            font.draw(sb,selectItems[i],
                    Gdx.graphics.getWidth()/2 + 70*i,
                    Gdx.graphics.getHeight()*0.7f);
        }
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
