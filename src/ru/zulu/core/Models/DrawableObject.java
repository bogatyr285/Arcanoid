package ru.zulu.core.Models;

import java.awt.*;

public abstract class DrawableObject {
	public int x;
	public int y;
	public int width;
	public int height;
	public int xVelocity;
	public int yVelocity;

	public abstract void draw(Graphics _graphics);

	public void move() {
		x +=xVelocity;
		y +=yVelocity;
	}
	
	public boolean isIntersects(DrawableObject _target) {
		return (x + width) >= _target.x && x <= (_target.x + _target.width) &&   (y + height) >= _target.y && y <= (_target.y + _target.height);
	}
}
