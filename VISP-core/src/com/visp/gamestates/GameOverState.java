package com.visp.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.visp.main.Game;
import com.visp.managers.GameKeys;
import com.visp.managers.GameStateManager;

public class GameOverState extends GameState {

	private int currentChoice = 0;
	private String[] options = {
		"Restart",
		"Help",
		"Quit"
	};
	
	private BitmapFont font = new BitmapFont(Gdx.files.internal("font/consolas.fnt"), false);

	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.pushState(GameStateManager.PLAYSTATE);
		}
		if(currentChoice == 1) {
			gsm.pushState(GameStateManager.HELPSTATE);
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void update() {
		//bg.update(); 
		handleInput();
	}
	
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.ENTER)) { select(); }
		if(GameKeys.isPressed(GameKeys.DOWN)) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
		if(GameKeys.isPressed(GameKeys.UP)) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
	}
	
	public void draw() {

		SpriteBatch sb = new SpriteBatch();
		
/*		
		
		
		s = "Final Score:  " + PlayState.player.getScore();
		*/
		
		sb.begin();
		font.draw(sb, "G A M E  O V E R", Game.WIDTH / 2 - 115, Game.HEIGHT / 2 + 100);
		font.draw(sb, "Final Score: " + PlayState.player.getScore(), Game.WIDTH / 2 - 115, Game.HEIGHT / 2 + 70);
		sb.end();
		
		// draw menu options
		for(int i = 0; i < options.length; i ++) {
			font.getData().setScale(1f);
			sb.begin();
			if(i == currentChoice) {
				font.setColor(1, 1, 1, 1);
			}
			else {
				font.setColor(1, 0, 0, 0.5f);
			}
			font.draw(sb, options[i],  157, 280 - i * 25);
			sb.end();
		}
		
	}
	
	public void dispose() {
		
		
	}
	
}
