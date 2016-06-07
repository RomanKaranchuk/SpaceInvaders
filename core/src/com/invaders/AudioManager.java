package com.invaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by NotePad.by on 02.06.2016.
 */
public class AudioManager {
    private static Music currentMusic;
    private static Sound currentSound;
    private static Sound oldSound;
    private static long id;


    public static long getId(){
        return AudioManager.id;
    }
    public static void setMusic(Music music){
        if (currentMusic != null){
            currentMusic.pause();
        }
        currentMusic = music;
        currentMusic.play();
    }
    public static void setSound(Sound sound, boolean gameOver){
        if (currentSound != null && !gameOver && !currentSound.equals(AudioManager.themeShield)) {
            currentSound.stop();
            oldSound = currentSound;
            oldSound.play();
        } else if (currentSound != null && gameOver){
            currentSound.stop();
        }
        currentSound = sound;
        id = currentSound.play();
    }
    public static Music getCurrentMusic(){
        return currentMusic;
    }
    public static Sound getCurrentSound() {return currentSound;}

    public static Sound turnOffShield = Gdx.audio.newSound(Gdx.files.internal("turn_off_shield.wav"));
    public static Sound themeShield = Gdx.audio.newSound(Gdx.files.internal("theme_shield_cut.wav"));
    public static Music menuTheme = Gdx.audio.newMusic(Gdx.files.internal("menu_theme.mp3"));
    public static Music highscoreTheme = Gdx.audio.newMusic(Gdx.files.internal("highscore_theme.mp3"));
    public static Music mainTheme = Gdx.audio.newMusic(Gdx.files.internal("main_theme_2.mp3"));
    public static Music winTheme = Gdx.audio.newMusic(Gdx.files.internal("win_theme.mp3"));
    public static Music gameOverTheme = Gdx.audio.newMusic(Gdx.files.internal("game_over_theme.wav"));
    public static Sound shotMissile = Gdx.audio.newSound(Gdx.files.internal("fire.wav"));
    public static Sound enemyExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion_cut_half.mp3"));
    public static Sound selectMenu = Gdx.audio.newSound(Gdx.files.internal("select_menu.mp3"));
    public static Sound shotGun = Gdx.audio.newSound(Gdx.files.internal("lazer_gun.wav"));
    public static Sound findBonus = Gdx.audio.newSound(Gdx.files.internal("find_bonus.wav"));
}
