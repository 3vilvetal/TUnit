package system;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * XML parser
 * @author Vitalii Lopushanskyi
 */
public class XmlParser {
	
	String xmlPath;
	
	public XmlParser(String xmlPath) {
		this.xmlPath = xmlPath;
	}
	
	public static Vector<Element> xmlElementsArray = new Vector<Element>();
	
	/**
	 * Get list of values from XML file
	 * @param tag
	 * @param xmlPath
	 * @return
	 */
	public Vector<String> getValues(String tag) {
		
		FileUtil fileUtil = new FileUtil();
		String data = fileUtil.getString(xmlPath); 
		
		return getValuesByTag(tag, data);
		
	}
	
	/**
	 * Creates collection with the list of values for some tag
	 * @return
	 */
	public Vector<String> getValuesByTag(String tag, String data) {
		
		Vector<String> valuesByTag = new Vector<String>();
		Element root, child;
		
		root = getRoot(data);
		xmlElementsArray.clear();
		getChildren(root);
		
		Iterator<Element> iterator = xmlElementsArray.iterator();
		while(iterator.hasNext()) {
			child = iterator.next();
			if (child.getName().equals(tag)) {
				valuesByTag.add(child.getValue());
			}
		}
		return valuesByTag;
	}
	
	/**
	 * Recursive child get for root of Xml document 
	 * @param child
	 */
	public static void getChildren(Element child) {
		
		List<?> children = child.getChildren();
		
		if (children.isEmpty()) {
			xmlElementsArray.add(child);
		}
		else {
			for (int i = 0; i < children.size(); i++) {
				child = (Element)children.get(i);
				getChildren(child);
			}
		}
	}
	
	/**
	 * Get root element
	 * @param xmlPath
	 * @return
	 */
	public static Element getRoot(String data) {
		
         SAXBuilder builder = new SAXBuilder();
         Document document = null;
         try {
             document = builder.build(new ByteArrayInputStream(data.getBytes("UTF-8")));
         } 
         catch (JDOMException e) {
             e.printStackTrace();
         } 
         catch (IOException e) {
             e.printStackTrace();
         }
         return document.getRootElement();
	}
	
	/**
	 * Sample
	 * @param args
	 */
	public static void main(String args[]) {
		
		String xmlPath = "access.xml";
		XmlParser xml = new XmlParser(xmlPath);
		System.out.println(xml.getValues("login").get(0));
	}
}
