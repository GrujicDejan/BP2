package transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Zadatak4 {
	
	public static void main (String[] args) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			conn.setAutoCommit(false);
			
			//dodajemo novog nastavnika
			pstmt = conn.prepareStatement("insert into nastavnik (nastavnik_id, ime, prezime, zvanje) "
					+ "values (?, ?, ?, ?)");
			
			pstmt.setInt(1, 102);
			pstmt.setString(2, "Marina");
			pstmt.setString(3, "Markovic");
			pstmt.setString(4, "asistent");
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//prikazujemo sadržaj tabele NASTAVNIK nakon dodavanja
			stmt = conn.createStatement();
			rset = stmt.executeQuery("select nastavnik_id, ime, prezime from NASTAVNIK");
			while (rset.next()) {
				System.out.println(rset.getInt(1)+ " " + rset.getString(2) + " " + rset.getString(3));
			}
			
			rset.close();
			
			//uneti nastavnik predaje neki postojeci predmet
			
			pstmt = conn.prepareStatement("insert into predaje (nastavnik_id, predmet_id) values (?, ?)");
			pstmt.setInt(1, 102);
			pstmt.setInt(2, 1);
			pstmt.executeUpdate();
			
			pstmt.close();
			
			//brišemo unetog nastavnika, ali moramo kaskadno, jer on nešto predaje
			
			pstmt = conn.prepareStatement("delete from predaje where nastavnik_id in "
					+ "(select nastavnik_id from nastavnik where ime = ? and prezime = ?)");
			pstmt.setString(1, "Marina");
			pstmt.setString(2, "Markovic");
			pstmt.executeUpdate();
			
			pstmt.close();
			
			pstmt = conn.prepareStatement("delete from nastavnik where ime = ? and prezime = ?");
			pstmt.setString(1, "Nikola");
			pstmt.setString(2, "Nikolic");
			pstmt.executeUpdate();
			
			//prikazujemo sadržaj tabele NASTAVNIK nakon brisanja
			rset = stmt.executeQuery("select nastavnik_id, ime, prezime from NASTAVNIK");
			while (rset.next()) {
				System.out.println(rset.getInt(1)+ " " + rset.getString(2) + " " + rset.getString(3));
			}
			
			conn.commit();
			
		}catch (Exception e) {
			e.printStackTrace();
			if (conn != null){
				try {
					conn.rollback();
				} catch(Exception e1){
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (rset != null) {
					rset.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}


}
