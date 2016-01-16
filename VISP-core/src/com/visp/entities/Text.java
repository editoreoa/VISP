package com.visp.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Text {
	
	// FIELDS
	private double x;
	private double y;
	private long time;
	private String s;
	private int offset;
	
	//private SpriteBatch sb = new SpriteBatch();
	private BitmapFont font = new BitmapFont(Gdx.files.internal("font/consolas.fnt"), false);
	
	//private float red, green, blue, alpha;
	
	private long start;
	
	// CONSTRUCTOR
	public Text(double x, double y, long time, String s, int offset) {
		this.x = x;
		this.y = y;
		this.time = time;
		this.s = s;
		this.offset = offset;
		start = System.nanoTime();
	}
	
	public boolean update() {
		long elapsed = (System.nanoTime() - start) / 1000000;
		if(elapsed > time) {
			return true;
		}
		return false;
	}
	
	public void draw(ShapeRenderer sr, SpriteBatch sb) {
		long elapsed = (System.nanoTime() - start) / 1000000;
		float alpha = (float) (255 * Math.sin(3.14 * elapsed / time) / 255);
		if(alpha > 1) alpha = 1;
		if(alpha < 0) alpha = 0;
		font.getData().setScale(.5f);
		sb.begin();
		font.setColor(1, 1, 1, alpha);
		font.draw(sb, s, (float) x - offset, (float) y + 15);
		sb.end();
	}

}
