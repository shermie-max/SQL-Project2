import java.sql.*;
import java.util.Scanner;

public class DBConnector {


	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	   
	public static Connection makeConnection() {
		Connection conn = null; // the actual mysql server connection

   		// strings for mysql hostname, userid, ...
		String db_host = "127.0.0.1:3306";
		String db_user= "root";
		String db_password="Hellomila1987*";
		String db_name="project2";
		
		// get user credentials and mysql server info

		db_host = "jdbc:mysql://" + db_host+ "/" + db_name+"?enabledTLSProtocols=TLSv1.2";
			
		// most mysql calls can cause exceptions,so we'll try to catch them. 
		try
		{
			// set up the client (this machine) to use mysql
			System.out.print("Initializing client DB subsystem ...");
			Class.forName(JDBC_DRIVER);
			System.out.println("Done.");
			   			   
			// go out and connect to the mysql server
			System.out.print("Connecting to remote DB ...");
			conn = DriverManager.getConnection(db_host,db_user, db_password);
			System.out.println("DB connection established.");

	   }  catch(SQLException se) {
		   //Handle errors for JDBC
		   se.printStackTrace();
	   }  catch(Exception e) {
		   // Handle any other exceptions
		   System.out.println("Exception"+e);
	   }
		return conn;
   }
}



