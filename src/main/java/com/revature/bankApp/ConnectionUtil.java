package com.revature.bankApp;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ConnectionUtil {
  private static Connection connect = null;
  private static Logger log = LogManager.getLogger(ConnectionUtil.class);

  private ConnectionUtil(){} //Singleton

  public static Connection getConnection() {
    log.traceEntry();
    if (connect != null){
      return ConnectionUtil.connect;
    }
    Properties props = new Properties();
    try (InputStream in = new FileInputStream("src\\main\\resources\\connections.properties");) {
    	props.load(in);
    } catch (FileNotFoundException e) {
		log.error(e);
	} catch (IOException e) {
		log.error(e);
	}
    try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (Exception e) {
    	log.error(e);
    }
	Connection con = null;
		
	String endpoint = props.getProperty("jdbc.url");
	String username = props.getProperty("jdbc.username");
	String password = props.getProperty("jdbc.password");
		
	try {
		con = DriverManager.getConnection(endpoint, username, password);
		
		return con;
	} catch (SQLException e) {
		log.catching(e);
		log.error("Sql Exception",e);
	}
	
	return null;
  }
}
