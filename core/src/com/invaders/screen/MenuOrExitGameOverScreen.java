package com.invaders.screen;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.TextureManager;
import com.invaders.camera.OrthoCamera;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class MenuOrExitGameOverScreen extends Screen{

    private OrthoCamera camera;
    private Texture texture;
    private BitmapFont questFont,font;
    private String[] selectItems;
    private int currentItem = 0;
    private ShapeRenderer shapeRenderer;
    private Map<Integer, Rectangle> menuItemsRects;

    public MenuOrExitGameOverScreen(Object won){
        if (won == null)
            texture = TextureManager.EMPTY;
        else if (won instanceof Boolean) {
            boolean boolWon = (Boolean)won;
            if (boolWon)
                texture = TextureManager.GAME_WON;
            else
                texture = TextureManager.GAME_OVER;
        }
    }

    @Override
    public void create() {
        camera = new OrthoCamera();
        questFont = new BitmapFont();
        font = new BitmapFont();
        selectItems = new String[] {"OK","NO"};
        AudioManager.setMusic(AudioManager.menuTheme);
        shapeRenderer = new ShapeRenderer();
        menuItemsRects = new TreeMap<Integer, Rectangle>();
    }

    @Override
    public void update() {
        camera.update();
        if (!AudioManager.getCurrentMusic().isPlaying()) {
            AudioManager.setMusic(AudioManager.menuTheme);
        }
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
        if (currentItem == 0) {
            AudioManager.getCurrentMusic().stop();
            ScreenManager.setScreen(new MenuScreen());
        } else if (currentItem == 1){

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
        for (int i = 0 ; i< selectItems.length; i++) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(80 / 255.0f, 80 / 255.0f, 50 / 255.0f, 1);
            shapeRenderer.rect(Gdx.graphics.getWidth()/2 + 70*i,
                    Gdx.graphics.getHeight()*0.7f,
                    40,
                    -40);
            shapeRenderer.end();
        }
        sb.begin();
        sb.draw(texture,
                Gdx.graphics.getWidth() / 2 - texture.getWidth() / 2 ,
                Gdx.graphics.getHeight() / 2 - texture.getHeight()/2);
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
            menuItemsRects.put(i,
                    new Rectangle(Gdx.graphics.getWidth()/2 + 70*i,
                            Gdx.graphics.getHeight() - Gdx.graphics.getHeight()*0.7f,
                            40,
                            40));
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
