package com.invaders.screen;

/**
 * Created by NotePad.by on 28.05.2016.
 */
public class ScreenManager {

    private static Screen currentScreen;
    private static Screen oldScreen;

    public static void setScreen(Screen screen) {
        if (currentScreen != null && currentScreen != oldScreen) {
            if (!currentScreen.getPaused() && oldScreen == null) {
                currentScreen.dispose();
                currentScreen = screen;
                currentScreen.create();
            } else if (!currentScreen.getPaused() && oldScreen != null) {
                currentScreen.dispose();
                currentScreen = oldScreen;
                oldScreen = null;
                currentScreen.resume();
            } else if (currentScreen.getPaused() && oldScreen == null) {
                currentScreen.pause();
                oldScreen = currentScreen;
                currentScreen = screen;
                currentScreen.create();
            }
        }else{
            currentScreen = screen;
            currentScreen.create();
        }
    }
    public static Screen getCurrentScreen(){
        return currentScreen;
    }
    public static Screen getOldScreen(){
        return oldScreen;
    }
    public static void setOldScreen(Screen oldScreen){
        ScreenManager.oldScreen = oldScreen;
    }
}
