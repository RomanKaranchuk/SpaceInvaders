package com.invaders.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.invaders.AudioManager;
import com.invaders.MainGame;
import com.invaders.SerializationScore;
import com.invaders.TextureManager;
import com.invaders.camera.OrthoCamera;
import com.invaders.screen.MenuOrExitGameOverScreen;
import com.invaders.screen.ScreenManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NotePad.by on 29.05.2016.
 */
public class EntityManager implements java.io.Serializable{
    private final ArrayList<Entity> entities = new ArrayList<Entity>();
    private final Player player;
    private transient BitmapFont scoreFont;
    private int scoreValue ;
    private ArrayList<Score> scores;
    private Score score;
    private OrthoCamera tempCamera;
    private long startTime, tempStartTime;
    private int amountEnemy;
    private Context context;




    public OrthoCamera getCamera(){
        return tempCamera;
    }

    public void addExtraEnemy(int amount){
        for (int i = 0; i < amount; i++) {
            float x = MathUtils.random(0, Gdx.graphics.getWidth() - TextureManager.ENEMY.getWidth());
            float y = MathUtils.random(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 2);
            float speed = MathUtils.random(1, 1.5f);
            addEntity(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
        }
    }

    public EntityManager(int amount, OrthoCamera camera) {
        startTime = System.currentTimeMillis();
        player = new Player(new Vector2(Gdx.graphics.getWidth()/2-TextureManager.PLAYER.getWidth()/2, 15), new Vector2(0, 0), this, camera);
        addExtraEnemy(amount);
        AudioManager.mainTheme.setLooping(true);
        AudioManager.mainTheme.setVolume(0.1f);
        AudioManager.setMusic(AudioManager.mainTheme);
        scoreFont = new BitmapFont();
        scoreValue = 0;
        scores = new ArrayList<Score>();
        score = new Score();
        tempCamera = camera;
        amountEnemy = amount;
        tempStartTime = startTime;
        context = new Context();
        context.setStrategy(new StrategySingleShooting(player));
    }
    public Context getContext(){
        return context;
    }

    public void update(){
        if ((System.currentTimeMillis() - tempStartTime >= 10000) &&
                (tempStartTime - startTime <= 30000)){
//            addExtraEnemy(amountEnemy);
            tempStartTime = System.currentTimeMillis();
        }
        for (BonusBullets bb : getBonusBullets()) {
            if (System.currentTimeMillis() - bb.getBornTime() > bb.getLifeTime()) {
                entities.remove(bb);
            }
        }

        for (BonusShield bs : getBonusShield()) {

            if (System.currentTimeMillis() - bs.getBornTime() > bs.getLifeTime()) {
                entities.remove(bs);
            }
        }
        for (Entity e : entities)
            e.update();
        for (Missile m : getMissiles())
            if (m.checkEnd())
                entities.remove(m);
        for (Bullet b : getBullets())
            if (b.checkEnd())
                entities.remove(b);
        for (Explosion exp : getExplosion()){
            if (exp.getAnimation().isAnimationFinished(exp.getStateTime()))
                entities.remove(exp);
        }
        player.update();
        checkCollisions();
    }

    public void render(SpriteBatch sb){
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                e.texture = TextureManager.ENEMY;
            } else if (e instanceof Player) {
                e.texture = TextureManager.PLAYER;
            } else if (e instanceof Missile) {
                e.texture = TextureManager.MISSILE;
            } else if (e instanceof BonusBullets) {
                e.texture = TextureManager.BONUS_BULLETS;
            } else if (e instanceof Explosion){
                e.texture = TextureManager.EXPLOSION;
            } else if (e instanceof BonusShield) {
                e.texture = TextureManager.BONUS_SHIELD;
            } else if (e instanceof Bullet){
                e.texture = TextureManager.BULLET;
            }
            e.render(sb);
        }
        player.texture = TextureManager.PLAYER;

        player.setStage();
        player.setBonusShield();

