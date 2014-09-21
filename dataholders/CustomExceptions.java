package dataholders;

public class CustomExceptions {

	public static class XMLParsingExeption extends Exception
	{
		public XMLParsingExeption(String message)
		{
			super(message);
		}
	}
	
}
