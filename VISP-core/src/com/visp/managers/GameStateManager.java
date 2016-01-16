package com.visp.managers;

import java.util.ArrayList;
import java.util.Stack;

import com.visp.gamestates.GameOverState;
import com.visp.gamestates.GameState;
import com.visp.gamestates.HelpState;
import com.visp.gamestates.HighscoreState;
import com.visp.gamestates.MenuState;
import com.visp.gamestates.PauseState;
import com.visp.gamestates.PlayState;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private Stack<GameState> currentStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	public static final int HELPSTATE = 3;
	public static final int PAUSESTATE = 4;
	public static final int HIGHSCORESTATE = 5;
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		currentStates = new Stack<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new PlayState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new HelpState(this));
		gameStates.add(new PauseState(this));
		gameStates.add(new HighscoreState(this));
		
		currentStates.push(gameStates.get(currentState));
		currentStates.peek().init();
		
	}
	
	public void pushState(int state) {
		currentState = state;
		currentStates.push(gameStates.get(currentState));
		currentStates.peek().init();
	}
	
	public void popState() {
		currentStates.pop();
	}
	
	public void update() {
		currentStates.peek().update();
	}
	
	public void draw() {
		currentStates.peek().draw();
	}
	
	public void handleInput() {
		
	}

}














