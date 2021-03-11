package Oracle;

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
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@nastava.is.pmf.uns.ac.rs:1521:xe", "baze2", "baze2");
			
			//Ispisati sve predmete koje predaje nastavnik Petar Peric
			String sql = "SELECT pr.naziv "
					+ "FROM predmet pr, nastavnik n, predaje p "
					+ "WHERE n.nastavnik_id = p.nastavnik_id AND pr.predmet_id = p.predmet_id AND n.ime = 'Petar' AND prezime = 'Peric' ";
			
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
