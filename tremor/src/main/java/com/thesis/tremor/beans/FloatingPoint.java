package com.thesis.tremor.beans;

public class FloatingPoint {

	private float x;
	
	private float y;

	public FloatingPoint() {
		
	}
	
	public FloatingPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
