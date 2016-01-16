package com.visp.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	
	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg = 
			new LwjglApplicationConfiguration();
		cfg.title = "VISP";
		cfg.width = 400;
		cfg.height = 500;
		cfg.useGL30 = false;
		cfg.resizable = true;
		
		new LwjglApplication(new Game(), cfg);
	}

}
