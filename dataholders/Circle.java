package dataholders;

import helper.Coordinate2D;

import java.awt.Color;

import conf.Constants;

public class Circle {
	
	private int r, y; // in centimeters
	private Color color;
	private int idx;
	
	public Circle(Coordinate2D pos, int idx)
	{
		y = 0;
		this.r = Constants.CIRCLE_RADIUS;
		this.color = Constants.CIRCLE_COLOR;
		this.idx = idx;
	}
	
	public int getRadius()
	{
		return r;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void incrementY(int amount)
	{
		y = (int) Math.max(0, y + amount);
	}
	
	@Override 
	public String toString()
	{
		return ("Circle " + idx);
	}
}
