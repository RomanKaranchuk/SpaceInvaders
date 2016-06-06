package com.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.SerializationScore;
import com.invaders.camera.OrthoCamera;
import com.invaders.entity.Score;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class HighscoreScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font;
    private ArrayList<Score> scores;


    @Override
    public void create() {
        camera = new OrthoCamera();
        font = new BitmapFont();
        scores = SerializationScore.Deserializate();
    }

    @Override
    public void update() {
        camera.resize();
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            AudioManager.getCurrentMusic().pause();
            ScreenManager.getOldScreen().setPaused(false);
            ScreenManager.setScreen(ScreenManager.getOldScreen());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        String highscores = "";
        String ws = "           ";


        Collections.sort(scores, new Score());
        int i = 1;
        for (Score s : scores){
            s.setId(i++);
        }
        for (Score s : scores){
            highscores += s.getId()+ ws+s.getPlayerName() +ws+ s.getScoreValue() +"\n";
        }
        font.draw(sb,"HIGHSCORES"+"\n\n"+"ID"+ws+"NAME"+ws+"SCORE"+"\n"+highscores, MainGame.WIDTH/3, MainGame.HEIGHT-50);
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
