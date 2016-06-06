package com.invaders;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.invaders.screen.MenuScreen;
import com.invaders.screen.ScreenManager;

public class MainGame implements ApplicationListener {
	public static int WIDTH = 480, HEIGHT = 700;
	private SpriteBatch batch;
	ParticleEffect pe;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ScreenManager.setScreen(new MenuScreen());

		pe = new ParticleEffect();
		pe.load(Gdx.files.internal("particleStars.party"),Gdx.files.internal(""));
		pe.getEmitters().first().setPosition(MainGame.WIDTH, 0);
		pe.start();
	}

	@Override
	public void dispose(){
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().dispose();
		batch.dispose();


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().update();

		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().render(batch);

		pe.update(Gdx.graphics.getDeltaTime());
		batch.begin();
		pe.draw(batch);
		batch.end();
		if (pe.isComplete())
			pe.reset();



	}

	@Override
	public void resize(int width, int height){
		if (ScreenManager.getCurrentScreen() != null){
			ScreenManager.getCurrentScreen().resize(width,height);
		}
	}

	@Override
	public void pause(){
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().pause();
	}

	@Override
	public void resume(){
		if (ScreenManager.getCurrentScreen() != null)
			ScreenManager.getCurrentScreen().resume();
	}
}
