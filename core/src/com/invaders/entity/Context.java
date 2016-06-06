package com.invaders.entity;

/**
 * Created by NotePad.by on 06.06.2016.
 */
public class Context implements java.io.Serializable{
    private Strategy strategy;
    public Context(){}
    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }
    public void shootStrategy(){
        strategy.shoot();
    }
}
