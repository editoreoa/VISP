package com.visp.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.visp.managers.GameKeys;
import com.visp.managers.GameStateManager;

public class HelpState extends GameState {
	
	// FIELDS
	private BitmapFont font = new BitmapFont(Gdx.files.internal("font/consolas.fnt"), false);
	
	public HelpState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {}
	public void update() {
		handleInput();
	}
	public void draw() {
		
		SpriteBatch sb = new SpriteBatch();
		
		font.getData().setScale(0.6f);
		sb.begin();
		font.draw(sb, "--HELP UNDER CONSTRUCTION--", 30, 50);
		font.draw(sb, "--PRESS SPACE TO EXIT--", 30, 65);
		sb.end();
	}
	
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.SPACE)) { gsm.popState(); }
	}

	@Override
	public void dispose() {
		
	}
}
