package helper;

public class Coordinate2D {
	public int x, y;
	
	public Coordinate2D(int x, int y)
	{
		this.x = x; 
		this.y = y;
	}
	
	public static String formatCoordinate(Coordinate2D coord)
	{
		return "[" + coord.x + "," + coord.y + "]";
	}
}
