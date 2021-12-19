package database;

import java.sql.*;


public class DB {

	 public static Connection conn = null;
	 public static PreparedStatement ps = null;
	 public static ResultSet rs;
	 
	 
	 public static Connection connect() throws SQLException {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				 conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost/gestion_biblio","root","");
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("Erreur de chargement de pilote");
			}
			
			return conn;
		}

}
