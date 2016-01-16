package com.visp.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.visp.managers.GameInputProcessor;
import com.visp.managers.GameKeys;
import com.visp.managers.GameStateManager;

public class Game implements ApplicationListener {
	
	public static int WIDTH;
	public static int HEIGHT;
	public float aspectRatio;
	
	public static OrthographicCamera cam;
	public Viewport viewport;
	
	
	private GameStateManager gsm;

	public void create() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		viewport = new ScreenViewport(cam);
		viewport.apply();
		cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		//cam.update();
		
		Gdx.input.setInputProcessor(
			new GameInputProcessor()
		);
		
		gsm = new GameStateManager();
		
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
		//cam.translate(WIDTH / 2, HEIGHT / 2);
		cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		//cam.update();
	}
	
	public void render() {
		
		// clear screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		
		gsm.update();
		gsm.draw();
		
		GameKeys.update();
	
	}
	
	public void pause() {}
	public void resume() {}
	public void dispose() {}
	
}
