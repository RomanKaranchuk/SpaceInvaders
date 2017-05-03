package com.invaders.entity;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by NotePad.by on 06.06.2016.
 */
public class StrategyTripleShooting implements Strategy, java.io.Serializable{
    private Player player;
    private float startTimeBonus, durBonus=5f;
    public StrategyTripleShooting(Player player, float startTimeBonus){
        this.player = player;
        this.startTimeBonus = startTimeBonus;
    }

    public Player getPlayer(){
        return this.player;
    }
    @Override
    public void shoot() {
        if (getPlayer().getEntityManager().getCurTime() - getPlayer().getLastGunFire() >= 0.1f &&
                getPlayer().getEntityManager().getCurTime()-startTimeBonus <= durBonus) {
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
            getPlayer().setLastGunFire(getPlayer().getEntityManager().getCurTime());
        } else if (getPlayer().getEntityManager().getCurTime()-startTimeBonus > durBonus){
            player.getEntityManager().getContext().setStrategy(new StrategySingleShooting(player));
        }
    }
}
