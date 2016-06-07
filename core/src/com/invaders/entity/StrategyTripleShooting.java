package com.invaders.entity;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by NotePad.by on 06.06.2016.
 */
public class StrategyTripleShooting implements Strategy, java.io.Serializable{
    private Player player;
    private long startTimeBonus, durBonus=5000;
    public StrategyTripleShooting(Player player, long startTimeBonus){
        this.player = player;
        this.startTimeBonus = startTimeBonus;
    }

    public Player getPlayer(){
        return this.player;
    }
    @Override
    public void shoot() {
        if (System.currentTimeMillis() - getPlayer().getLastGunFire() >= 100 &&
                System.currentTimeMillis()-startTimeBonus <= durBonus) {
            getPlayer().getEntityManager().addEntity(
                    new Bullet(
                    new Vector2(getPlayer().pos.x +15, getPlayer().pos.y+30),
                    new Vector2(0, 15)));
            getPlayer().getEntityManager().addEntity(
                    new Bullet(
                    new Vector2(getPlayer().pos.x +15, getPlayer().pos.y+30),
                    new Vector2(1, 15)));
            getPlayer().getEntityManager().addEntity(
                    new Bullet(
                    new Vector2(getPlayer().pos.x +15, getPlayer().pos.y+30),
                    new Vector2(-1, 15)));
            getPlayer().setLastGunFire(System.currentTimeMillis());
        } else if (System.currentTimeMillis()-startTimeBonus > durBonus){
            player.getEntityManager().getContext().setStrategy(new StrategySingleShooting(player));
        }
    }
}
