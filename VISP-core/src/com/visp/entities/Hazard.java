package com.visp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.visp.main.Game;

public class Hazard {
	// FIELDS
		private double x;
		private double y;
		private int r;
		
		private int type;
		private int rank;
		
		private boolean ready;
		private boolean dead;
		
		private boolean linger;
		private long lingerTimer;
		private long lingerDelay = 10000;
		
		// CONDTRUCTOR
		public Hazard(int type, int rank, double x, double y) {
			
			this.type = type;
			this.rank = rank;
			this.x = x;
			this.y = y;
			
			if(type == 1) {
				if(rank == 1) {
					r = 6;
				}
				if(rank == 2) {
					//color1 = new Color(2, 110, 36, 96);
					r = 12;
				}
				if(rank == 3) {
					//color1 = new Color(2, 110, 36, 96);
					r = 24;
				}
				if(rank == 4) {
					//color1 = new Color(2, 110, 36, 96);
					r = 36;
				}
			}
			
			ready = false;
			dead  = false;
			linger = true;
			
			
			lingerTimer =  System.nanoTime();	
		}
		
		// FUNCTIONS
		public double getX() { return x; }
		public double getY() { return y; }
		public int getR() { return r; }
		
		public int getType() { return type; }
		public int getRank() { return rank; }
		
		public boolean isDead() { return dead; }
		
		public void update() {
			
			if(!ready) {
				if(x > r && x < Game.WIDTH - r &&
					y > r && y < Game.HEIGHT - r) {
					ready = true;
				}
			}
			
			// update linger
			if(linger) {
				long elapsed = (System.nanoTime() - lingerTimer) / 1000000;
				if(elapsed > lingerDelay) {
					lingerTimer = System.nanoTime();
					dead = true;
				}
			}
			
		}
		
		public void draw(ShapeRenderer sr) {
			Gdx.gl.glEnable(GL30.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		    sr.begin(ShapeType.Filled);
		    sr.setColor(2/255.0f, 110/255.0f, 36/255.0f, 196/255.0f);
			sr.ellipse((int) (x - r), (int) (y - r), 2 * r, 2 * r);
			sr.end();	
			sr.begin(ShapeType.Line);
			sr.setColor(2/255.0f, 110/255.0f, 36/255.0f, 196/255.0f);
			sr.ellipse((int) (x - r), (int) (y - r), 2 * r, 2 * r);
			sr.end();
			Gdx.gl.glDisable(GL30.GL_BLEND);
		}

}