        player.render(sb);
        if (scoreFont == null)
            scoreFont = new BitmapFont();
        scoreFont.draw(sb,"Score: "+scoreValue, 0,Gdx.graphics.getHeight());
    }
    private void checkCollisions(){
        checkCollisionBonusShieldPlayer();
        checkCollisionBonusBulletsPlayer();
        checkCollisionEnemyProjectils();
        if (gameOver()){
            toSerializateScore();
            ScreenManager.setScreen(new MenuOrExitGameOverScreen(true));
            AudioManager.setMusic(AudioManager.winTheme);
        }
        if (!player.getHasShield()){
//            checkCollisionPlayerEnemy();
        }
    }
    private void checkCollisionBonusShieldPlayer(){
        for (BonusShield bs : getBonusShield()) {
            if (bs.getBounds().overlaps(player.getBounds())) {
                player.setHasShield(false);
                player.setStartShield(0);
                player.setHasShield(true);
                if (player.getStartShield() == 0){
                    player.setStartShield(System.currentTimeMillis());
                }
                entities.remove(bs);
            }
        }
    }
    private void checkCollisionBonusBulletsPlayer(){
        for (BonusBullets bb : getBonusBullets()) {
            if (bb.getBounds().overlaps(player.getBounds())){
                AudioManager.setSound(AudioManager.findBonus, false);
                context.setStrategy(new StrategyTripleShooting(player,System.currentTimeMillis()));
                entities.remove(bb);
            }
        }
    }
    private void checkCollisionEnemyProjectils(){
        for (Enemy e : getEnemies()){
            for (Entity proj : getProjectils()){ // getMissiles
                if (e.getBounds().overlaps(proj.getBounds())){
                    if (proj instanceof Missile) {
                        e.setDamage(25);
                    }
                    else if (proj instanceof Bullet) {
                        e.setDamage(18);
                    }
                    if (e.getDamage() <= 0) {
                        AudioManager.setSound(AudioManager.enemyExplosion, false);
                        Explosion curExplosion = new Explosion(e.getPosition(), new Vector2(0,0));
                        entities.add(curExplosion);
                        scoreValue += 10;
                        entities.remove(e);
                        int randInt = MathUtils.random(0,3);//(0,30)
                        if (randInt == 1) {
                            BonusBullets bonusBullets = new BonusBullets(new Vector2(e.pos.x, e.pos.y), new Vector2(0, 0));
                            bonusBullets.setBornTime(System.currentTimeMillis());
                            entities.add(bonusBullets);
                        } else if (randInt == 2){
                            BonusShield bonusShield = new BonusShield(new Vector2(e.pos.x, e.pos.y), new Vector2(0, 0));
                            bonusShield.setBornTime(System.currentTimeMillis());
                            entities.add(bonusShield);
                        }
                    }
                    entities.remove(proj);
                }
            }
        }
    }

    private void checkCollisionPlayerEnemy(){
        for (Enemy e : getEnemies()) {
            if (e.getBounds().overlaps(player.getBounds())) {
                toSerializateScore();
                ScreenManager.setScreen(new MenuOrExitGameOverScreen(false));
                AudioManager.setMusic(AudioManager.gameOverTheme);
            }
        }
    }
    public void toSerializateScore(){
        scores.clear();
        score.setPlayerName("Player");
        score.setScoreValue(scoreValue);
        scores.add(score);
        if (SerializationScore.Deserializate() != null) {
            for (Score s : SerializationScore.Deserializate()) {
                if (scores.size()+1 > 10)
                    break;
                scores.add(s);
            }
        }
        Collections.sort(scores, new Score());
        int i = 1;
        for (Score s : scores){
            s.setId(i++);
        }
        SerializationScore.Serializate(scores);
    }



    public void addEntity(Entity entity){
        entities.add(entity);
    }

    private Array<Enemy> getEnemies(){
        Array<Enemy> ret = new Array<Enemy>();
        for (Entity e : entities)
            if (e instanceof Enemy)
                ret.add((Enemy)e);
        return ret;
    }

    private Array<Bullet> getBullets(){
        Array<Bullet> ret = new Array<Bullet>();
        for (Entity e : entities)
            if (e instanceof Bullet)
                ret.add((Bullet)e);
        return ret;
    }

    private Array<Missile> getMissiles(){
        Array<Missile> ret = new Array<Missile>();
        for (Entity e : entities)
            if (e instanceof Missile)
                ret.add((Missile)e);
        return ret;
    }
    private Array<BonusBullets> getBonusBullets(){
        Array<BonusBullets> ret = new Array<BonusBullets>();
        for (Entity e : entities)
            if (e instanceof BonusBullets)
                ret.add((BonusBullets)e);
        return ret;
    }
    private Array<BonusShield> getBonusShield(){
        Array<BonusShield> ret = new Array<BonusShield>();
        for (Entity e : entities)
            if (e instanceof BonusShield)
                ret.add((BonusShield)e);
        return ret;
    }

    private Array<Entity> getProjectils(){
        Array<Entity> ret = new Array<Entity>();
        for (Entity e : entities) {
            if (e instanceof Bullet) {
                ret.add((Bullet) e);
            } else if (e instanceof Missile){
                ret.add((Missile) e);
            }
        }
        return ret;
    }
    private Array<Explosion> getExplosion(){
        Array<Explosion> ret = new Array<Explosion>();
        for (Entity e : entities) {
            if (e instanceof Explosion) {
                ret.add((Explosion) e);
            }
        }
        return ret;
    }

    public boolean gameOver(){
        return getEnemies().size <= 0;
    }

}
