package com.invaders.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.SerializationScore;
import com.invaders.camera.OrthoCamera;
import com.invaders.entity.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class HighscoreScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font,ctrlFont;
    private ArrayList<Score> scores;
    private String selectItem;
    private Map<Integer, Rectangle> menuItemsRects;
    private ShapeRenderer shapeRenderer;
    private int currentItem = 0;

    @Override
    public void create() {
        camera = new OrthoCamera();
        font = new BitmapFont();
        scores = SerializationScore.Deserializate();
        ctrlFont = new BitmapFont();
        selectItem = "BACK";
        menuItemsRects = new TreeMap<Integer, Rectangle>();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update() {
        camera.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            select();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            AudioManager.getCurrentMusic().pause();
            ScreenManager.getOldScreen().setPaused(false);
            ScreenManager.setScreen(ScreenManager.getOldScreen());
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            for (Map.Entry<Integer, Rectangle> item : menuItemsRects.entrySet()) {
                if (item.getValue().contains(touchPos.x, touchPos.y)) {
                    currentItem = item.getKey();
                    select();
                    AudioManager.setSound(AudioManager.selectMenu, true);
                }
            }
        }
    }
    public void select(){
        if (currentItem == 0){
            AudioManager.setSound(AudioManager.selectMenu, true);
            AudioManager.getCurrentMusic().pause();
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

        shapeRenderer.setProjectionMatrix(normalProjection);
        // Rectangle over menuItems for check click on each items.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 1);
        shapeRenderer.rect(Gdx.graphics.getWidth() / 5,
                Gdx.graphics.getHeight()*0.1f,
                40,
                -40);
        shapeRenderer.end();

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
        font.draw(sb,"HIGHSCORES"+"\n\n"+"ID"+ws+"NAME"+ws+"SCORE"+"\n"+highscores,
                Gdx.graphics.getWidth()/3,
                Gdx.graphics.getHeight()-50);

        menuItemsRects.clear();

        ctrlFont.setColor(Color.RED);
        ctrlFont.draw(sb,
                selectItem,
                Gdx.graphics.getWidth()/5,
                Gdx.graphics.getHeight()*0.1f);
        menuItemsRects.put(0,
                new Rectangle(Gdx.graphics.getWidth()/5,
                        Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.1f,
                        40,
                        40));
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
