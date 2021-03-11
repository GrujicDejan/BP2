package dataManipulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Zadatak1 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			stmt = conn.createStatement();
			
			/*
			 * Ubaciti jednog novog nastavnika - koristiti Statement
			 */
			String query = "insert into Nastavnik (nastavnik_Id, ime, prezime, zvanje) "
					+ "values (101, 'Marko', 'Jovanovic', 'redovni profesor')";
			
			int brRedova = stmt.executeUpdate(query);
			System.out.println("Broj dodatih redova: " + brRedova);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
