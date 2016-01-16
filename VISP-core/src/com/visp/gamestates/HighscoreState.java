package com.visp.gamestates;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.visp.managers.GameKeys;
import com.visp.managers.GameStateManager;

public class HighscoreState  extends GameState {
	
	private SpriteBatch sb;
	
	private BitmapFont font;
	
	private String currentHigh;
	 
	public FileHandle highscore;
	
	public HighscoreState(GameStateManager gsm) {
		super(gsm);
	}
	
	@Override
	public void init() {
		
		sb = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("font/consolas.fnt"), false);
		
		if(Gdx.files.external("highscores.dat").exists()) {
			highscore = Gdx.files.external("highscores.dat");
			this.currentHigh = getHighScore();
		} else {
			if(Gdx.files.isExternalStorageAvailable()) {
				String extRoot = Gdx.files.getExternalStoragePath();
				List<String> genericBoard = Arrays.asList("50:AAA");
				Path file = Paths.get(extRoot + "/highscores.dat");
				try {
					Files.write(file, genericBoard, Charset.forName("UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			highscore = Gdx.files.external("highscores.dat");
			this.currentHigh = getHighScore();
		}
		
		checkScore();
		
	}
	
	public void checkScore() {
		if(PlayState.player.getScore() > Integer.parseInt(currentHigh.split(":")[0])) {
			String name = JOptionPane.showInputDialog("New Record! What is your name?");
			currentHigh = PlayState.player.getScore() + ":" + name;
			setHighScore(currentHigh);
		}
	}
	
	public String getHighScore() {
		return highscore.readString();
	}
	
	public void setHighScore(String hs) {
		highscore.writeString(hs, false);
	}


	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw() {
		if(highscore.equals("")) {
			currentHigh = this.getHighScore();
		}
		sb.begin();
		
		font.draw(sb, "--PRESS ENTER TO EXIT--", 30, 445);
		font.setColor(1f, (float)215.0/255.0f, 0f, 1f);
		font.draw(sb, "High Score: " + currentHigh, 30, 420);
		font.setColor(1f, 1f, 1f, 1f);
		font.draw(sb, "Your Score: " + PlayState.player.getScore(), 30, 400);
		sb.end();
	}

	@Override
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.ENTER)) { 
			gsm.popState(); 
		}
		
	}

	@Override
	public void dispose() {
		
	}

}
