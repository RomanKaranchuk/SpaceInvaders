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
import com.badlogic.gdx.utils.Array;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.camera.OrthoCamera;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class MenuScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font, titleFont;
    private String[] menuItems;
    private int currentItem = 0;
    private Map<Integer, Rectangle> menuItemsRects;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        camera = new OrthoCamera();
        font = new BitmapFont();
        titleFont = new BitmapFont();

        menuItems = new String[] {"PLAY","LOAD GAME","HIGHSCORES","ABOUT","QUIT"};
        menuItemsRects = new TreeMap<Integer, Rectangle>();
        AudioManager.setMusic(AudioManager.menuTheme);
        AudioManager.getCurrentMusic().stop();// delete

        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void update() {
        camera.resize();
        camera.update();
        if (!AudioManager.getCurrentMusic().isPlaying()) {
//            AudioManager.setMusic(AudioManager.menuTheme);
        }
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
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            for (Map.Entry<Integer, Rectangle> item : menuItemsRects.entrySet()) {
                if (item.getValue().contains(touchPos.x, touchPos.y) &&
                    currentItem != item.getKey()) {
                    currentItem = item.getKey();
//                    select();
                    AudioManager.setSound(AudioManager.selectMenu, true);
                }
            }
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
        Matrix4 normalProjection = new Matrix4().setToOrtho2D(0,0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        sb.setProjectionMatrix(normalProjection);
        shapeRenderer.setProjectionMatrix(normalProjection);
        // Rectangle over menuItems for check click on each items.
        for (int i = 0 ; i< 5; i++) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 1);
            shapeRenderer.rect(Gdx.graphics.getWidth() / 2 - 20,
                    Gdx.graphics.getHeight()*0.55f - i*35-15,//Gdx.graphics.getHeight()*0.05f,
                    90,
                    20);
//            System.out.println("Check =" +  Gdx.graphics.getHeight());
            shapeRenderer.end();
        }


        sb.begin();
        menuItemsRects.clear();
        for (int i = 0; i <menuItems.length; i++){
            if (currentItem == i) {
                font.setColor(Color.RED);
            }
            else {
                font.setColor(Color.WHITE);
            }

            font.draw(sb,
                    menuItems[i],
                    Gdx.graphics.getWidth()/2-20,
                    Gdx.graphics.getHeight()*0.55f - i*35);//Gdx.graphics.getHeight()*0.05f);

            menuItemsRects.put(menuItems.length-i-1,
                    new Rectangle(Gdx.graphics.getWidth()/2-20,
                            Gdx.graphics.getHeight()*0.55f - i*35 + 60,//Gdx.graphics.getHeight()*0.05f,
                    90,
                    20));
        }

        titleFont.draw(sb,
                "SPACE INVADERS",
                Gdx.graphics.getWidth()/2-20,
                Gdx.graphics.getHeight()*0.55f + 35);
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
