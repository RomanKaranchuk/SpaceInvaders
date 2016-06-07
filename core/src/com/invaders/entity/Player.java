package com.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.invaders.MainGame;
import com.invaders.TextureManager;
import com.invaders.camera.OrthoCamera;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class Player extends  Entity implements java.io.Serializable{
    private EntityManager entityManager;
    private long lastMissileFire;
    private long lastGunFire;
    private OrthoCamera camera;
    private BonusShield bonusShield;
    private boolean hasShield = false;
    private long startShield = 0;

    float stateTime = 0f;
    int countFrames = 0;

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
    public void setStartShield(long startShield){
        this.startShield = startShield;
    }
    public Player(){}

    public Player(Vector2 pos, Vector2 direction, EntityManager entityManager, OrthoCamera camera){
        super(TextureManager.PLAYER, pos, direction);
        this.entityManager = entityManager;
        this.camera = camera;
        this.bonusShield = new BonusShield(getPosition(),new Vector2(0,0));
    }

    public EntityManager getEntityManager(){
        return this.entityManager;
    }
    public long getLastGunFire(){
        return this.lastGunFire;
    }
    public void setLastGunFire(long newLastGunFire){
        this.lastGunFire = newLastGunFire;
    }

    @Override
    public void render(SpriteBatch batch){
        super.render(batch);

        if (this.hasShield) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (countFrames < 8 ||
                    System.currentTimeMillis()-this.startShield>5000) {
                this.bonusShield.setCurrentFrame(this.bonusShield.getAnimation().getKeyFrame(stateTime, true));
            } else if (countFrames >= 8) {
                this.bonusShield.setCurrentFrame(this.bonusShield.getAnimation().getKeyFrame(0.30f, false));
            }
            if (System.currentTimeMillis() -startShield>10000){
                this.hasShield = false;
                this.setStartShield(0);
            } else if (5000<System.currentTimeMillis()-startShield &&
                    System.currentTimeMillis()-startShield<10000){
                this.bonusShield.setCurrentFrame(this.bonusShield.getAnimation().getKeyFrame(stateTime, true));
            }
            countFrames += 1;

            batch.draw(this.bonusShield.getCurrentFrame(),
                    this.getPosition().x - 28,
                    this.getPosition().y - 35);
        }
    }

    @Override
    public void update() {
        pos.add(direction);

        int dir = 0;
        if (Gdx.input.isTouched()){
            Vector2 touch = camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());
            if (touch.x > MainGame.WIDTH/ 2) {
                dir = 2;
            }
            else {
                dir = 1;
            }
        }
        int dx=0, dy=0;
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.A) || dir == 1) &&
                this.getPosition().x-3 >= 0) { //left
            dx = -1;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                Gdx.input.isKeyPressed(Input.Keys.D) || dir == 2) &&
                this.getPosition().x+43 <= MainGame.WIDTH) { //right
            dx = 1;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) &&
                this.getPosition().y+43 <=MainGame.HEIGHT){
            dy = 1;
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.DOWN)|| Gdx.input.isKeyPressed(Input.Keys.S)) &&
                this.getPosition().y-3 >= 0){
            dy = -1;
        }

        setDirection(dx*300,dy*300);
        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) ||
        Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT)){
            if (System.currentTimeMillis() - lastMissileFire >= 300) {
                entityManager.addEntity(new Missile(new Vector2(pos.x +0, pos.y+30),
                                                    new Vector2(0, 15)));
                entityManager.addEntity(new Missile(new Vector2(pos.x +30, pos.y+30),
                                                    new Vector2(0, 15)));
                lastMissileFire = System.currentTimeMillis();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            getEntityManager().getContext().shootStrategy();
        }

    }
}
