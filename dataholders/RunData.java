package dataholders;

import helper.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import conf.Constants;
import core.MainWindow;
import dataholders.CustomExceptions.XMLParsingExeption;

public class RunData {
	public Circle[] circles;
	
	public RunData()
	{
		this(Constants.CIRCLE_COUNT);
	}
	
	public RunData(Circle[] circles)
	{
		this.circles = circles;
	}
	
	public RunData(int circleCount)
	{
		circles = new Circle[circleCount];
		for(int i = 0; i < circleCount; i++)
		{
			circles[i] = new Circle(0, i+1);
		}
	}
	
	public Circle[] getCircles()
	{
		return circles;
	}
	
	public void incrementY(int index, int amount)
	{
		circles[index].incrementY(amount);
	}
	
	public static enum CustomNode{
		Circles("Circles"),
			Circle("Circle"),
				Id("Id"),
				Height("height");
		
		String tagName;
		CustomNode(String tagName)
		{
			this.tagName = tagName;
		}
		
		String getTagName()
		{
			return this.tagName;
		}
	}
	
	public static void writeToFile(File file, RunData runData)
	{
	    FileOutputStream fos = null;
	    try {
	        fos = new FileOutputStream(file);
	        XMLOutputFactory xmlOutFact = XMLOutputFactory.newInstance();
	        XMLStreamWriter writer = xmlOutFact.createXMLStreamWriter(fos);
	        writer.writeStartDocument();writer.writeCharacters("\n");
	        
	        writer.writeStartElement(CustomNode.Circles.getTagName());writer.writeCharacters("\n");
	        for(Circle c : runData.getCircles())
	        {
	        	writer.writeStartElement(CustomNode.Circle.getTagName());writer.writeCharacters("\n");
        			writer.writeStartElement(CustomNode.Id.getTagName());
        				writer.writeCharacters(String.valueOf(c.getId()));
        			writer.writeEndElement();writer.writeCharacters("\n");
        			writer.writeStartElement(CustomNode.Height.getTagName());
    					writer.writeCharacters(String.valueOf(Utils.convert(c.getY() / (double) MainWindow._scaleFactor, Utils.UNIT.CM, Utils.UNIT.MM)));
    				writer.writeEndElement();writer.writeCharacters("\n");
        		writer.writeEndElement();writer.writeCharacters("\n");
	        }
	        writer.writeEndElement();writer.writeCharacters("\n");
	        writer.flush();
	    }
	    catch(IOException exc) {
	    }
	    catch(XMLStreamException exc) {
	    }
	    finally {
	    }
	}
	
	public static RunData loadFromFile(File file) throws XMLParsingExeption, ParserConfigurationException, SAXException, IOException
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setIgnoringElementContentWhitespace(true);

	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(file);
	        
	    // Get all circles
	    NodeList circles = doc.getElementsByTagName(CustomNode.Circle.getTagName());
	        
	    List<Circle> loadedCircles = new ArrayList<Circle>();
	    
	    for(int i = 0; i < circles.getLength(); i++)
	    {
	    	Element circleElement = (Element) circles.item(i);
	    	
	    	NodeList idNL = circleElement.getElementsByTagName(CustomNode.Id.getTagName());
	    	NodeList heightNL = circleElement.getElementsByTagName(CustomNode.Height.getTagName());
	        	
	    	if(idNL.getLength() != 1 || heightNL.getLength() != 1)
	    		throw new CustomExceptions.XMLParsingExeption("Invalid id or height value for for circle " + (i+1));
	        	
	    	int id = Integer.valueOf(idNL.item(0).getFirstChild().getNodeValue());
	    	double height = Double.valueOf(heightNL.item(0).getFirstChild().getNodeValue());
	        int y = (int) Utils.convert(height * MainWindow._scaleFactor, Utils.UNIT.MM, Utils.UNIT.CM);
	    	loadedCircles.add(new Circle(y, id));
	    }
	    
	    System.out.println("Loaded data from file " + file.getAbsolutePath() + " (" + loadedCircles.size() + " circles)");
	    
	    return new RunData(loadedCircles.toArray(new Circle[0]));
	}
	
	public static int getIntValue(Node node)
	{
		System.out.println("Node name:" + node.getNodeName());
		return Integer.valueOf(node.getFirstChild().getNodeValue());
	}
	
	public static void main(String[] args)
	{
		String filename = "/home/harry/tmp/out.xml";
//		RunData.writeToFile(filename, new RunData());

//		try {
//			RunData.loadFromFile(filename);
//		} catch (XMLParsingExeption | ParserConfigurationException | SAXException | IOException e) {
//			System.out.println("Failed to parse xml file: " + filename);
//		}
		
//		
//		RunData loadedData = null;
//		try {
//			loadedData = RunData.loadFromFile(filename);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(loadedData != null)
//			System.out.println(loadedData);
//		else
//			System.err.println("Failed to load data from file " + filename);
	}
}
