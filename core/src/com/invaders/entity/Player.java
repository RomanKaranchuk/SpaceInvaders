package com.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import com.invaders.MainGame;
import com.invaders.TextureManager;
import com.invaders.camera.OrthoCamera;
import com.invaders.camera.VirtualViewport;

import java.io.Console;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class Player extends  Entity implements java.io.Serializable{
    private EntityManager entityManager;
    private float lastMissileFire;
    private float lastGunFire;
    private OrthoCamera camera;
    private BonusShield bonusShield;
    private boolean hasShield = false;
    private float startShield = 0f;

    float stateTime = 0f;
    int countFrames = 0;

//    private OrthographicCamera orthCamera;
    private transient Stage stage;
    private transient Touchpad touchpad;
    private transient Touchpad tpFire;
    private transient Touchpad.TouchpadStyle touchpadStyle;
    private transient Touchpad.TouchpadStyle tpFireStyle;
    private transient Skin touchpadSkin;
    private transient Drawable touchBackground;
    private transient Drawable touchKnob;

//    private Texture blockTexture;
//    private Sprite blockSprite;
//    private float blockspeed;
//    private SpriteBatch batch;

    public void setBonusShield(){
        this.bonusShield = new BonusShield(getPosition(),new Vector2(0,0));
    }
    public void setTouchpadSkinAndBackground(){
        if (this.touchpadSkin == null){
            touchpadSkin = new Skin();
            touchpadSkin.add("touchBackground", new Texture("dpad.png"));
            touchpadSkin.add("touchKnob", new Texture("joystick.png"));
        }
        if (this.touchBackground == null)
            touchBackground = touchpadSkin.getDrawable("touchBackground");
        if (this.touchKnob == null)
            touchKnob = touchpadSkin.getDrawable("touchKnob");
    }
    public void setStage(){
        if (this.stage == null) {
            this.setTouchpadSkinAndBackground();
            this.setTpFire();
            this.setTouchpad();
            stage = new Stage(new ScreenViewport(camera));
            stage.addActor(touchpad);
            stage.addActor(tpFire);
            Gdx.input.setInputProcessor(stage);
        }
    }
    public void setTouchpad(){

        if (this.touchpadStyle == null) {
            this.touchpadStyle = new Touchpad.TouchpadStyle();
            touchpadStyle.background = touchBackground;
            touchpadStyle.knob = touchKnob;
        }
        if (this.touchpad == null){
            touchpad = new Touchpad(10, touchpadStyle);
            touchpad.setBounds(15, 15, 100, 100);
        }
    }
    public void setTpFire(){
        if (this.tpFireStyle == null) {
            this.tpFireStyle = new Touchpad.TouchpadStyle();
            tpFireStyle.background = touchBackground;
        }
        if (this.tpFire == null){
            tpFire = new Touchpad(10, tpFireStyle);
            tpFire.setBounds(Gdx.graphics.getWidth()-115, 15, 100, 100);
        }
    }


    public void setHasShield(boolean hasShield){
        this.hasShield = hasShield;
        stateTime = 0f;
        countFrames = 0;
    }
    public boolean getHasShield(){
        return this.hasShield;
    }
    public float getStartShield(){
        return this.startShield;
    }
    public void setStartShield(float startShield){
        this.startShield = startShield;
    }
    public Player(){}

    public Player(Vector2 pos, Vector2 direction, EntityManager entityManager, OrthoCamera camera){
        super(TextureManager.PLAYER, pos, direction);
        this.entityManager = entityManager;
        this.camera = camera;
        this.bonusShield = new BonusShield(getPosition(),new Vector2(0,0));

//        orthCamera = new OrthographicCamera();
//        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
//        orthCamera.setToOrtho(false, 10f * aspectRatio, 10f);


        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("dpad.png"));
        touchpadSkin.add("touchKnob", new Texture("joystick.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        tpFireStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        tpFireStyle.background = touchBackground;
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(10, touchpadStyle);
        tpFire = new Touchpad(10, tpFireStyle);
        tpFire.setBounds(Gdx.graphics.getWidth()-130, 30, 100, 100);
        touchpad.setBounds(30, 30, 100, 100);

        stage = new Stage(new ScreenViewport(camera));
        stage.addActor(touchpad);
        stage.addActor(tpFire);
        Gdx.input.setInputProcessor(stage);

//        blockTexture = new Texture(Gdx.files.internal("block.png"));
//        blockSprite = new Sprite(blockTexture);
//        blockSprite.setPosition(Gdx.graphics.getWidth()/2 - blockSprite.getWidth()/2,
//                Gdx.graphics.getHeight()/2 - blockSprite.getHeight()/2);
//        blockspeed = 5;


    }

    public EntityManager getEntityManager(){
        return this.entityManager;
    }
    public float getLastGunFire(){
        return this.lastGunFire;
    }
    public void setLastGunFire(float newLastGunFire){
        this.lastGunFire = newLastGunFire;
    }

    @Override
    public void render(SpriteBatch sb){


//        sb.setProjectionMatrix(stage.getCamera().combined);
//        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX() * blockspeed);
//        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY() * blockspeed);
//        blockSprite.draw(batch);
//        sb.draw(texture,pos.x,pos.y);
        tpFire.setBounds(Gdx.graphics.getWidth()-130, 30, 100, 100);


        sb.draw(texture,pos.x,pos.y);
        if (this.hasShield) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (countFrames < 8 ||
                    this.getEntityManager().getCurTime()-this.startShield>5000) {
                this.bonusShield.setCurrentFrame(this.bonusShield.getAnimation().getKeyFrame(stateTime, true));
            } else if (countFrames >= 8) {
                this.bonusShield.setCurrentFrame(this.bonusShield.getAnimation().getKeyFrame(0.30f, false));
            }
            if (this.getEntityManager().getCurTime() -startShield>10f){
                this.hasShield = false;
                this.setStartShield(0);
            } else if (5f<this.getEntityManager().getCurTime()-startShield &&
                    this.getEntityManager().getCurTime()-startShield<10f){
                this.bonusShield.setCurrentFrame(this.bonusShield.getAnimation().getKeyFrame(stateTime, true));
            }
            countFrames += 1;

            sb.draw(this.bonusShield.getCurrentFrame(),
                    this.getPosition().x - 28,
                    this.getPosition().y - 35);
        }

    }

    @Override
    public void update() {
        stage.getViewport().update(Gdx.graphics.getWidth(),
                    Gdx.graphics.getHeight(), true);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        pos.add(direction);
        int dir = 0;
//        if (Gdx.input.isTouched()){
//            Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
//            if (touch.x > MainGame.WIDTH/ 2) {
//                dir = 2;
//            }
//            else {
//                dir = 1;
//            }
//        }
        float dx=0, dy=0;

        if (this.pos.x+TextureManager.PLAYER.getWidth() > Gdx.graphics.getWidth()){
            pos.x = Gdx.graphics.getWidth()-TextureManager.PLAYER.getWidth();
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.A) || dir == 1) &&
                this.getPosition().x-3 >= 0) { //left
            dx = -1;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                Gdx.input.isKeyPressed(Input.Keys.D) || dir == 2) &&
                this.getPosition().x+43 <= Gdx.graphics.getWidth()) { //right
            dx = 1;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) &&
                this.getPosition().y+43 <=Gdx.graphics.getHeight()){
            dy = 1;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.DOWN)|| Gdx.input.isKeyPressed(Input.Keys.S)) &&
                this.getPosition().y-3 >= 0){
            dy = -1;
        }



        if (touchpad.getKnobPercentX() != 0) {
            if (this.getPosition().x > 0 &&
                    this.getPosition().x + TextureManager.PLAYER.getWidth() < Gdx.graphics.getWidth()) {
                dx = touchpad.getKnobPercentX();
            } else if (this.getPosition().x < 0 && touchpad.getKnobPercentX() > 0) {
                dx = touchpad.getKnobPercentX();
            } else if (this.getPosition().x + TextureManager.PLAYER.getWidth() >= Gdx.graphics.getWidth() &&
                    touchpad.getKnobPercentX() < 0)
                dx = touchpad.getKnobPercentX();
        }

        if (touchpad.getKnobPercentY() != 0) {
            if (this.getPosition().y > 0 &&
                    this.getPosition().y + TextureManager.PLAYER.getWidth() < Gdx.graphics.getHeight()) {
                dy = touchpad.getKnobPercentY();
            } else if (this.getPosition().y < 0 && touchpad.getKnobPercentY() > 0) {
                dy = touchpad.getKnobPercentY();
            } else if (this.getPosition().y + TextureManager.PLAYER.getWidth() >= Gdx.graphics.getHeight() &&
                    touchpad.getKnobPercentY() < 0)
                dy = touchpad.getKnobPercentY();
        }

//        System.out.println(this.getPosition().x + " " + this.getPosition().y);
//        System.out.println(touchpad.getKnobPercentX() + " " + touchpad.getKnobPercentY());
        setDirection(dx*350,dy*350);

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) ||
        Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT) ||
                tpFire.isTouched()){
            if (this.getEntityManager().getCurTime() - lastMissileFire >= 0.3f) {
                entityManager.addEntity(new Missile(new Vector2(pos.x +0, pos.y+30),
                                                    new Vector2(0, 15)));
                entityManager.addEntity(new Missile(new Vector2(pos.x +30, pos.y+30),
                                                    new Vector2(0, 15)));
                lastMissileFire = this.getEntityManager().getCurTime();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            getEntityManager().getContext().shootStrategy();
        }

    }
}
