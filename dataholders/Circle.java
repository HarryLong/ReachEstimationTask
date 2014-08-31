package dataholders;

import helper.Coordinate2D;

import java.awt.Color;

import conf.Constants;

public class Circle {
	
	double offsetPercentage;
	private int r;
	private Color color;
	private int idx;
	
	public Circle(Coordinate2D pos, int idx)
	{
		offsetPercentage = 0d; 
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
	
	public double getOffset()
	{
		return offsetPercentage;
	}
	
	public void incrementY(int amount)
	{
		offsetPercentage = Math.max(0, Math.min(offsetPercentage + 0.001*amount, 1d));
	}
	
	@Override 
	public String toString()
	{
		return ("Circle " + idx);
	}
}
