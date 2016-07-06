package com.invaders;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.invaders.camera.OrthoCamera;
import com.invaders.screen.MenuScreen;
import com.invaders.screen.ScreenManager;

public class MainGame implements ApplicationListener {
	public static int WIDTH = 480, HEIGHT = 800;
	private SpriteBatch batch;
	ParticleEffect pe;
//	private static final int        FRAME_COLS = 5;         // #1
//	private static final int        FRAME_ROWS = 5;         // #2
//
//	Animation animation;          // #3
//	Texture sheet;              // #4
//	TextureRegion[]                 frames;             // #5
//	TextureRegion currentFrame;           // #7
//
//	float stateTime;
//	int countFrames = 0;




	@Override
	public void create () {
		batch = new SpriteBatch();


		ScreenManager.setScreen(new MenuScreen());

		pe = new ParticleEffect();
		pe.load(Gdx.files.internal("particleStars.party"),Gdx.files.internal(""));
		pe.getEmitters().first().setPosition(MainGame.WIDTH, 0);
		pe.start();


//		sheet = new Texture(Gdx.files.internal("sprite-explosion.png")); // #9
//		TextureRegion[][] tmp = TextureRegion.split(sheet,
//				sheet.getWidth()/FRAME_COLS,
//				sheet.getHeight()/FRAME_ROWS);              // #10
//		frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
//		int index = 0;
//		for (int i = 0; i < FRAME_ROWS; i++) {
//			for (int j = 0; j < FRAME_COLS; j++) {
//				frames[index++] = tmp[i][j];
//			}
//		}
//		animation = new Animation(1f/30f, frames);      // #11
//		stateTime = 0f;



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

		if (ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().update();
		}

		if (ScreenManager.getCurrentScreen() != null) {
			ScreenManager.getCurrentScreen().render(batch);
		}

//		pe.update(Gdx.graphics.getDeltaTime());

//		stateTime += Gdx.graphics.getDeltaTime();           // #15

//		if (countFrames < 8) {
//			currentFrame = animation.getKeyFrame(stateTime, true);  // #16
//		} else {
//			currentFrame = animation.getKeyFrame(0.30f, false);  // #16
//		}
//		countFrames+=1;


		batch.begin();
//		pe.draw(batch);


//		batch.draw(currentFrame, 0, 0);             // #17
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
