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
import com.invaders.camera.OrthoCamera;
import com.invaders.entity.EntityManager;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by NotePad.by on 28.05.2016.
 */
public class GameScreen extends Screen {

    private OrthoCamera camera;
    private static EntityManager entityManager;
    private String backItem;
    private BitmapFont font;
    private int currentItem = 0;
    private Map<Integer, Rectangle> menuItemsRects;
    private ShapeRenderer shapeRenderer;


    public static EntityManager getEntityManager(){
        return entityManager;
    }


    public GameScreen(){
        font = new BitmapFont();
        backItem = "PAUSE";
        menuItemsRects = new TreeMap<Integer, Rectangle>();
        shapeRenderer = new ShapeRenderer();
    }
    public GameScreen(EntityManager eM, OrthoCamera camera){
        this.camera = camera;
        entityManager = eM;

        font = new BitmapFont();
        backItem = "PAUSE";
        menuItemsRects = new TreeMap<Integer, Rectangle>();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void create() {
        if (camera == null || entityManager == null) {
            camera = new OrthoCamera();
            entityManager = new EntityManager(10, camera);
        }
    }

    @Override
    public void update() {
        camera.update();
        entityManager.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            AudioManager.getCurrentMusic().pause();
            ScreenManager.getCurrentScreen().setPaused(true);
            ScreenManager.setScreen(new MenuOrResumeScreen());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            AudioManager.setSound(AudioManager.selectMenu, true);
            select();
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
            AudioManager.getCurrentMusic().pause();
            ScreenManager.getCurrentScreen().setPaused(true);
            ScreenManager.setScreen(new MenuOrResumeScreen());
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
        shapeRenderer.rect(Gdx.graphics.getWidth()*0.9f,
                Gdx.graphics.getHeight()*0.99f,
                40,
                -40);
        shapeRenderer.end();

        sb.begin();
        menuItemsRects.clear();
        entityManager.render(sb);
        font.setColor(Color.RED);
        font.draw(sb,
                backItem,
                Gdx.graphics.getWidth()*0.9f,
                Gdx.graphics.getHeight()*0.99f);
        menuItemsRects.put(0,
                new Rectangle(Gdx.graphics.getWidth()*0.9f,
                        Gdx.graphics.getHeight()*0.01f,
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
