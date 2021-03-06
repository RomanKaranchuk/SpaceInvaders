package com.invaders.entity;

import com.badlogic.gdx.math.Vector2;
/**
 * Created by NotePad.by on 06.06.2016.
 */
public class StrategySingleShooting implements Strategy ,java.io.Serializable{
    private Player player;
    public StrategySingleShooting(Player player){
        this.player = player;
    }
    public Player getPlayer(){
        return this.player;
    }
    @Override
    public void shoot() {
        if (getPlayer().getEntityManager().getCurTime() - getPlayer().getLastGunFire() >= 0.1f) {
            getPlayer().getEntityManager().addEntity(new Bullet(new Vector2(getPlayer().pos.x +15, getPlayer().pos.y+30),
                    new Vector2(0, 15)));
            getPlayer().setLastGunFire(getPlayer().getEntityManager().getCurTime());
        }
    }
}
