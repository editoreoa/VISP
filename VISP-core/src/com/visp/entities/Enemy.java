package com.visp.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.visp.gamestates.PlayState;
import com.visp.main.Game;

public class Enemy implements Poolable {
	
	private float x, y, speed;
	private int radius;
	private double rad;
	
	private float dx, dy;
	
	private int health, type, rank;
	
	private boolean ready, dead, hit, slow;

	private long hitTimer;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	
	private float red, green, blue, alpha;
	
	// Constructor
	public Enemy(int type, int rank) {
		this.type = type;
		this.rank = rank;
	}
	
	public void init(int type, int rank) {
		this.type = type;
		this.rank = rank;
		
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay = 1000;
			
		switch(type) {
			
		// default enemy
		case 1: 
			//color1 purple
			red = 128 / 255.0f;
			green = 58 / 255.0f;
			blue = 237 / 255.0f;
			alpha = 128 / 255.0f;
			speed = 1.1f;
			
			if(rank == 1) {
				radius = 5;
				health = 1;
			}
			if(rank == 2) {
				radius = 10;
				health = 2;
			}
			if(rank == 3) {
				radius = 20;
				health = 3;
			}
			if(rank == 4) {
				radius = 30;
				health = 4;
			}
			break;
		
		// stronger, faster default
		case 2:
			//color1 = Color.RED;
			red = 173 / 255.0f;
			green = 113 / 255.0f;
			blue = 38 / 255.0f;
			alpha = 128 / 255.0f;
			speed = 1.9f;
			if(rank == 1) {
				radius = 5;
				health = 2;
			}
			if(rank == 2) {
				radius = 10;
				health = 3;
			}
			if(rank == 3) {
				radius = 20;
				health = 4;
			}
			if(rank == 4) {
				radius = 30;
				health = 5;
			}
			break;
		
		// slow, tough, leaves behind hazard
		case 3:
			//color1 = Color.GREEN;
			red = 17 / 255.0f;
			green = 237 / 255.0f;
			blue = 65 / 255.0f;
			alpha = 128 / 255.0f;
			speed = 0.9f;
			if(rank == 1) {
				radius = 6;
				health = 3;
			}
			if(rank == 2) {
				radius = 12;
				health = 4;
			}
			if(rank == 3) {
				radius = 24;
				health = 5;
			}
			if(rank == 4) {
				radius = 36;
				health = 6;
			}
			break;
		case 4:
			radius = 56;
			health = 250;
			if(rank == 8){
				red = 255 / 255.0f;
				green = 230 / 255.0f;
				blue = 8 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 0.4f;
			}
			if(rank == 7){
				red = 240 / 255.0f;
				green = 217 / 255.0f;
				blue = 6 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 0.8f;
			}
			if(rank == 6){
				red = 207 / 255.0f;
				green = 186 / 255.0f;
				blue = 3 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 1.2f;
			}
			if(rank == 5){
				red = 175 / 255.0f;
				green = 158 / 255.0f;
				blue = 3 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 1.6f;
			}
			if(rank == 4){
				red = 144 / 255.0f;
				green = 130 / 255.0f;
				blue = 0 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 2f;
			}
			if(rank == 3){
				red = 111 / 255.0f;
				green = 100 / 255.0f;
				blue = 0 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 2.4f;
			}
			if(rank == 2){
				red = 80 / 255.0f;
				green = 72 / 255.0f;
				blue = 3 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 2.8f;
			}
			if(rank == 1){
				red = 48 / 255.0f;
				green = 44 / 255.0f;
				blue = 2 / 255.0f;
				alpha = 196 / 255.0f;
				speed = 3.2f;
			}
			break;
		case 5:
			red = 204 / 255.0f;
			green = 19 / 255.0f;
			blue = 61 / 255.0f;
			alpha = 128 / 255.0f;
			speed = 2.5f;
			radius = 7;
			health = 40;
		default:
			break;
		}
		
		x = (float) Math.random() * Game.WIDTH / 2 + Game.WIDTH / 4;
		y = Game.HEIGHT + radius;
		
		double angle = Math.random() * 140 + 20;
		rad =  Math.toRadians(angle);
		
		dx = (float) Math.cos(rad) * speed;
		dy = (float) -Math.sin(rad) * speed;
		
		ready = false;
		dead = false;
		
		hit = false;
		hitTimer = 0;
		
	}
	
	// Functions
	public float getX() { return x; }
	public float getY() { return y; }
	public float getRadius() { return radius; }
	public float getSpeed() { return speed; }
	public void setSpeed(float s) { speed = s; }
	
	public int getType() { return type; }
	public void setType(int t) { type = t; }
	public int getRank() { return rank; }
	public void setRank(int r) { rank = r; }
	
	public void setSlow(boolean b) { slow = b; }
	
	public boolean isDead() { return dead; }
	
	public void hit(int i) {
		health -= i;
		if(health <= 0) {
			dead = true;
		}
		hit = true;
		hitTimer = System.nanoTime();
	}
	
