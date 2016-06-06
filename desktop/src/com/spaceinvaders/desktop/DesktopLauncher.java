package com.spaceinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.invaders.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame(), cfg);
		cfg.title = "SpaceInvaders";
		cfg.useGL30 = true;
		cfg.width = MainGame.WIDTH;
		cfg.height = MainGame.HEIGHT;
	}
}
