package com.visp.entities;

public class GameObject {
	
	protected float x;
	protected float y;
	protected float radius;
	
	protected float dx;
	protected float dy;
	
	protected float radians;
	protected float speed;
	protected float rotationSpeed;
	
	protected int width;
	protected int height;
	
	protected float[] shapeX;
	protected float[] shapeY;
	
	protected GameObject(float x, float y, float r, float speed) {
		this.x = x;
		this.y = y;
		this.radius = r;
		this.speed = speed;
	}
	
	// FUNCTIONS
	public double getX() { return x; }
	public double getY() { return y; }
	public float getRadius() { return radius; }
	
}
