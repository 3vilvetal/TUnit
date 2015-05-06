package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBUtil {
	
	public static String CONNECTION_TEST_DATA_STRING = StaticValues.getDBString();
	
	/**
	 * Converts table in array of Objects[][]
	 * @param query
	 * @return
	 * @throws SQLException
	 * @example //CASE 1: Get objects sample
		Object[][] results = getObjects(rs);
		for (int i = 0; i < results.length; i++) {
			for (int j = 0; j < results [i].length; j++) {
				System.out.print(results[i][j] + " | ");
			}
			System.out.println();
		}
	 */
	public static Object[][] getObjects(ResultSet resultSet) {
		
		Object[][] finalResult = null;
		
		try {
			//Gather meta data
			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			ArrayList<Object[]> result = new ArrayList<Object[]>();
			Object[] header = new Object[columnCount];
			 
			//Gather column names
			for (int i = 1; i <= columnCount; i++){
				Object label = rsMetaData.getColumnLabel(i);
				header[i-1] = label;
			}
			//Store all data in object array
			while (resultSet.next()) {
				Object[] str = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					Object obj = resultSet.getObject(i);
					str[i-1] = obj;
				}
				result.add(str);
			}
			int resultLength = result.size() + 1;
			finalResult = new Object[resultLength][columnCount];
			finalResult[0] = header;
			
			//Gather all arrays in objects array
			for(int i = 1; i < resultLength; i++) {
				Object[] row = result.get(i - 1);
				finalResult[i] = row;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return finalResult;
	}
	
	/**
	 * Converts result set in the List of HashMaps
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 * @example //CASE 2: Get the List of Hash Maps sample
		List <HashMap<String, Object>> listResults = getList(rs);
		System.out.println(listResults.get(0).get("service"));
		System.out.println(listResults.get(1).get("service"));
	 */
	public static List <HashMap<String, Object>> getList(ResultSet resultSet) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			//Get meta data
			ResultSetMetaData meta = resultSet.getMetaData();
		    int columns = meta.getColumnCount();
		    
		    //Start circle to gather all data from result set
		    while (resultSet.next()) {
		        HashMap<String, Object> row = new HashMap<String, Object>(columns);
		        for(int i = 1; i <= columns; i++) {
		            row.put(meta.getColumnName(i), resultSet.getObject(i));
		        }
		        list.add(row);
		    }
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	    return list;
	}
	
	/**
	 * Convert result set in the HashMap by the defined in parameter key
	 * @param resultSet
	 * @param parameter
	 * @return
	 * @throws SQLException
	 * @example //CASE 3: Get the HashMap
		HashMap<String, HashMap<String, Object>> hashMap = getHashMap(rs, "service");
		System.out.println(hashMap.get("my_ufins").get("login"));
		System.out.println(hashMap.get("ftp").get("password"));
	 */
	public static HashMap<String, HashMap<String, Object>> getHashMap(ResultSet resultSet, String parameter) {
		
		HashMap<String, HashMap<String, Object>> hashMap = new HashMap<String, HashMap<String, Object>>();
		try {
			//Gather all meta data
			ResultSetMetaData meta = resultSet.getMetaData();
		    int columns = meta.getColumnCount();
		    
	
		    //Gather all results to HashMap in circle
		    while (resultSet.next()) {
		        HashMap<String, Object> row = new HashMap<String, Object>(columns);
		        
		        for(int i = 1; i <= columns; i++) {
		            row.put(meta.getColumnName(i), resultSet.getObject(i));
		        }
		        hashMap.put((String) row.get(parameter),row);
		    }
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	    return hashMap;
	}
	
	/**
	 * Connection to test_data database
	 * @return Statement statement
	 * @throws SQLException 
	 */

	public static Connection  connectionToTestData() {
		return connectionToDB(CONNECTION_TEST_DATA_STRING);
	}
	
	
	/**
	 * Create connection to test_data Insert And Close Connection
	 * @param table
	 * @param colums
	 * @param values
	 * @throws SQLException
	 */
	public static void insertTestData(String table, String colums, String values) {
		Connection con = connectionToTestData();
		Statement stmt = createStatment(con);
		String sqlString = "INSERT INTO " + table + " (" + colums + ") " + "VALUES (" + values + ");";
		insertIntoDB(stmt, sqlString);
		closeStatment(stmt);
		closeConnectionToDB(con);
	}

	/**
	 * Create connection to test_data Insert And Close Connection(For difficult query's)
	 * @param sqlString
	 * @throws SQLException
	 */
	public static void insertTestData(String sqlString) {
		Connection con = connectionToTestData();
		Statement stmt = createStatment(con);
		insertIntoDB(stmt, sqlString);
		closeStatment(stmt);
		closeConnectionToDB(con);
	}
	
	/**
	 * Create connection to test_data, Select query And Close Connection 
	 * @param sqlString
	 * @return
	 * @throws SQLException
	 */
	
	public static ResultSet selectTestData(String sqlString) {
		Connection con = connectionToTestData();
		Statement stmt = createStatment(con);
		ResultSet result = selectFromDB(stmt, sqlString);
		closeStatment(stmt);
		closeConnectionToDB(con);
		return result;
	}
	
	/**
	 * Create connection to DB
	 * @Example connectionString = "jdbc:mysql://192.168.1.1:3306/qadb1?user=test&password=*******"
	 * @return connection; 
	 * @throws SQLException;
	 **/

	public static Connection connectionToDB(String connectionString) {
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver"); //I will use mssql database type 
			connection = DriverManager.getConnection(connectionString); //Connection string 
			System.out.println("Opened database successfully");
		}
		catch ( Exception e ){
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return connection;
	}

	/**
	 * Create standard connection statement;
	 * @param con Connection;
	 * @return a new default Statement object ;
	 * @throws SQLException;
	 */

	public static Statement createStatment(Connection con) {
		Statement stmt = null;
		try{
			stmt = con.createStatement();
		}
		catch( Exception e ){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return  stmt;
	}
	
	/**
	 * Close statement
	 * @stmt
	 * @throws SQLException
	 */
	public static void closeStatment(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close conection ToDB
	 * @con 
	 * @throws SQLException 
	 */
	public static void closeConnectionToDB(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert to DB
	 * @param stmt
	 * @param sqlString
	 * @throws SQLException 
	 */
	public static void insertIntoDB(Statement stmt, String sqlString) {
		try {
			stmt.executeUpdate(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Select from DB
	 * @param stmt
	 * @param sqlString
	 * @return sqlResult
	 * @throws SQLException
	 */
	public static ResultSet selectFromDB(Statement stmt, String sqlString) {
		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

}
