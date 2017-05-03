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
import com.invaders.SerializationEntityManager;
import com.invaders.camera.OrthoCamera;
import com.invaders.entity.EntityManager;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by NotePad.by on 03.06.2016.
 */
public class SavesScreen extends Screen {
    private OrthoCamera camera;
    private BitmapFont font, titleFont;
    private ArrayList<String> menuItems;
    private int currentItem = 0;
    private ArrayList<EntityManager> entityManagers;
    private Map<Integer, Rectangle> menuItemsRects;
    private ShapeRenderer shapeRenderer;
    private String backItem;

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
        menuItems.add("BACK");
        menuItemsRects = new TreeMap<Integer, Rectangle>();
        shapeRenderer = new ShapeRenderer();
        backItem = "BACK";
    }

    @Override
    public void update() {
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
    private void select(){

        if (currentItem != menuItems.size()-1) {
            ScreenManager.setOldScreen(null);
            ScreenManager.setScreen(new GameScreen(entityManagers.get(currentItem),
                    entityManagers.get(currentItem).getCamera()));
            AudioManager.setMusic(AudioManager.mainTheme);
        } else {
            AudioManager.setMusic(AudioManager.menuTheme);
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
        for (int i = 0 ; i< menuItems.size()-1; i++) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 1);
            shapeRenderer.rect(Gdx.graphics.getWidth() / 2,
                    Gdx.graphics.getHeight()*0.65f - i*35,
                    90,
                    -20);
            shapeRenderer.end();
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 1);
        shapeRenderer.rect(Gdx.graphics.getWidth() / 5,
                Gdx.graphics.getHeight()*0.1f,
                40,
                -40);
        shapeRenderer.end();


        sb.begin();
        menuItemsRects.clear();
        for (int i = menuItems.size()-2; i >=0; i--){
            if (currentItem == i)
                font.setColor(Color.RED);
            else
                font.setColor(Color.WHITE);
            font.draw(sb,
                    menuItems.get(i),
                    Gdx.graphics.getWidth()/2,
                    Gdx.graphics.getHeight()*0.65f-35*i);
            menuItemsRects.put(i,
                    new Rectangle(Gdx.graphics.getWidth()/2,
                            Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()*0.65f - i*35) ,
                            90,
                            20));
        }
        if (currentItem == menuItems.size()-1)
            font.setColor(Color.RED);
        else
            font.setColor(Color.WHITE);
        font.draw(sb,
                menuItems.get(menuItems.size()-1),
                Gdx.graphics.getWidth()/5,
                Gdx.graphics.getHeight()*0.1f);
        menuItemsRects.put(menuItems.size()-1,
                new Rectangle(Gdx.graphics.getWidth()/5,
                        Gdx.graphics.getHeight()-
                                (Gdx.graphics.getHeight()*0.1f) ,
                        40,
                        40));

        titleFont.draw(sb,
                "SELECT SAVE",
                Gdx.graphics.getWidth()/3,
                Gdx.graphics.getHeight()*0.65f+35);
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
