package com.spaceinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.invaders.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = MainGame.WIDTH;
		cfg.height = MainGame.HEIGHT;
		cfg.title = "SpaceInvaders";
		cfg.useGL30 = true;
		new LwjglApplication(new MainGame(), cfg);
	}
}
