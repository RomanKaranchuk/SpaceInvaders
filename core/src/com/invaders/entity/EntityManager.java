package com.invaders.entity;

import com.badlogic.gdx.Audio;
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

import java.math.BigInteger;
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
    private Bonus bonus;




    public OrthoCamera getCamera(){
        return tempCamera;
    }

    public void addExtraEnemy(int amount){
        for (int i = 0; i < amount; i++) {
            float x = MathUtils.random(0, MainGame.WIDTH - TextureManager.ENEMY.getWidth());
            float y = MathUtils.random(MainGame.HEIGHT, MainGame.HEIGHT * 4);
            float speed = MathUtils.random(5, 8);
            addEntity(new Enemy(new Vector2(x, y), new Vector2(0, -speed)));
        }
    }

    public EntityManager(int amount, OrthoCamera camera) {
        startTime = System.currentTimeMillis();
        player = new Player(new Vector2(200, 15), new Vector2(0, 0), this, camera);
        addExtraEnemy(amount);
        AudioManager.mainTheme.setLooping(true);
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
            addExtraEnemy(amountEnemy);
            tempStartTime = System.currentTimeMillis();
        }

        for (Entity e : entities)
            e.update();
        for (Missile m : getMissiles())
            if (m.checkEnd())
                entities.remove(m);
        for (Bullet b : getBullets())
            if (b.checkEnd())
                entities.remove(b);
        player.update();
        checkCollisions();
    }

    public void render(SpriteBatch sb){
        for (Entity e : entities) {
            if (e instanceof Enemy){
                e.texture = TextureManager.ENEMY;
            } else if (e instanceof Player){
                e.texture = TextureManager.PLAYER;
            } else if (e instanceof Missile){
                e.texture = TextureManager.MISSILE;
            } else if (e instanceof  Bonus){
                e.texture = TextureManager.BONUS;
            }
            e.render(sb);
        }
        player.texture = TextureManager.PLAYER;
        player.render(sb);
        if (scoreFont == null)
            scoreFont = new BitmapFont();
        scoreFont.draw(sb,"Score: "+scoreValue, 0,MainGame.HEIGHT);
    }
    private void checkCollisions(){
        for (Enemy e : getEnemies()){
            for (Entity proj : getProjectils()){ // getMissiles
                if (e.getBounds().overlaps(proj.getBounds())){
                    if (proj instanceof Missile) {
                        e.setDamage(2);
                    }
                    else if (proj instanceof Bullet) {
                        e.setDamage(1);
                    }
                    if (e.getDamage() <= 0) {
                        AudioManager.setSound(AudioManager.enemyExplosion, false);
                        scoreValue += 10;
                        entities.remove(e);
                        if (MathUtils.random(0,15) == 1) {
                            bonus = new Bonus(new Vector2(e.pos.x, e.pos.y), new Vector2(0, 0));
                            entities.add(bonus);
                        }
                    }
                    entities.remove(proj);
                    if (gameOver()){
                        toSerializateScore();
                        ScreenManager.setScreen(new MenuOrExitGameOverScreen(true));
                        AudioManager.setMusic(AudioManager.winTheme);
                    }
                }
            }
            if (e.getBounds().overlaps(player.getBounds())) {
                toSerializateScore();
                ScreenManager.setScreen(new MenuOrExitGameOverScreen(false));
                AudioManager.setMusic(AudioManager.gameOverTheme);
            }
        }
        for (Bonus b : getBonus()){
            if (b.getBounds().overlaps(player.getBounds())){
                AudioManager.setSound(AudioManager.findBonus, false);
                context.setStrategy(new StrategyTripleShooting(player,System.currentTimeMillis()));
                entities.remove(b);
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
    private Array<Bonus> getBonus(){
        Array<Bonus> ret = new Array<Bonus>();
        for (Entity e : entities)
            if (e instanceof Bonus)
                ret.add((Bonus)e);
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

    public boolean gameOver(){
        return getEnemies().size <= 0;
    }

}
