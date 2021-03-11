package dataManipulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Zadatak2 {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			/*
			 * Ubaciti dva nova nastavnika sa istim nastavnik_id Greska: prvi
			 * nastavnik je ubacen, kod drugog se javlja greska da ne mogu da
			 * postoje dva nastavnika sa istim nastavnik_id
			 */
			
			pstmt = conn.prepareStatement("insert into Nastavnik (nastavnik_Id, ime, prezime, zvanje) values(?, ?, ?, ?)");
			pstmt.setInt(1, 919);
			pstmt.setString(2, "Marko");
			pstmt.setString(3, "Markovic");
			pstmt.setString(4, "demonstrator");
			
			// Ovo se nece izvrsiti
			pstmt.setInt(1, 919);
			pstmt.setString(2, "Nikola");
			pstmt.setString(3, "Nikolic");
			pstmt.setString(4, "demonstrator");
			
			System.out.println("Broj dodatih redova -> " + pstmt.executeUpdate());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
