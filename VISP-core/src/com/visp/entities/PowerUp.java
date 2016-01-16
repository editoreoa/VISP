package com.visp.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.visp.main.Game;

public class PowerUp {
	
	// FIELDS
	private double x;
	private double y;
	private int radius;
	
	private float red, green, blue, alpha;
	
	private int type;
	// 1 -- +1 life
	// 2 -- +1 power
	// 3 -- +2 power
	// 4 -- slow down time

	// CONSTRUCTOR
	public  PowerUp(int type, double x, double y) {	
		this.type = type;
		this.x = x;
		this.y = y;
		
		if(type == 1) {
			red = 1.0f;
			green = 192 / 255.0f;
			blue = 203 / 255.0f;
			alpha = 128 / 255.0f;
			radius = 7;
		}
		if(type == 2) {
			red = 1.0f;
			green = 1.0f;
			blue = 0.0f;
			alpha = 128 / 255.0f;
			radius = 4;
		}
		if(type == 3) {
			red = 1.0f;
			green = 1.0f;
			blue = 0.0f;
			alpha = 128 / 255.0f;
			radius = 6;
		}
		if(type == 4) {
			red = 1.0f;
			green = 1.0f;
			blue = 1.0f;
			alpha = 128 / 255.0f;
			radius = 5;
		}
		
	}
	
	// FUNCTIONS
		public float getX() { return (float) x; }
		public float getY() { return (float) y; }
		public double getRadius() { return radius; }
		
		public int getType() { return type; }
		
		public boolean update() {
			
			y -= 1.5;
			
			if(y > Game.HEIGHT + radius) {
				return true;
			}
			
			return false;
			
		}
		
		public void draw(ShapeRenderer sr) {
			
			sr.setColor(red, green, blue, alpha);
			sr.begin(ShapeType.Filled);
			sr.rect((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
			sr.end();
			sr.setColor(red, green, blue, alpha);
			sr.begin(ShapeType.Line);
			sr.rect((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
			sr.end();
			
		}
	
}
