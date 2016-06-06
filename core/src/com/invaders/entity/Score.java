package com.invaders.entity;

import java.util.Comparator;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class Score implements java.io.Serializable, Comparator<Score>, Comparable<Score>{
    private int id =1;
    private String playerName;
    private int scoreValue;


    public void setId(int id){
        this.id = id;
    }
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    public void setScoreValue(int scoreValue){
        this.scoreValue = scoreValue;
    }

    public int getId(){
        return this.id;
    }
    public String getPlayerName(){
        return this.playerName;
    }

    public int getScoreValue(){
        return scoreValue;
    }

    @Override
    public int compare(Score s1, Score s2) {
        return s2.scoreValue - s1.scoreValue;
    }

    @Override
    public int compareTo(Score s) {
        return (s.playerName).compareTo(this.playerName);
    }
}