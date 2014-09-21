package helper;

import java.awt.Dimension;

public class Utils {
	
	public static enum UNIT {
		MM(1d), CM(100d);
		
		UNIT(double factor)
		{
			this.factor = factor;
		}
		
		public double getFactor()
		{
			return factor;
		}
		private double factor;
	}
	
	public static Dimension getScreenSize()
	{
		return java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public static double convert(double fromValue, UNIT fromUnit, UNIT toUnit)
	{
		// First convert to mm
		return fromValue * fromUnit.getFactor() / toUnit.getFactor(); 
	}
	
	public static void main(String[] args)
	{
		int amount = 10000;
		
		System.out.println(Utils.convert(amount, UNIT.CM, UNIT.MM));
	}
}
