/*
 * Napisati program koji ce:
 * Ubaciti novog nastavnika (ucitavati vrednosti obelezja preko tastature)
 * Ubaciti predmet koji predaje taj nastavnik (ubacivanje podataka u tabele Predmet i Predaje u jednoj transakciji).
 * Prikazati sadr�aj tabele Nastavnik.
 * Izbrisati unetog nastavnika (brisanje mora biti kaskadno � prvo iz tabele Predaje, pa iz tabele Nastavnik, u  transakciji).
 * Ponoviti prikaz sadrzaja tabele Nastavnik.
 */

package forExercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Zadatak {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rSet = null;
		Scanner sc = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/nastavnikpredmet", "root", "root");
			
			// Unos novog nastavnika
			sc = new Scanner(System.in);
			System.out.println("Unos nastavnika...");
			System.out.println("Unesite id: ");
			int idN = sc.nextInt();
			System.out.println("Unesite ime: ");
			String ime = sc.next();
			System.out.println("Unesite prezime: ");
			String prezime = sc.next();
			System.out.println("Unesite zvanje: ");
			String zvanje = sc.next();
			
			pstmt = conn.prepareStatement("insert into Nastavnik (nastavnik_id, ime, prezime, zvanje) values(?, ?, ?, ?)");
			pstmt.setInt(1, idN);
			pstmt.setString(2, ime);
			pstmt.setString(3, prezime);
			pstmt.setString(4, zvanje);
			pstmt.executeUpdate();
			
			conn.setAutoCommit(false);
			
			// Unos novog predmeta i povezivanje sa nastavnikom
			System.out.println("Unos predmeta...");
			System.out.println("Unesite id: ");
			int idP = sc.nextInt();
			System.out.println("Unesite naziv: ");
			String naziv = sc.next();
			sc.close();
			
			pstmt = conn.prepareStatement("insert into Predmet (predmet_id, naziv) values(?, ?)");
			pstmt.setInt(1, idP);
			pstmt.setString(2, naziv);
			pstmt.executeUpdate();

			pstmt = conn.prepareStatement("insert into Predaje (nastavnik_id, predmet_id) values (?, ?)");
			pstmt.setInt(1, idN);
			pstmt.setInt(2, idP);
			pstmt.executeUpdate();

			conn.commit();
			
			// Prikaz sadrzaja tabele nastavnik
			System.out.println("Prikaz tabele nastavnik nakon dodavanja");
			stmt = conn.createStatement();
			rSet = stmt.executeQuery("select * from nastavnik");
			while (rSet.next()) {
				System.out.println(rSet.getInt(1) + " " + rSet.getString(2) + " " + rSet.getString(3));
			}
			
			// Kaskadno brisanje unetog nastavnika
			pstmt = conn.prepareStatement("delete from Predaje where nastavnik_id = ?");
			pstmt.setInt(1, idN);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("delete from Nastavnik where nastavnik_id = ?");
			pstmt.setInt(1, idN);
			pstmt.executeUpdate();
			
			conn.commit();
			
			// Prikazujemo sadr�aj tabele NASTAVNIK nakon brisanja
			System.out.println("Prikaz tabele nastavnik nakon brisanja");
			stmt = conn.createStatement();
			rSet = stmt.executeQuery("select nastavnik_id, ime, prezime from NASTAVNIK");
			while (rSet.next()) {
				System.out.println(rSet.getInt(1)+ " " + rSet.getString(2) + " " + rSet.getString(3));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (rSet != null) {
					rSet.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (sc != null) {
					sc.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
