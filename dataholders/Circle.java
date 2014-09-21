package dataholders;

import helper.Utils;
import helper.Utils.UNIT;

import java.awt.Color;

import conf.Constants;

public class Circle {
	
	public static int _scalefactor = 1;
	
	private int y;  // in pixels
	private Color color;
	private int id;
	
	public Circle(int y, int id)
	{
		this.y = y;
		this.color = Constants.CIRCLE_COLOR;
		this.id = id;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public int getY()
	{
		return y;
	}

	public void incrementY(int amount)
	{	
		System.out.println("Incrementing y (" + y + ") by " + amount);
		y = (int) Math.max(0, y + amount);
		System.out.println("New y: " + y);
	}
	
	public int getId()
	{
		return id;
	}
	
	@Override 
	public String toString()
	{
		return ("Circle " + id);
	}
}
