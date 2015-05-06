package tunit;

import system.DBUtil;
import system.Date;
import system.StackTraceInfo;

public class Assert {
	
	/**
     * Protect constructor since it is a static only class
     */
    protected Assert() {}
	
    /**
     * Static values for insert to database (TODO: custom parameters for different users)
     */
	public static String table = "example",
				 		colums = "test_name, test_message, expected_result, actual_result, test_result, date";
	
	/**
	 * Save result to database with mandatory fields
	 * @param message
	 * @param expected
	 * @param actual
	 */
	public static void saveResult(String message, Object expected, Object actual) {
		//Define few values for insert: name, result and date
		String testName = StackTraceInfo.getInvokingMethodName();
		String testResult = expected.equals(actual) + "";
		String testDate = Date.getCurrentDate();
		
		System.out.println(testName);
				
		//Insert to database
		String values = "'"+ testName + "','" + message + "','" + expected + "','" + actual + "','" + testResult + "','" + testDate + "'";
		DBUtil.insertTestData(table, colums, values);
	}
	
	/**
	 * Re-write for assertEquals
	 * @param message
	 * @param expected
	 * @param actual
	 */
	static public void assertEquals(String message, Object expected, Object actual) {
		//Database insert
		saveResult(message, expected, actual);
		//JUnit assert
		org.junit.Assert.assertEquals(message, expected, actual);
	}
	
	/**
	 * Re-write for assertEquals (no message)
	 * @param expected
	 * @param actual
	 */
	static public void assertEquals(Object expected, Object actual) {
		//Database insert
		saveResult("no message", expected, actual);
		//JUnit assert
		org.junit.Assert.assertEquals(null, expected, actual);
	}
}
