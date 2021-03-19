/*
 * Ubaciti novog nastavnika i novi predmet (transakcije) id nastavnika i id predmeta su broj indeksa
 * pozivom funkcije (procedure) povezi.sql povezati novog nastavnika sa novim predmetom
 */

package StoredProcedures;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class Zadatak1MySql {

	public static void main(String[] args) {

		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			// Pozivanje nasvnika sa predmetom
			cstmt = conn.prepareCall("{call povezi (?, ?, ?, ?)}");
			cstmt.setString(1, "Maja");
			cstmt.setString(2, "Majic");
			cstmt.setString(3, "Analiza 1");
			cstmt.registerOutParameter(4, Types.INTEGER);
			cstmt.executeUpdate();
			
			System.out.println("Status: " + cstmt.getInt(4));
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
