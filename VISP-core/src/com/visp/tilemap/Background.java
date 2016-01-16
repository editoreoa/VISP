package com.visp.tilemap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.visp.main.Game;

public class Background {

	private Texture texture;
	
	private SpriteBatch sb;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(String s, double ms) {
		
		try {
			texture = new Texture(Gdx.files.internal("bgMenu.png"));
			sb = new SpriteBatch();
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % Game.WIDTH;
		this.y = (y * moveScale) % Game.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(ShapeRenderer sr) {
		
		/*sb.begin();
		((Batch) texture).draw(texture, (int)x, (int)y);
		sb.end();
		if(x < 0) {
			sb.begin();
			((Batch) texture).draw(texture, (int)x + Game.WIDTH, (int)y);
			sb.end();
		}
		if(x > 0) {
			sb.begin();
			((Batch) texture).draw(texture, (int)x - Game.WIDTH, (int)y);
			sb.end();
		}*/
	}

}
