package system;

/**
 * Work with static values
 * @author Vitalii Lopushanskyi
 *
 */
public class StaticValues {
	
	static String xmlPath = "access.xml";
	static XmlParser xml = new XmlParser(xmlPath);
	
	public static String getDBLogin() {
		return xml.getValues("login").get(0);
	}
	
	public static String getDBPass() {
		return xml.getValues("password").get(0);
	}
	
	public static String getDBIp() {
		return xml.getValues("ip").get(0);
	}
	
	public static String getDBPort() {
		return xml.getValues("port").get(0);
	}
	
	public static String getDBSchema() {
		return xml.getValues("schema").get(0);
	}
	
	public static String getDBString() {
		return "jdbc:mysql://" + getDBIp() +":" + getDBPort() + "/" + getDBSchema() + "?user=" + getDBLogin() + "&password=" + getDBPass();
	}

}
