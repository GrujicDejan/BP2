package transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Zadatak3 {
	
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		/*
		 * Ubaciti dva nova nastavnika sa istim nastavnik_id
		 * Za razliku od prethodnog primera, sada nijedan nastavnik nece biti ubacen, 
		 * jer se oba poziva executeUpdate metode desavaju u okviru jedne transakcije
		 */
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement("insert into Nastavnik (nastavnik_Id, ime, prezime, zvanje) values(?, ?, ?, ?)");
			
			pstmt.setInt(1, 505);
			pstmt.setString(2, "Marko");
			pstmt.setString(3, "Markovic");
			pstmt.setString(4, "demonstrator");
			pstmt.executeUpdate();
			
			pstmt.setInt(1, 506);
			pstmt.setString(2, "Nemanja");
			pstmt.setString(3, "Nikolic");
			pstmt.setString(4, "docent");
			pstmt.executeUpdate();
			
			/* 
			 * Zbog conn.setAutoCommit(false); -> nece se izvrsiti ni jedna komanda ukoliko pokusamo da dodamo dve pojave tipa Nastavnik sa istim ID-em.
			 */
//			pstmt.setInt(1, 505);
//			pstmt.setString(2, "Nikola");
//			pstmt.setString(3, "Markovic");
//			pstmt.setString(4, "demonstrator");
//			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}


}
