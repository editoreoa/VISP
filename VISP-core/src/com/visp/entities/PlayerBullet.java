package com.visp.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.visp.main.Game;

public class PlayerBullet extends GameObject {

	private int payload;
	
	//private Color color1;
	
	// Constructor
	public PlayerBullet(double angle, float x, float y, float speed, float radius, int payload) {
		super(x, y, radius, speed);
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.radius = radius;
		this.payload = payload;
		
		radians = (float) Math.toRadians(angle);
		dx = (float) Math.cos(radians) * speed;
		dy = (float) Math.sin(radians) * speed;
		
		//color1 = new Color(214, 255, 28, 128);
		
	}
	
	// FUNCTIONS
	public int getPayload() { return payload; }
	
	public boolean update() {
		x += dx;
		y += dy;
		
		if(x < -radius || x > Game.WIDTH + radius ||
			y < -radius || y > Game.HEIGHT + radius) {
				return true;
			}
		
		return false;
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(1, 1, 1, 1);
		sr.begin(ShapeType.Filled);
		sr.circle((int) (x - radius), (int) (y - radius), radius);
		sr.end();
	}
	
	
}