	public void explode() {
		
		if(rank > 1) {
			
			int amount = 0;
			if(type == 1) {
				amount = 3;
			}
			if(type == 2) {
				amount = 3;
			}
			if(type == 3) {
				amount = 3;
			}
			
			for(int i = 0; i < amount; i++) {
				
				Enemy e = PlayState.enemyPool.obtain();
				e.init(getType(), getRank() - 1);
				e.setSlow(slow);
				e.x = this.x;
				e.y = this.y;
				if(!ready) {
					double angle = 0;
					angle = Math.random() * 360;
					e.rad = (float) Math.toRadians(angle);
					
					e.dx = (float) Math.cos(rad) * speed;
					e.dy = (float) Math.sin(rad) * speed;
				}
				PlayState.enemies.add(e);
			}
			if(type == 4) {
				Enemy e = PlayState.enemyPool.obtain();
				e.init(getType(), getRank() - 1);
				e.setSlow(slow);
				e.x = this.x;
				e.y = this.y;
				if(!ready) {
					float angle = 0;
					Random r = new Random();
					angle = r.nextInt(359);
					e.rad = (float) Math.toRadians(angle);
					
					e.dx = (float) Math.cos(rad) * speed;
					e.dy = (float) Math.sin(rad) * speed;
				}
				
				for(int i = 0; i < 9 - getRank(); i++) {
					Enemy e2 = PlayState.enemyPool.obtain();
					Enemy e3 = PlayState.enemyPool.obtain();
					e2.init(3, 3);
					e2.setSlow(slow);
					e2.x = this.x;
					e2.y = this.y;
					e3.init(5, 1);
					e3.setSlow(slow);
					e3.x = this.x;
					e3.y = this.y;
					if(!ready) {
						double angle = 0;
						angle = Math.random() * 360;
						e2.rad = (float) Math.toRadians(angle);
						
						e2.dx = (float) Math.cos(rad) * speed;
						e2.dy = (float) Math.sin(rad) * speed;
						
						e3.rad = (float) Math.toRadians(angle);
						
						e3.dx = (float) Math.cos(rad) * speed;
						e3.dy = (float) Math.sin(rad) * speed;
					}
					PlayState.enemies.add(e2);
					PlayState.enemies.add(e3);
				}
				PlayState.enemies.add(e);
			}
		}
	}
	
	public void update() {
		
		if(slow) {
			x += dx * 0.3;
			y += dy * 0.3;
		} else {
			x += dx;
			y += dy;
		}
		
		if(!ready) {
			if(x > radius && x < Game.WIDTH - radius &&
				y > radius && y < Game.HEIGHT - radius) {
				ready = true;
			}
		}
		
		if(hit) {
			long elapsed = (System.nanoTime() - hitTimer) / 1000000;
			if(elapsed > 60) {
				hit = false;
				hitTimer = 0;
			}
		}
		
		if(type == 5) {
			if(x < radius && dx < 0) dx = -dx;
			if(y < radius && dy < 0) dy = -dy;
			if(x > Game.WIDTH - radius && dx > 0) dx = -dx;
			if(y > Game.HEIGHT / 2 - radius && dy > 0 && (x < radius || x > Game.WIDTH - radius) || y > Game.HEIGHT - radius) { 
				dx = 0;
				dy = 0;	
				firing = true;
			}
			
			if(firing) {
				long elapsed = (System.nanoTime() - firingTimer) / 1000000;
				if(elapsed > firingDelay){
					firingTimer = System.nanoTime();
					int angle = 45;
					for(int i = 0; i < 8; i++) {
						PlayState.eBullets.add(new EnemyBullet(angle * i, x, y, 3, 3));
				
					}
				}
			}
			
			return;
		}
		
		if(x < radius && dx < 0) dx = -dx;
		if(y < radius && dy < 0) dy = -dy;
		if(x > Game.WIDTH - radius && dx > 0) dx = -dx;
		if(y > Game.HEIGHT - radius && dy > 0) dy = -dy;
		
		if(type == 4) {
			BossMusic.setBossState(getRank());
		}
	}
	
	public void draw(ShapeRenderer sr) {
		Gdx.gl.glEnable(GL30.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
	    if(hit){
	    	sr.setColor(1 - red, 1 - green, 1 - blue, alpha);	
	    } else {
	    	sr.setColor(red, green, blue, alpha);    	
	    }
		sr.begin(ShapeType.Filled);
		sr.ellipse((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
		sr.end();
		
		sr.begin(ShapeType.Line);
		sr.ellipse((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
		sr.end();
		Gdx.gl.glDisable(GL30.GL_BLEND);
	}

	public void reset() {
		this.x = 0;
		this.y = 0;
		this.radius = 0;
		this.speed = 0;
		this.dx = 0;
		this.dy = 0;
		this.rad = 0;
		this.health = 0;
		this.type = 0;
		this.rank = 0;
		this.hitTimer = 0;
		this.red = 0;
		this.green = 0;
		this.blue = 0;
		this.alpha = 0;
		
		this.hit = false;
		this.ready = false;
		this.dead = false;
		this.slow = false;
	}

}









