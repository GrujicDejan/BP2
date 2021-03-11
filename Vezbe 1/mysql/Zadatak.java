package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Zadatak {
	
	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rSet = null;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			//Ispisati nastavnike koji predaju predmet Baze podataka 2
			String sql = "SELECT n.ime "
					+ "FROM predmet pr, nastavnik n, predaje p "
					+ "WHERE n.nastavnik_id = p.nastavnik_id AND pr.predmet_id = p.predmet_id AND pr.naziv = 'Baze podataka 2' ";
			
			stmt = conn.createStatement();
			rSet = stmt.executeQuery(sql);
			
			while (rSet.next()) {
				System.out.println(rSet.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rSet.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
