package com.penelope.jugueteria.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private static String URL_FORMAT="jdbc:mysql://%s:%s/%s" +
			"?useUnicode=true" + 
			"&useJDBCCompliantTimezoneShift=true" + 
			"&useLegacyDatetimeCode=false" + 
			"&serverTimezone=UTC";
	
	public static Connection getConnection() {
		String host="localhost";
		String port="3306";
		String dbName="jugueteria";
		String user="root";
		String password="penelope96";
		
		Connection conn=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url=String.format(URL_FORMAT, host,port,dbName);
			conn=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Fallo al crear la conexion");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		try {
			if (conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Fallo al cerrar la conexion jdbc");
			e.printStackTrace();
		}
	}
	

}
