package com.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.invaders.AudioManager;
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

    public Player(){}

    public Player(Vector2 pos, Vector2 direction, EntityManager entityManager, OrthoCamera camera){
        super(TextureManager.PLAYER, pos, direction);
        this.entityManager = entityManager;
        this.camera = camera;
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
