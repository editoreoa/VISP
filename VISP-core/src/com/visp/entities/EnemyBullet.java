package com.visp.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.visp.main.Game;

public class EnemyBullet extends GameObject {

	EnemyBullet(float angle, float x, float y, float r, float speed) {
		super(x, y, r, speed);
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.radius = r;
		
		
		radians = (float) Math.toRadians(angle);
		dx = (float) Math.cos(radians) * speed;
		dy = (float) Math.sin(radians) * speed;
	}
	
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
		sr.setColor(1, 0, 0, 1);
		sr.begin(ShapeType.Filled);
		sr.circle((int) (x - radius), (int) (y - radius), radius);
		sr.end();
	}
	
}
