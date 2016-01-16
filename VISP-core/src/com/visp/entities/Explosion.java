package com.visp.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Explosion {
	// Fields
		private double x;
		private double y;
		private float r;
		private float maxRadius;
		
		// Constructor
		public Explosion(double x, double y, float r, float max) {
			
			this.x = x;
			this.y = y;
			this.r = r;
			maxRadius = max;
			
		}
		
		public boolean update() {
			r += 5;
			if(r >= maxRadius) {
				return true;
			}
			return false;
		}
		
		public void draw(ShapeRenderer sr) {
			sr.setColor(1, 1, 1, 0.5f);
			sr.begin(ShapeType.Line);
			sr.ellipse((int) (x - r), (int) (y - r), 2 * r, 2 * r);
			sr.end();
		} 
	}

