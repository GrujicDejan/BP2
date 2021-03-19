/*
 * Ubaciti novog nastavnika i novi predmet (transakcije) � id nastavnika i id predmeta su broj indeksa
 * pozivom funkcije (procedure) povezi.sql povezati novog nastavnika sa novim predmetom
 */

package StoredProcedures;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class Zadatak1Oracle {

	public static void main(String[] args) {

		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@nastava.is.pmf.uns.ac.rs:1521:xe", "baze2", "baze2");
			
			// Pozivanje nasvnika sa predmetom
			cstmt = conn.prepareCall("{? = call povezi (?, ?, ?)}");
			cstmt.setString(2, "Mina");
			cstmt.setString(3, "Minic");
			cstmt.setString(4, "Baze podataka 2");
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.executeUpdate();
			
			System.out.println("Status: " + cstmt.getInt(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (cstmt != null) {
					cstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
	

	}

}
